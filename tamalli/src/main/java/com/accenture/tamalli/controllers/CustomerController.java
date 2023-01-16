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

@RestController
@RequestMapping(path="/api/tamalli/customers")
public class CustomerController {

    @Autowired
    private ICustomerService iCustomerService;

  

    @GetMapping("")
    public ResponseEntity<List<CustomerDTO>> getApiAllCustomers(){
        return  new ResponseEntity<>(iCustomerService.getAllCustomers(),HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> getApiCustomer(@PathVariable Long id){
        return  new ResponseEntity<>(iCustomerService.getCustomerById(id),HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteApiCustomer(@PathVariable Long id){
        return  new ResponseEntity<>(iCustomerService.deleteCustomerById(id),HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<CustomerDTO> addApiNewCustomer(@RequestBody Customer customer){
        return  new ResponseEntity<>( iCustomerService.addNewCustomer(customer),HttpStatus.CREATED);
    }

    @PutMapping("/{customerId}")
    public ResponseEntity<CustomerDTO> updateApiCustomer(@RequestBody Customer customer,@PathVariable Long customerId){
        return  new ResponseEntity<>(iCustomerService.fullUpdateCustomer(customer, customerId),HttpStatus.OK);
    }

    @PatchMapping("/{customerId}")
    public ResponseEntity<CustomerDTO> updatePartialApiCustomer(@RequestBody Map<String, Object> changes, @PathVariable Long customerId){
        return  new ResponseEntity<>(iCustomerService.partialUpdateCustomer(changes,customerId),HttpStatus.OK);
    }
}
