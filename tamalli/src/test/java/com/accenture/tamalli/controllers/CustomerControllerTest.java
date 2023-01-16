package com.accenture.tamalli.controllers;

import com.accenture.tamalli.dto.customers.CustomerDTO;
import com.accenture.tamalli.models.Customer;
import com.accenture.tamalli.services.ICustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/*we will use @ExtendWIth(SpringExtension.class) annotation to register the test class as a Spring Unit Test
@WebMvcTest(ProductController.class) annotation that will enable us to write a Spring MVC test that focuses only on Spring MVC components.
@WebMvcTest(ProductController.class) will also disable full auto-configuration and instead apply only configuration relevant to MVC tests. Without other components like Services or Repositories in ApplicationContext, we will test the controller in isolation.
*/
@ExtendWith(SpringExtension.class)
@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {

    //MockMvc component is provided by Spring to make calls to the Spring MVC API and assert different properties like status code and received response.
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    private  CustomerDTO customer;
    private  List<CustomerDTO> customers;
    @MockBean
    private ICustomerService icustomerService; // This will mock a Spring Bean and Inject it where is needed

    @BeforeEach
    public void createCustomersDTO(){

        customer = new CustomerDTO(1L,"Mario","Alvarez","mario@gmail.com","5512234556","Chimalhuacan, Estado de México",3L);
        customers= new ArrayList<>();
        customers.add(customer);
    }

    @Test
    void getAllCustomersTest() throws Exception {


        when(icustomerService.getAllCustomers()).thenReturn(customers);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/tamalli/customers"))
                .andExpect(status().isOk()); //check is response status is 200

        verify(icustomerService, times(1)).getAllCustomers();
    }

    @Test
    void getCustomerByIdTest() throws Exception{

        when(icustomerService.getCustomerById(1L)).thenReturn(customer);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/tamalli/customers/1"))
                .andExpect(status().isOk()); //check is response status is 200

        verify(icustomerService, times(1)).getCustomerById(1L);
    }


    @Test
    void deleteCustomerByIdTest() throws Exception{

        when(icustomerService.deleteCustomerById(1L)).thenReturn("Customer with id:1 has been deleted successfully");

        MvcResult mvcResult=mockMvc.perform(MockMvcRequestBuilders.delete("/api/tamalli/customers/1"))
                .andExpect(status().isOk()) //check is response status is 200
                .andReturn();
        assertEquals("Customer with id:1 has been deleted successfully", mvcResult.getResponse().getContentAsString());
    }

    @Test
    void addNewCustomerTest() throws Exception{


        String json="{\n" +
                "    \"customerId\":100,\n" +
                "    \"address\":\"monterrey\",\n" +
                "    \"email\":\"rola@gmail.com\",\n" +
                "    \"firstName\":\"Rolando\",\n" +
                "    \"lastName\":\"Garcia\",\n" +
                "    \"password\":\"1469o\",\n" +
                "    \"phoneNumber\":\"16468645\"\n" +
                "}";

        CustomerDTO customer = new CustomerDTO();
        when(icustomerService.addNewCustomer(any(Customer.class))).thenReturn(customer);

        MockHttpServletRequestBuilder mockRequest =
                MockMvcRequestBuilders.post("/api/tamalli/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(mockRequest)
                .andExpect(status().isCreated());
    }



    @Test
    void fullUpdateCustomerTest() throws Exception{



        String json ="{\n" +
                "    \"customerId\":1,\n" +
                "    \"address\":\"monterrey\",\n" +
                "    \"email\":\"rola@gmail.com\",\n" +
                "    \"firstName\":\"Rolando\",\n" +
                "    \"lastName\":\"Garcia\",\n" +
                "    \"password\":\"1469o\",\n" +
                "    \"phoneNumber\":\"16468645\"\n" +
                "}";

        Customer changes=mapper.readValue(json,Customer.class);
        when(icustomerService.fullUpdateCustomer(changes,1L)).thenReturn(customer);

        MockHttpServletRequestBuilder mockRequest =
                MockMvcRequestBuilders.put("/api/tamalli/customers/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk()); //check is response status is 200
    }

    @Test
    void updatePartialApiCustomerTest() throws Exception{

        //Update customer 1
        Map<String, Object> changes = new HashMap<>();
        changes.put("address","monterrey");
        changes.put("password","1469o");


        when(icustomerService.partialUpdateCustomer(changes,1L)).thenReturn(customer);

        MockHttpServletRequestBuilder mockRequest =
                MockMvcRequestBuilders.patch("/api/tamalli/customers/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(changes))
                        .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk()); //check is response status is 200
    }

    @Test
    void updatePartialApiCustomer2Test() throws Exception{

        //Update customer 1
        Map<String, Object> changes = new HashMap<>();
        changes.put("password","1469o");
        changes.put("email","roca@gmail.com");
        changes.put("address","calle nilo");
        changes.put("phone number", "551213457896");
        changes.put("FirstName", "Raul");
        changes.put("LastName","Gom");


        when(icustomerService.partialUpdateCustomer(changes,1L)).thenReturn(customer);

        MockHttpServletRequestBuilder mockRequest =
                MockMvcRequestBuilders.patch("/api/tamalli/customers/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(changes))
                        .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk()); //check is response status is 200
    }



}
