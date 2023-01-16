package com.accenture.tamalli.controllers;

import com.accenture.tamalli.dto.orders.OrderDTO;
import com.accenture.tamalli.dto.orders.OrderHistoryDTO;
import com.accenture.tamalli.dto.orders.ShoppingCartDTO;
import com.accenture.tamalli.services.IOrderService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.mockito.Mockito.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(OrderController.class)
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;


    private ShoppingCartDTO shoppingCart;

    @MockBean
    private IOrderService iOrderService;

    @Test
    void changeApiShoppingCartStatusTest() throws Exception{

        String json ="{\n" +
                "    \"orderId\": 3,\n" +
                "    \"purchaseDate\": \"2023-01-06T19:17:57.1340973\",\n" +
                "    \"totalCost\": 51.00,\n" +
                "    \"customerId\": 1\n" +
                "}";
        OrderDTO order = mapper.readValue(json, OrderDTO.class);

        when(iOrderService.changeShoppingCartStatusToPaid(anyLong())).thenReturn(order);

        MockHttpServletRequestBuilder mockRequest =
                MockMvcRequestBuilders.put("/api/tamalli/orders/paid/1")
                        .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk()); //check is response status is 200
    }

    @Test
    void getApiCustomerShoppingCartTest() throws Exception{

        String json= "{\n" +
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
        shoppingCart=mapper.readValue(json, ShoppingCartDTO.class);
        when(iOrderService.getShoppingCart(anyLong())).thenReturn(shoppingCart);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/tamalli/orders/shoppingCart/1"))
                .andExpect(status().isOk()); //check is response status is 200

    }

    @Test
    void getApiCustomerHistoryTest() throws Exception{

        String json="[{\"orderId\":1,\"purchaseDate\":\"2023-01-01T13:14:21\",\"totalCost\":22.50,\"customerId\":1,\"orderDetails\":[{\"detailOrderId\":1,\"quantityOrdered\":1,\"productOrdered\":\"Tamal verde\",\"productPriceOrdered\":12.50,\"productLine\":\"Food\"},{\"detailOrderId\":2,\"quantityOrdered\":1,\"productOrdered\":\"Café\",\"productPriceOrdered\":10.00,\"productLine\":\"Drink\"}]},{\"orderId\":2,\"purchaseDate\":\"2023-01-03T08:30:45\",\"totalCost\":50.00,\"customerId\":1,\"orderDetails\":[{\"detailOrderId\":3,\"quantityOrdered\":2,\"productOrdered\":\"Tamal rajas\",\"productPriceOrdered\":13.00,\"productLine\":\"Food\"},{\"detailOrderId\":4,\"quantityOrdered\":1,\"productOrdered\":\"Atole galleta\",\"productPriceOrdered\":14.00,\"productLine\":\"Drink\"},{\"detailOrderId\":5,\"quantityOrdered\":1,\"productOrdered\":\"Café\",\"productPriceOrdered\":10.00,\"productLine\":\"Drink\"}]}]";
        List<OrderHistoryDTO> history=mapper.readValue(json,new TypeReference<List<OrderHistoryDTO>>(){});
        when(iOrderService.getShoppingHistory(anyLong())).thenReturn(history);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/tamalli/orders/history/1"))
                .andExpect(status().isOk()); //check is response status is 200

    }


    @Test
    void getApiAllOrdersPaidTest() throws Exception{

        String json="[{\"orderId\":1,\"purchaseDate\":\"2023-01-01T13:14:21\",\"totalCost\":22.50,\"customerId\":1},{\"orderId\":2,\"purchaseDate\":\"2023-01-03T08:30:45\",\"totalCost\":50.00,\"customerId\":1}]";
        List<OrderDTO> orders =mapper.readValue(json,new TypeReference<List<OrderDTO>>(){});
        when(iOrderService.getAllOrdersPaidStore()).thenReturn(orders);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/tamalli/orders"))
                .andExpect(status().isOk()); //check is response status is 200


    }

}
