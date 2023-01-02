package com.accenture.tamalli.controllers;

import com.accenture.tamalli.dto.customers.CustomerDTO;
import com.accenture.tamalli.models.Customer;
import com.accenture.tamalli.services.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path="/api/customer")
public class CustomerController {

    @Autowired
    private ICustomerService iCustomerService;


    @GetMapping("/all")
    public List<CustomerDTO> getApiCustomers(){
        return  iCustomerService.getAllCustomers();
    }

    @GetMapping("/{id}")
    public CustomerDTO getApiCustomer(@PathVariable Long id){
        return  iCustomerService.getCustomerById(id);
    }

    @GetMapping("/getId")
    public CustomerDTO getApiIdCustomer(@RequestBody Customer customer){
        return iCustomerService.getCustomerId(customer.getEmail(), customer.getPassword());
    }

    @DeleteMapping("delete/{id}")
    public void deleteApiCustomer(@PathVariable Long id){
        iCustomerService.deleteCustomerById(id);
    }

    @PostMapping("/add")
    public CustomerDTO addApiNewCustomer(@RequestBody Customer customer){
         return iCustomerService.addNewCustomer(customer);
    }

    @PutMapping("/update")
    public CustomerDTO updateApiCustomer(@RequestBody Customer customer){
        return  iCustomerService.fullUpdateCustomer(customer);
    }

    @PatchMapping("/partial/update/{id}")
    public CustomerDTO updateApiPartialCustomer(@RequestBody Map<String, Object> changes, @PathVariable Long id){
        return iCustomerService.partialUpdateCustomer(changes,id);

    }






}
