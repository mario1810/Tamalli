package com.accenture.tamalli.services;

import com.accenture.tamalli.dto.customers.CustomerDTO;
import com.accenture.tamalli.exceptions.CustomerException;
import com.accenture.tamalli.exceptions.NotFoundCustomerException;
import com.accenture.tamalli.models.Customer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;


/*
* The code examples in this tutorial use the @ExtendWith annotation to tell JUnit 5 to enable Spring support.
* As of Spring Boot 2.1, we no longer need to load the SpringExtension because it's included as a meta annotation in
* the Spring Boot test annotations like @DataJpaTest, @WebMvcTest, and @SpringBootTest.
* */
//@ExtendWith(SpringExtension.class)
@SpringBootTest
public class CustomerServiceTest {

    @Autowired
    private ICustomerService iCustomerService;

    private List<CustomerDTO> expectedCustomer;

    private Customer inputCustomer;

    @BeforeEach
    public void setup(){
        expectedCustomer = new ArrayList<>();
        expectedCustomer.add(new CustomerDTO(1L,"Mario","Alvarez","mario@gmail.com","5512234556","Chimalhuacan, Estado de México",3L));
        expectedCustomer.add(new CustomerDTO(2L,"Juan","Cota","juan@gmail.com","5512234577","Villa, La paz",4L));
        expectedCustomer.add(new CustomerDTO(3L,"Ramiro","Nuñes","ramiro@gmail.com","5512234588","Flores, Guadalajara",5L));
        //new customer
        expectedCustomer.add(new CustomerDTO(4L,"Rolando","Garcia","rola@gmail.com","16468645","monterrey",6L));
        //full update to customer 1 (id has to be 1L)
        expectedCustomer.add(new CustomerDTO(1L,"Rolando","Garcia","rola@gmail.com","16468645","monterrey",6L));
        //partial update to customer 1 (lastName and address)
        expectedCustomer.add(new CustomerDTO(1L,"Mario","Obrador","mario@gmail.com","5512234556","Texcoco",3L));
        //get results
    }

    @Test
    @Transactional
    void getAllCustomersTest(){
        List<CustomerDTO> currentCustomer= iCustomerService.getAllCustomers();
        assertThat(currentCustomer).hasSize(3);
        assertEquals(expectedCustomer.get(0).getFirstName(),currentCustomer.get(0).getFirstName());
        assertEquals(expectedCustomer.get(1).getFirstName(),currentCustomer.get(1).getFirstName());
        assertEquals(expectedCustomer.get(2).getFirstName(),currentCustomer.get(2).getFirstName());
    }

    @Test
    @Transactional
    void getCustomerByIdTest(){
        CustomerDTO currentCustomer = iCustomerService.getCustomerById(1L);
        assertThat(currentCustomer).isNotNull();
        assertEquals(expectedCustomer.get(0).getCustomerId(),currentCustomer.getCustomerId());
        assertEquals(expectedCustomer.get(0).getEmail(),currentCustomer.getEmail());
    }

    @Test
    @Transactional
    void getCustomerByIdExceptionTest(){
        Throwable error=assertThrows(NotFoundCustomerException.class, ()->iCustomerService.getCustomerById(5L));
        assertEquals("there is no customer with id:5",error.getMessage());
    }

    @Test
    @Transactional
    void addNewCustomerTest(){
        inputCustomer = new Customer();
        inputCustomer.setCustomerId(100L);
        inputCustomer.setAddress("monterrey");
        inputCustomer.setEmail("rola@gmail.com");
        inputCustomer.setFirstName("Rolando");
        inputCustomer.setLastName("Garcia");
        inputCustomer.setPassword("14690");
        inputCustomer.setPhoneNumber("16468645");
        inputCustomer.setOrders(null);

        CustomerDTO currentCustomer = iCustomerService.addNewCustomer(inputCustomer);
        assertThat(currentCustomer).isNotNull();
        assertEquals(expectedCustomer.get(3).getCustomerId(),currentCustomer.getCustomerId());
        assertEquals(expectedCustomer.get(3).getEmail(),currentCustomer.getEmail());
    }

    @Test
    @Transactional
    void addNewCustomerExceptionTest(){

        //email is already registered
        inputCustomer = new Customer();
        inputCustomer.setCustomerId(100L);
        inputCustomer.setAddress("monterrey");
        inputCustomer.setEmail("mario@gmail.com");
        inputCustomer.setFirstName("Rolando");
        inputCustomer.setLastName("Garcia");
        inputCustomer.setPassword("14690");
        inputCustomer.setPhoneNumber("16468645");
        inputCustomer.setOrders(null);

        Throwable error=assertThrows(CustomerException.class, ()->iCustomerService.addNewCustomer(inputCustomer));
        assertEquals("Already exists a customer with the same email",error.getMessage());
    }

    @Test
    @Transactional
    void deleteCustomerByIdTest(){
        String message = iCustomerService.deleteCustomerById(1L);

        assertThat(message).isNotNull();
        assertEquals( "Customer with id:1 has been deleted successfully",message);
    }
    @Test
    @Transactional
    void deleteCustomerByIdExceptionTest(){
        Throwable error=assertThrows(NotFoundCustomerException.class, ()->iCustomerService.deleteCustomerById(5L));
        assertEquals("there is no customer with id:5",error.getMessage());
    }

    @Test
    @Transactional
    void fullUpdateCustomerTest(){

        inputCustomer = new Customer();
        inputCustomer.setAddress("monterrey");
        inputCustomer.setEmail("rola@gmail.com");
        inputCustomer.setFirstName("Rolando");
        inputCustomer.setLastName("Garcia");
        inputCustomer.setPassword("14690");
        inputCustomer.setPhoneNumber("16468645");
        
        CustomerDTO currentCustomer= iCustomerService.fullUpdateCustomer(inputCustomer,1L);
        assertThat(currentCustomer).isNotNull();
        assertEquals(expectedCustomer.get(4).getCustomerId(),currentCustomer.getCustomerId());
        assertEquals(expectedCustomer.get(4).getEmail(),currentCustomer.getEmail());

    }

    @Test
    @Transactional
    void fullUpdateCustomerExceptionTest(){

        inputCustomer = new Customer();
        inputCustomer.setAddress("monterrey");
        inputCustomer.setEmail("rola@gmail.com");
        inputCustomer.setFirstName("Rolando");
        inputCustomer.setLastName("Garcia");
        inputCustomer.setPassword("14690");
        inputCustomer.setPhoneNumber("16468645");

        Throwable error=assertThrows(NotFoundCustomerException.class, ()->iCustomerService.fullUpdateCustomer(inputCustomer,5L));
        assertEquals("there is no customer with id:5",error.getMessage());

    }

    @Test
    @Transactional
    void partialUpdateCustomerTest(){

        Map<String ,Object> changes= new HashMap<>();
        changes.put("address","Texcoco");
        changes.put("lastName","Obrador");

        CustomerDTO currentCustomer= iCustomerService.partialUpdateCustomer(changes, 1L);
        assertEquals(expectedCustomer.get(5).getCustomerId(),currentCustomer.getCustomerId());
        assertEquals(expectedCustomer.get(5).getAddress(),currentCustomer.getAddress());
        assertEquals(expectedCustomer.get(5).getLastName(),currentCustomer.getLastName());
    }

    @Test
    @Transactional
    void findCustomerTest(){

        CustomerDTO currentCustomer = iCustomerService.findCustomer("mario@gmail.com","123");

        assertEquals(1L,currentCustomer.getCustomerId());
        assertEquals("Mario",currentCustomer.getFirstName());
        assertEquals("Alvarez",currentCustomer.getLastName());
    }

    @Test
    @Transactional
    void findCustomerExceptionTest(){

        Throwable error=assertThrows(CustomerException.class, ()->iCustomerService.findCustomer("mario@gmaail.com","123"));
        assertEquals("Email or password  is incorrect",error.getMessage());

    }
}
