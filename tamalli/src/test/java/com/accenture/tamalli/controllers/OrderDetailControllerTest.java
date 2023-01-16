package com.accenture.tamalli.controllers;

import com.accenture.tamalli.dto.orderDetails.ProductOrderDTO;
import com.accenture.tamalli.services.IOrderDetailService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(OrderDetailController.class)
public class OrderDetailControllerTest {
    @Autowired
    private MockMvc mockMvc;


    static ObjectMapper mapper;

    @MockBean
    private IOrderDetailService iOrderDetailService;
    @BeforeAll
    public static void setup(){
        mapper = new ObjectMapper();
    }

    @Test
    void addApiProductToShoppingCartTest() throws Exception{

        String inputJson="{\n" +
                "    \"productId\":1,\n" +
                "    \"quantity\":2\n" +
                "}";

        String json="{\"orderId\":5,\"detailOrderId\":9,\"quantityOrdered\":2,\"productOrdered\":\"Tamal verde\",\"productPriceOrdered\":12.50,\"productLine\":\"Food\"}";
        ProductOrderDTO product =mapper.readValue(json, ProductOrderDTO.class);
        when(iOrderDetailService.addProductToShoppingCart(1L, 1L,2)).thenReturn(product);

        MockHttpServletRequestBuilder mockRequest =
                MockMvcRequestBuilders.post("/api/tamalli/orderDetail/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(inputJson)
                        .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(mockRequest)
                .andExpect(status().isCreated());


    }

    @Test
    void removeApiProductFromShoppingCartTest()throws Exception{

        when(iOrderDetailService.removeProductFromShoppingCart(1L, 1L)).thenReturn("the product with Id:1L has been deleted from your shopping bag");

        MvcResult mvcResult=mockMvc.perform(MockMvcRequestBuilders.delete("/api/tamalli/orderDetail/1/1"))
                .andExpect(status().isOk()) //check is response status is 200
                .andReturn();
        assertEquals("the product with Id:1L has been deleted from your shopping bag", mvcResult.getResponse().getContentAsString());
    }

    @Test
    void removeApiAllProductsFromShoppingCartTest() throws Exception{

        when(iOrderDetailService.removeAllProductsFromShoppingCart(1L)).thenReturn("All products have been deleted from your shopping bag");

        MvcResult mvcResult=mockMvc.perform(MockMvcRequestBuilders.delete("/api/tamalli/orderDetail/all/1"))
                .andExpect(status().isOk()) //check is response status is 200
                .andReturn();
        assertEquals("All products have been deleted from your shopping bag", mvcResult.getResponse().getContentAsString());
    }

    @Test
    void changeApiProductQuantityAtShoppingCartTest() throws Exception{

        String inputJson="{\n" +
                "    \"productId\":1,\n" +
                "    \"quantity\":2\n" +
                "}";
        String json ="{\"orderId\":3,\"detailOrderId\":6,\"quantityOrdered\":2,\"productOrdered\":\"Tamal verde\",\"productPriceOrdered\":12.50,\"productLine\":\"Food\"}";
        ProductOrderDTO updtaedProduct= mapper.readValue(json,ProductOrderDTO.class);
        when(iOrderDetailService.changeProductQuantityAtShoppingCart(1L,1L,2)).thenReturn(updtaedProduct);


        MockHttpServletRequestBuilder mockRequest =
                MockMvcRequestBuilders.put("/api/tamalli/orderDetail/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(inputJson)
                        .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk()); //check is response status is 200

    }

}
