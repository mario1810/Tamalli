package com.accenture.tamalli.controllers;

import com.accenture.tamalli.exceptions.*;
import com.accenture.tamalli.models.Customer;
import com.accenture.tamalli.services.ICustomerService;
import com.accenture.tamalli.services.IOrderDetailService;
import com.accenture.tamalli.services.IProductDescriptionService;

import org.hamcrest.Matchers;
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

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest({CustomerController.class,OrderDetailController.class, ProductDescriptionController.class})
public class ExceptionTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ICustomerService icustomerService; // This will mock a Spring Bean and Inject it where is needed

    @MockBean
    private  IOrderDetailService iOrderDetailService;

    @MockBean
    private IProductDescriptionService iProductDescriptionService;



    @Test
    void addNewCustomerCustomerExceptionTest() throws Exception{


        String json="{\n" +
                "    \"customerId\":100,\n" +
                "    \"address\":\"monterrey\",\n" +
                "    \"email\":\"mario@gmail.com\",\n" +
                "    \"firstName\":\"Rolando\",\n" +
                "    \"lastName\":\"Garcia\",\n" +
                "    \"password\":\"1469o\",\n" +
                "    \"phoneNumber\":\"16468645\"\n" +
                "}";

        when(icustomerService.addNewCustomer(any(Customer.class))).thenThrow( new BadRequestCustomerException("email is in the database"));

        MockHttpServletRequestBuilder mockRequest =
                MockMvcRequestBuilders.post("/api/tamalli/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(mockRequest)
                .andExpect(status().isBadRequest())  //Actual,//expected
                .andExpect(jsonPath("$.message", Matchers.equalTo("email is in the database")))
                .andExpect(jsonPath("$.className",Matchers.equalTo("class com.accenture.tamalli.exceptions.BadRequestCustomerException")))
                .andExpect(jsonPath("$.httpStatus",Matchers.equalTo("BAD_REQUEST")))
                .andExpect(jsonPath("$.timestamp",Matchers.notNullValue()));
    }

    @Test
    void removeApiAllProductsFromShoppingCartExceptionTest() throws Exception{

        when(iOrderDetailService.removeAllProductsFromShoppingCart(1L)).thenThrow(new OrderDetailException("The shopping cart is already empty"));

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/tamalli/orderDetail/all/1"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", Matchers.equalTo("The shopping cart is already empty")))
                .andExpect(jsonPath("$.className",Matchers.equalTo("class com.accenture.tamalli.exceptions.OrderDetailException")))
                .andExpect(jsonPath("$.httpStatus",Matchers.equalTo("BAD_REQUEST")))
                .andExpect(jsonPath("$.timestamp",Matchers.notNullValue()));
    }

    @Test
    void changeApiProductQuantityAtShoppingCartExceptionTest() throws Exception{

        String inputJson="{\n" +
                "    \"productId\":1,\n" +
                "    \"quantity\":2\n" +
                "}";
        when(iOrderDetailService.changeProductQuantityAtShoppingCart(1L,1L,2)).thenThrow( new ProductException("The product's price has changed so we are going to respect the previous price for the quantity you have ordered."));


        MockHttpServletRequestBuilder mockRequest =
                MockMvcRequestBuilders.put("/api/tamalli/orderDetail/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(inputJson)
                        .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(mockRequest)
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message", Matchers.equalTo("The product's price has changed so we are going to respect the previous price for the quantity you have ordered.")))
                .andExpect(jsonPath("$.className",Matchers.equalTo("class com.accenture.tamalli.exceptions.ProductException")))
                .andExpect(jsonPath("$.httpStatus",Matchers.equalTo("INTERNAL_SERVER_ERROR")))
                .andExpect(jsonPath("$.timestamp",Matchers.notNullValue()));

    }

    @Test
    void deleteProductDescriptionNotFoundExceptionTest() throws Exception{

        when(iProductDescriptionService.deleteProductDescription(10L)).thenThrow(new NotFoundProductDescriptionException("There is no a description for a product with that id"));
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/tamalli/descriptions/10"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", Matchers.equalTo("There is no a description for a product with that id")))
                .andExpect(jsonPath("$.className",Matchers.equalTo("class com.accenture.tamalli.exceptions.NotFoundProductDescriptionException")))
                .andExpect(jsonPath("$.httpStatus",Matchers.equalTo("NOT_FOUND")))
                .andExpect(jsonPath("$.timestamp",Matchers.notNullValue()));
    }


}
