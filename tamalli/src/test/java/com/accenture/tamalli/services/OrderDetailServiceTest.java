package com.accenture.tamalli.services;

import com.accenture.tamalli.dto.orderDetails.ProductOrderDTO;
import com.accenture.tamalli.dto.products.ProductPriceDTO;
import com.accenture.tamalli.exceptions.*;
import com.accenture.tamalli.repositories.IOrderDetailRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class OrderDetailServiceTest {

    @Autowired
    private IOrderDetailService iOrderDetailService;

    @Autowired
    private IProductService iProductService;

    @Autowired
    private IOrderDetailRepository iOrderDetailRepository;

    private static String expectedAddedProductToShoppingCartJson;

    private static String expectedUpdatedProductQuantityJson;

    private static ObjectMapper objectMapper;

    @BeforeAll
    public static void setup(){
        objectMapper = new ObjectMapper();
        objectMapper = JsonMapper.builder().addModule(new JavaTimeModule()).build();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm a z");
        objectMapper.setDateFormat(df);
        jsonSetup();
    }

    @Test
    @Transactional
    void addProductToShoppingCartTest() throws Exception{

        ProductOrderDTO expectedAddedProductToShoppingCart= objectMapper.readValue(expectedAddedProductToShoppingCartJson,ProductOrderDTO.class);

        //Customer 3, product 1, quantity 2
        ProductOrderDTO currentAddedProductToShoppingCart=iOrderDetailService.addProductToShoppingCart(3L,1L,2);

        //is not empty
        assertNotNull(currentAddedProductToShoppingCart);
        //is created  in the DB
        assertNotNull(iOrderDetailRepository.findByDetailOrderId(currentAddedProductToShoppingCart.getDetailOrderId()).orElse(null));
        //is the same product
        assertEquals(expectedAddedProductToShoppingCart.getProductOrdered(),currentAddedProductToShoppingCart.getProductOrdered());
        // json are equal
        assertEquals(expectedAddedProductToShoppingCartJson,objectMapper.writeValueAsString(currentAddedProductToShoppingCart));

    }

    @Test
    @Transactional
    void addProductToShoppingCartExceptionTest (){

        //product 1, quantity 2
        Throwable error=assertThrows(OrderDetailException.class, ()->iOrderDetailService.addProductToShoppingCart(1L,1L,2));
        //add a product that  is already in the order
        assertEquals("There is already a product with id:1 in your shopping cart. You can remove it or change the quantity",error.getMessage());
    }

    @Test
    @Transactional
    void removeProductFromShoppingCartTest(){

        //customer 1, product 1
        String result=iOrderDetailService.removeProductFromShoppingCart(1L,1L);

        assertEquals("the product with Id:1 has been deleted from your shopping bag",result);


    }

    @Test
    @Transactional
    void removeProductFromShoppingCartExceptionTest(){

        //customer 1, product 10
        Throwable error=assertThrows(NotFoundOrderDetailException.class, ()->iOrderDetailService.removeProductFromShoppingCart(1L,10L));

        assertEquals("There is no a product with id:10 in the shopping cart",error.getMessage());
    }

    @Test
    @Transactional
    void removeAllProductsFromShoppingCartTest(){

        //customer 2
        String result=iOrderDetailService.removeAllProductsFromShoppingCart(2L);
        assertEquals("All products have been deleted from your shopping bag",result);

    }



    @Test
    @Transactional
    void removeAllProductsFromShoppingCartExceptionTest(){


        //customer 2
        Throwable error=assertThrows(OrderDetailException.class, ()-> iOrderDetailService.removeAllProductsFromShoppingCart(3L));
        //shopping cart empty
        assertEquals("The shopping cart is already empty",error.getMessage());
    }

    @Test
    @Transactional
    void changeProductQuantityAtShoppingCartTest() throws Exception{

        ProductOrderDTO expectedUpdatedProductQuantity= objectMapper.readValue(expectedUpdatedProductQuantityJson,ProductOrderDTO.class);

        //Customer 1, product 1, quantity 2
        ProductOrderDTO currentUpdatedProductQuantity=iOrderDetailService.changeProductQuantityAtShoppingCart(1L,1L,2);

        //is not empty
        assertNotNull(currentUpdatedProductQuantity);
        //is the same product
        assertEquals(expectedUpdatedProductQuantity.getProductOrdered(),currentUpdatedProductQuantity.getProductOrdered());
        //quantity
        assertEquals(expectedUpdatedProductQuantity.getQuantityOrdered(),currentUpdatedProductQuantity.getQuantityOrdered());
        // json are equal
        assertEquals(expectedUpdatedProductQuantityJson,objectMapper.writeValueAsString(currentUpdatedProductQuantity));


    }

    @Test
    @Transactional
    void changeProductQuantityAtShoppingCartException1Test(){


        // customer 10, product 1, quantity 10
        Throwable error=assertThrows(NotFoundCustomerException.class, ()->iOrderDetailService.changeProductQuantityAtShoppingCart(10L,1L,10));
        //wrong customer
        assertEquals("there is no customer with id:10",error.getMessage());


    }

    @Test
    @Transactional
    void changeProductQuantityAtShoppingCartException2Test() {


        // customer 1, product 10, quantity 10
        Throwable error=assertThrows(NotFoundOrderDetailException.class, ()->iOrderDetailService.changeProductQuantityAtShoppingCart(1L,10L,10));
        //there is no product in the shopping cart
        assertEquals("There is no a product with id:10 in the shopping cart",error.getMessage());
    }

    @Test
    @Transactional
    void changeProductQuantityAtShoppingCartException3Test(){

        // customer 1, product 1, quantity 100
        Throwable error=assertThrows(BadRequestProductException.class, ()->iOrderDetailService.changeProductQuantityAtShoppingCart(1L,1L,100));
        //wrong quantity
        assertEquals("Quantity is no valid, please choose a value between 1 and 30",error.getMessage());
    }

    @Test
    @Transactional
    void changeProductQuantityAtShoppingCartException4Test(){

        //Product price change
        iProductService.changeProductPrice(new ProductPriceDTO(1L, new BigDecimal(20.50)) );
        Throwable error=assertThrows(ProductException.class, ()->iOrderDetailService.changeProductQuantityAtShoppingCart(1L,1L,10));
        //add products when tha price has changed
        assertEquals("The product's price has changed so we are going to respect the previous price for the quantity you have ordered. Delete this product with id:1 if you need more.",error.getMessage());

    }

    private static void jsonSetup(){

        expectedAddedProductToShoppingCartJson ="{\"orderId\":5,\"detailOrderId\":9,\"quantityOrdered\":2,\"productOrdered\":\"Tamal verde\",\"productPriceOrdered\":12.50,\"productLine\":\"Food\"}";

        expectedUpdatedProductQuantityJson="{\"orderId\":3,\"detailOrderId\":6,\"quantityOrdered\":2,\"productOrdered\":\"Tamal verde\",\"productPriceOrdered\":12.50,\"productLine\":\"Food\"}";

    }
}
