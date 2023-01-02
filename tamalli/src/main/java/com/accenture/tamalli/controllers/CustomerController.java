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
        List<Customer> customers= iCustomerService.getAllCustomers();
        List<CustomerDTO> customersDTO=customers.stream()
                                                .map((customer)->filterCustomer(customer))
                                                .collect(Collectors.toList());
        return customersDTO;
    }

    @GetMapping("/{id}")
    public CustomerDTO getApiCustomer(@PathVariable Long id){
        Customer customer= iCustomerService.getCustomerById(id);
        return filterCustomer(customer);
    }

    @GetMapping("/getId")
    public CustomerDTO getApiIdCustomer(@RequestBody Customer customer){
        customer =iCustomerService.getCustomerId(customer.getEmail(), customer.getPassword());
        return filterCustomer(customer);
    }

    @DeleteMapping("delete/{id}")
    public void deleteApiCustomer(@PathVariable Long id){
        iCustomerService.deleteCustomerById(id);
    }

    @PostMapping("/add")
    public CustomerDTO addApiNewCustomer(@RequestBody Customer customer){
         customer= iCustomerService.addNewCustomer(customer);
        return filterCustomer(customer);

    }

    @PutMapping("/update")
    public CustomerDTO updateApiCustomer(@RequestBody Customer customer){
        customer=  iCustomerService.fullUpdateCustomer(customer);
        return filterCustomer(customer);
    }

    @PatchMapping("/partial/update/{id}")
    public CustomerDTO updateApiPartialCustomer(@RequestBody Map<String, Object> changes, @PathVariable Long id){
        Customer customer=  iCustomerService.partialUpdateCustomer(changes,id);
        return filterCustomer(customer);
    }

    /***
     *  Map a Customer Object into A costumerDTO object (hidden fields )
     * @param customer object to be mapped
     * @return A CustomerDTO Object
     */
    private CustomerDTO filterCustomer(Customer customer){
        CustomerDTO filteredCustomer = new CustomerDTO();
        filteredCustomer.setCustomerId(customer.getCustomerId());
        filteredCustomer.setFirstName(customer.getFirstName());
        filteredCustomer.setLastName(customer.getLastName());
        filteredCustomer.setEmail(customer.getEmail());
        filteredCustomer.setPhoneNumber(customer.getPhoneNumber());
        filteredCustomer.setAddress(customer.getAddress());
        return filteredCustomer;
    }





}
