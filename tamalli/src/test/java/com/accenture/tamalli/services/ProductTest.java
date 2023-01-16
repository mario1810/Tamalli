package com.accenture.tamalli.services;

import com.accenture.tamalli.dto.products.ProductPriceDTO;
import com.accenture.tamalli.exceptions.BadRequestProductException;
import com.accenture.tamalli.exceptions.NotFoundProductException;
import com.accenture.tamalli.models.Drink;
import com.accenture.tamalli.models.Product;
import com.accenture.tamalli.models.Tamal;
import com.fasterxml.jackson.core.type.TypeReference;
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
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ProductTest {

    @Autowired
    private IProductService iProductService;
    private static ObjectMapper objectMapper;

    private static String expectedAddedDrinkJson;
    private static String expectedAddedTamalJson;
    private static String expectedGetDrinkJson;
    private static String expectedGetTamalJson;
    private static String expectedGetProductJson;
    private static String expectedGetAllDrinksJson;
    private static String expectedGetAllTamalesJson;

    private static String expectedProductPriceChangeJson;

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
    void addDrinkTest() throws Exception{

        Drink chocolate = new Drink();
        chocolate.setProductName("Chocolate");
        chocolate.setPrice( new BigDecimal("10.00"));
        chocolate.setCapacityLiters(0.3);


        Drink currentAddedDrink=iProductService.addDrink(chocolate);

        assertNotNull(currentAddedDrink);
        //check product id
        assertEquals(7L,currentAddedDrink.getProductId());
        assertEquals(expectedAddedDrinkJson,objectMapper.writeValueAsString(currentAddedDrink));

    }

    @Test
    @Transactional
    void addDrinkExceptionTest() {

        Drink atoleGalleta = new Drink();
        atoleGalleta.setProductName("Atole galleta");
        atoleGalleta.setPrice( new BigDecimal("14.00"));
        atoleGalleta.setCapacityLiters(0.3);

        Throwable error=assertThrows(BadRequestProductException.class, ()->iProductService.addDrink( atoleGalleta));

        //add a product that already exists
        assertEquals("This product is already in the database",error.getMessage());

    }


    @Test
    @Transactional
    void addTamalTest() throws Exception{

        Tamal tamalDuclce= new Tamal();
        tamalDuclce.setProductName("Tamal dulce");
        tamalDuclce.setPrice(new BigDecimal("13.00"));
        tamalDuclce.setWeightKilogram(0.25);

        Tamal currentAddedTamal=iProductService.addTamal(tamalDuclce);
        assertNotNull(currentAddedTamal);
        assertEquals(expectedAddedTamalJson,objectMapper.writeValueAsString(currentAddedTamal));
    }

    @Test
    @Transactional
    void addTamalExceptionTest(){
        Tamal tamalRajas= new Tamal();
        tamalRajas.setProductName("Tamal rajas");
        tamalRajas.setPrice(new BigDecimal("13.00"));
        tamalRajas.setWeightKilogram(0.25);

        Throwable error=assertThrows(BadRequestProductException.class, ()->iProductService.addTamal(tamalRajas));

        //add a product that already exists
        assertEquals("This product is already in the database",error.getMessage());

    }

    @Test
    @Transactional
    void getTamalByIdTest() throws Exception{

     Tamal expectedGetTamal=objectMapper.readValue(expectedGetTamalJson,Tamal.class);
     Tamal currentTamal=iProductService.getTamalById(2L);
     //not null
     assertNotNull(currentTamal);
     //same name
     assertEquals(expectedGetTamal.getProductName(),currentTamal.getProductName());
     //same price
     assertEquals(expectedGetTamal.getPrice(),currentTamal.getPrice());
     //same all
     assertEquals(expectedGetTamalJson,objectMapper.writeValueAsString(currentTamal));

    }

    @Test
    @Transactional
    void getTamalByIdExceptionTest(){

        Throwable error=assertThrows(NotFoundProductException.class, ()->iProductService.getTamalById(21L));
        //add a product that already exists
        assertEquals("There is no tamal with id:21",error.getMessage());
    }



    @Test
    @Transactional
    void getDrinkByIdTest() throws Exception{

        Drink expectedGetDrink=objectMapper.readValue(expectedGetDrinkJson,Drink.class);
        Drink currentDrink=iProductService.getDrinkById(4L);
        //not null
        assertNotNull(currentDrink);
        //same name
        assertEquals(expectedGetDrink.getProductName(),currentDrink.getProductName());
        //same price
        assertEquals(expectedGetDrink.getPrice(),currentDrink.getPrice());
        //same all
        assertEquals(expectedGetDrinkJson,objectMapper.writeValueAsString(currentDrink));

    }

    @Test
    @Transactional
    void getDrinkByIdExceptionTest(){

        Throwable error=assertThrows(NotFoundProductException.class, ()->iProductService.getDrinkById(21L));
        //add a product that already exists
        assertEquals("There is no drink with id:21",error.getMessage());
    }


    @Test
    @Transactional
    void getProductByIdTest() throws Exception{

        Tamal expectedGetProduct=objectMapper.readValue(expectedGetProductJson,Tamal.class);
        Product currentProduct=iProductService.getProductById(1L);
        //not null
        assertNotNull(currentProduct);
        assertEquals(expectedGetProduct.getProductId(),currentProduct.getProductId());
        //same name
        assertEquals(expectedGetProduct.getProductName(),currentProduct.getProductName());
        //same price
        assertEquals(expectedGetProduct.getPrice(),currentProduct.getPrice());
        assertEquals(expectedGetProduct.getProductType(),currentProduct.getProductType());
        assertEquals(expectedGetProduct.getWeightKilogram(),((Tamal)currentProduct).getWeightKilogram());


    }

    @Test
    @Transactional
    void getProductByIdExceptionTest(){

        Throwable error=assertThrows(NotFoundProductException.class, ()->iProductService.getProductById(21L));
        //add a product that already exists
        assertEquals("There is no product with id:21",error.getMessage());
    }


    @Test
    @Transactional
    void getAllDrinksTest() throws Exception{

        List<Drink> expectedDrinks=objectMapper.readValue(expectedGetAllDrinksJson,new TypeReference<List<Drink>>() {});
        List<Drink> currentDrinks=iProductService.getAllDrinks();
        //not null
        assertNotNull(currentDrinks);
        //same name
        assertEquals(expectedDrinks.size(),currentDrinks.size());
        //same price for first drink
        assertEquals(expectedDrinks.get(0).getPrice(),currentDrinks.get(0).getPrice());
        //same all
        assertEquals(expectedGetAllDrinksJson,objectMapper.writeValueAsString(currentDrinks));

    }

    @Test
    @Transactional
    void getAllTamalesTest() throws Exception{

        List<Tamal> expectedTamales=objectMapper.readValue(expectedGetAllTamalesJson,new TypeReference<List<Tamal>>() {});
        List<Tamal> currentTamales=iProductService.getAllTamales();
        //not null
        assertNotNull(currentTamales);
        //same name
        assertEquals(expectedTamales.size(),currentTamales.size());
        //same price for first drink
        assertEquals(expectedTamales.get(0).getPrice(),currentTamales.get(0).getPrice());
        //same all
        assertEquals(expectedGetAllTamalesJson,objectMapper.writeValueAsString(currentTamales));
    }

    @Test
    @Transactional
    void getAllProductTest() throws Exception{

        List<Drink> expectedDrinks=objectMapper.readValue(expectedGetAllDrinksJson, new TypeReference<List<Drink>>() {});
        List<Tamal> expectedTamales=objectMapper.readValue(expectedGetAllTamalesJson, new TypeReference<List<Tamal>>() {});
        List<Product> currentProducts=iProductService.getAllProduct();
        //not null
        assertNotNull(currentProducts);
        //same name
        assertEquals(expectedTamales.size()+expectedDrinks.size(),currentProducts.size());
        //same all (first 3 are tamales)
        assertEquals(objectMapper.writeValueAsString(expectedTamales.get(0)),objectMapper.writeValueAsString(currentProducts.get(0)));
        assertEquals(objectMapper.writeValueAsString(expectedTamales.get(1)),objectMapper.writeValueAsString(currentProducts.get(1)));
        assertEquals(objectMapper.writeValueAsString(expectedTamales.get(2)),objectMapper.writeValueAsString(currentProducts.get(2)));
        //are drinks
        assertEquals(objectMapper.writeValueAsString(expectedDrinks.get(0)),objectMapper.writeValueAsString(currentProducts.get(3)));
        assertEquals(objectMapper.writeValueAsString(expectedDrinks.get(1)),objectMapper.writeValueAsString(currentProducts.get(4)));
        assertEquals(objectMapper.writeValueAsString(expectedDrinks.get(2)),objectMapper.writeValueAsString(currentProducts.get(5)));
    }

    @Test
    @Transactional
    void deleteProductTest(){

        String result=iProductService.deleteProduct(1L);
        assertEquals("The product with id:1 has been deleted",result);

    }

    @Test
    @Transactional
    void deleteProductExceptionTest(){
        Throwable error=assertThrows(NotFoundProductException.class, ()->iProductService.deleteProduct(10L));
        assertEquals("There is no product with id:10",error.getMessage());
    }

    @Test
    @Transactional
    void changeProductPriceTest() throws Exception {

        ProductPriceDTO product = new ProductPriceDTO(2L, new BigDecimal("100.0"));

        Product expectedUpdateProduct= objectMapper.readValue(expectedProductPriceChangeJson, Tamal.class);
        Product updatedProduct=iProductService.changeProductPrice(product);

        assertEquals(expectedUpdateProduct.getProductName(),updatedProduct.getProductName());
        assertEquals(expectedUpdateProduct.getPrice(),updatedProduct.getPrice());
        assertEquals(expectedProductPriceChangeJson,objectMapper.writeValueAsString(updatedProduct));
    }


    @Test
    @Transactional
    void changeProductPriceExceptionTest(){

        ProductPriceDTO product = new ProductPriceDTO(20L, new BigDecimal("100.0"));
        Throwable error=assertThrows(NotFoundProductException.class, ()->iProductService.changeProductPrice(product));
        assertEquals("There is no product with id:20",error.getMessage());

    }

    private static void jsonSetup() {

        expectedAddedDrinkJson ="{\"productId\":7,\"productName\":\"Chocolate\",\"price\":10.00,\"productType\":\"Drink\",\"capacityLiters\":0.3}";

        expectedAddedTamalJson ="{\"productId\":8,\"productName\":\"Tamal dulce\",\"price\":13.00,\"productType\":\"Food\",\"weightKilogram\":0.25}";

        expectedGetDrinkJson ="{\"productId\":4,\"productName\":\"Café\",\"price\":10.00,\"productType\":\"Drink\",\"capacityLiters\":0.3}";

        expectedGetTamalJson ="{\"productId\":2,\"productName\":\"Tamal rajas\",\"price\":13.00,\"productType\":\"Food\",\"weightKilogram\":0.25}";

        expectedGetProductJson ="{\"productId\":1,\"productName\":\"Tamal verde\",\"price\":12.50,\"productType\":\"Food\",\"weightKilogram\":0.25}\n";

        expectedGetAllDrinksJson ="[{\"productId\":4,\"productName\":\"Café\",\"price\":10.00,\"productType\":\"Drink\",\"capacityLiters\":0.3},{\"productId\":5,\"productName\":\"Atole Champurrado\",\"price\":12.00,\"productType\":\"Drink\",\"capacityLiters\":0.3},{\"productId\":6,\"productName\":\"Atole galleta\",\"price\":14.00,\"productType\":\"Drink\",\"capacityLiters\":0.3}]";

        expectedGetAllTamalesJson ="[{\"productId\":1,\"productName\":\"Tamal verde\",\"price\":12.50,\"productType\":\"Food\",\"weightKilogram\":0.25},{\"productId\":2,\"productName\":\"Tamal rajas\",\"price\":13.00,\"productType\":\"Food\",\"weightKilogram\":0.25},{\"productId\":3,\"productName\":\"Tamal al pastor\",\"price\":17.25,\"productType\":\"Food\",\"weightKilogram\":0.25}]";

        expectedProductPriceChangeJson ="{\"productId\":2,\"productName\":\"Tamal rajas\",\"price\":100.0,\"productType\":\"Food\",\"weightKilogram\":0.25}";
    }



}
