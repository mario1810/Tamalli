package com.accenture.tamalli.controllers;

import com.accenture.tamalli.dto.customers.CustomerDTO;
import com.accenture.tamalli.exceptions.BadRequestCustomerException;
import com.accenture.tamalli.exceptions.CustomerException;
import com.accenture.tamalli.exceptions.OrderDetailException;
import com.accenture.tamalli.models.Customer;
import com.accenture.tamalli.models.OrderDetail;
import com.accenture.tamalli.services.ICustomerService;
import com.accenture.tamalli.services.IOrderDetailService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
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

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest({CustomerController.class,OrderDetailController.class})
public class ExceptionTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    private  CustomerDTO customer;
    private List<CustomerDTO> customers;
    @MockBean
    private ICustomerService icustomerService; // This will mock a Spring Bean and Inject it where is needed

    @MockBean
    private  IOrderDetailService iOrderDetailService;

    private List<OrderDetail> shoppingList;


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

        CustomerDTO customer = new CustomerDTO();
        when(icustomerService.addNewCustomer(any(Customer.class))).thenThrow( new CustomerException("email is in the database"));

        MockHttpServletRequestBuilder mockRequest =
                MockMvcRequestBuilders.post("/api/tamalli/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(mockRequest)
                .andExpect(status().isBadRequest())  //Actual,//expected
                .andExpect(jsonPath("$.message", Matchers.equalTo("email is in the database")))
                .andExpect(jsonPath("$.className",Matchers.equalTo("class com.accenture.tamalli.exceptions.CustomerException")))
                .andExpect(jsonPath("$.httpStatus",Matchers.equalTo("BAD_REQUEST")))
                .andExpect(jsonPath("$.timestamp",Matchers.notNullValue()));
    }

    @Test
    void removeApiAllProductsFromShoppingCartTest() throws Exception{

        when(iOrderDetailService.removeAllProductsFromShoppingCart(1L)).thenThrow(new OrderDetailException("The shopping cart is already empty"));

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/tamalli/orderDetail/all/1"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", Matchers.equalTo("The shopping cart is already empty")))
                .andExpect(jsonPath("$.className",Matchers.equalTo("class com.accenture.tamalli.exceptions.OrderDetailException")))
                .andExpect(jsonPath("$.httpStatus",Matchers.equalTo("BAD_REQUEST")))
                .andExpect(jsonPath("$.timestamp",Matchers.notNullValue()));
    }




}