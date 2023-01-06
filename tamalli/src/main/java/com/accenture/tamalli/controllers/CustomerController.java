package com.accenture.tamalli.controllers;

import com.accenture.tamalli.dto.customers.CustomerDTO;
import com.accenture.tamalli.models.Customer;
import com.accenture.tamalli.services.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path="/api/tamalli")
public class CustomerController {

    @Autowired
    private ICustomerService iCustomerService;

  

    @GetMapping("/customers")
    public List<CustomerDTO> getApiAllCustomers(){
        return  iCustomerService.getAllCustomers();
    }


    @GetMapping("/customers/{id}")
    public CustomerDTO getApiCustomer(@PathVariable Long id){
        return  iCustomerService.getCustomerById(id);
    }

    @GetMapping("/customers/{email}/{password}")
    public CustomerDTO getApiIdCustomer(@PathVariable String email, @PathVariable String password){
        return iCustomerService.getCustomerId(email, password);
    }

    @DeleteMapping("/customers/{id}")
    public String deleteApiCustomer(@PathVariable Long id){
        return iCustomerService.deleteCustomerById(id);
    }

    @PostMapping("/customers/{id}")
    public CustomerDTO addApiNewCustomer(@RequestBody Customer customer){
         return iCustomerService.addNewCustomer(customer);
    }

    @PutMapping("/customers/{id}")
    public CustomerDTO updateApiCustomer(@RequestBody Customer customer){
        return  iCustomerService.fullUpdateCustomer(customer);
    }

    @PatchMapping("/customers/{id}")
    public CustomerDTO updatePartialApiCustomer(@RequestBody Map<String, Object> changes, @PathVariable Long id){
        return iCustomerService.partialUpdateCustomer(changes,id);
    }
}
