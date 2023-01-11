package com.accenture.tamalli.services;

import com.accenture.tamalli.dto.orders.OrderDTO;
import com.accenture.tamalli.dto.orders.OrderHistoryDTO;
import com.accenture.tamalli.dto.orders.ShoppingCartDTO;
import com.accenture.tamalli.exceptions.BadRequestOrderException;
import com.accenture.tamalli.exceptions.NotFoundCustomerException;
import com.accenture.tamalli.repositories.IOrderRepository;
import com.fasterxml.jackson.core.type.TypeReference;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeAll;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class OrderServiceTest {

    @Autowired
    private IOrderService iOrderService;

    @Autowired
    private IOrderRepository iOrderRepository;

    private List<OrderDTO> expectedOrders;

    private static ObjectMapper objectMapper;

    private static  String expectedShoppingHistoryJson;

    private static String expectedPaidOrderJson;

    private static String expectedAllPaidOrdersJson;

    private  static String expectedShoppingCartJson;

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
    void changeApiShoppingCartStatusTest() throws Exception{
        OrderDTO expectedShoppingCart = objectMapper.readValue(expectedPaidOrderJson, OrderDTO.class);



        OrderDTO currentShoppingCart= iOrderService.changeShoppingCartStatusToPaid(1L);
        //Order id
        assertEquals(expectedShoppingCart.getOrderId(),currentShoppingCart.getOrderId());
        //Cost
        assertEquals(expectedShoppingCart.getTotalCost(),currentShoppingCart.getTotalCost());
        //Customer id
        assertEquals(expectedShoppingCart.getCustomerId(),currentShoppingCart.getCustomerId());
        //Is paid field true?
        assertTrue(iOrderRepository.findById(currentShoppingCart.getOrderId()).orElse(null).getPaid());

    }

    @Test
    @Transactional
    void changeApiShoppingCartStatusExceptionTest(){
        Throwable error=assertThrows(BadRequestOrderException.class, ()->iOrderService.changeShoppingCartStatusToPaid(3L));
        //try to change status to empty order
        assertEquals("Your shopping cart with Id:5 is empty, please add products",error.getMessage());
    }

    @Test
    @Transactional
    void getApiCustomerShoppingCartTest()throws Exception{

        ShoppingCartDTO expectedShoppingCart= objectMapper.readValue(expectedShoppingCartJson,ShoppingCartDTO.class);

        ShoppingCartDTO currentShoppingCart=iOrderService.getShoppingCart(1L);

        //same final cost
        assertEquals(expectedShoppingCart.getTotalCost(),currentShoppingCart.getTotalCost());
        //Same list size
        assertEquals(expectedShoppingCart.getShoppingCartList().size(), currentShoppingCart.getShoppingCartList().size());

    }

    @Test
    @Transactional
    void getApiCustomerShoppingCartExceptionTest(){
        Throwable error=assertThrows(NotFoundCustomerException.class, ()->iOrderService.getShoppingCart(7L));
        assertEquals("There is no a customer with Id:7",error.getMessage());
    }

    @Test
    @Transactional
    void getApiCustomerHistoryTest() throws Exception{

        List<OrderHistoryDTO> expectedShoppingHistory = objectMapper.readValue(expectedShoppingHistoryJson, new TypeReference<List<OrderHistoryDTO>>() {});

        List<OrderHistoryDTO> currentShoppingHistory = iOrderService.getShoppingHistory(1L);
        //Same size
        assertEquals(expectedShoppingHistory.size(), currentShoppingHistory.size());
        //same  JSON form
        assertEquals(expectedShoppingHistoryJson,objectMapper.writeValueAsString(currentShoppingHistory));
    }

    @Test
    @Transactional
    void getApiCustomerHistoryExceptionTest(){
        Throwable error=assertThrows(NotFoundCustomerException.class, ()->iOrderService.getShoppingHistory(7L));
        assertEquals("There is no a customer with Id:7",error.getMessage());
    }

    @Test
    @Transactional
    void getApiAllOrdersPaidTest() throws Exception{

        List<OrderDTO> expectedPaidOrders=objectMapper.readValue(expectedAllPaidOrdersJson,new TypeReference<List<OrderDTO>>() {});
        List<OrderDTO> currentPaidOrders= iOrderService.getAllOrdersPaidStore();

        assertEquals(expectedPaidOrders.size(),currentPaidOrders.size());
        assertEquals(expectedAllPaidOrdersJson,objectMapper.writeValueAsString(currentPaidOrders));

    }

    @Test
    void getApiAllOrdersPaidFromTest() throws Exception{

        List<OrderDTO> currentPaidOrders= iOrderService.getAllOrdersFrom("2022-01-03");
        assertEquals(2,currentPaidOrders.size());
    }

    @Test
    @Transactional
    void getApiAllOrdersPaidFromExceptionTest() throws Exception{

        Throwable error=assertThrows(DateTimeException.class, ()->iOrderService.getAllOrdersFrom("2022-31-03"));
        assertNotNull(error.getMessage());
    }

    private static void jsonSetup(){

        expectedShoppingHistoryJson= "[{\"orderId\":1,\"purchaseDate\":\"2023-01-01T13:14:21\",\"totalCost\":22.50,\"customerId\":1,\"orderDetails\":[{\"detailOrderId\":1,\"quantityOrdered\":1,\"productOrdered\":\"Tamal verde\",\"productPriceOrdered\":12.50,\"productLine\":\"Food\"},{\"detailOrderId\":2,\"quantityOrdered\":1,\"productOrdered\":\"Café\",\"productPriceOrdered\":10.00,\"productLine\":\"Drink\"}]},{\"orderId\":2,\"purchaseDate\":\"2023-01-03T08:30:45\",\"totalCost\":50.00,\"customerId\":1,\"orderDetails\":[{\"detailOrderId\":3,\"quantityOrdered\":2,\"productOrdered\":\"Tamal rajas\",\"productPriceOrdered\":13.00,\"productLine\":\"Food\"},{\"detailOrderId\":4,\"quantityOrdered\":1,\"productOrdered\":\"Atole galleta\",\"productPriceOrdered\":14.00,\"productLine\":\"Drink\"},{\"detailOrderId\":5,\"quantityOrdered\":1,\"productOrdered\":\"Café\",\"productPriceOrdered\":10.00,\"productLine\":\"Drink\"}]}]";

        expectedPaidOrderJson="{\n" +
                "    \"orderId\": 3,\n" +
                "    \"purchaseDate\": \"2023-01-06T19:17:57.1340973\",\n" +
                "    \"totalCost\": 51.00,\n" +
                "    \"customerId\": 1\n" +
                "}";
        expectedShoppingCartJson="{\n" +
                "    \"totalCost\": 51.00,\n" +
                "    \"shoppingCartList\": [\n" +
                "        {\n" +
                "            \"detailOrderId\": 6,\n" +
                "            \"quantityOrdered\": 2,\n" +
                "            \"productOrdered\": \"Tamal verde\",\n" +
                "            \"productPriceOrdered\": 12.50,\n" +
                "            \"productLine\": \"Food\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"detailOrderId\": 7,\n" +
                "            \"quantityOrdered\": 2,\n" +
                "            \"productOrdered\": \"Tamal rajas\",\n" +
                "            \"productPriceOrdered\": 13.00,\n" +
                "            \"productLine\": \"Food\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";

        expectedAllPaidOrdersJson="[{\"orderId\":1,\"purchaseDate\":\"2023-01-01T13:14:21\",\"totalCost\":22.50,\"customerId\":1},{\"orderId\":2,\"purchaseDate\":\"2023-01-03T08:30:45\",\"totalCost\":50.00,\"customerId\":1}]";
    }



}


