package com.accenture.tamalli.services;

import com.accenture.tamalli.dto.customers.CustomerDTO;
import com.accenture.tamalli.models.Customer;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ICustomerService {

    List<CustomerDTO> getAllCustomers();

    CustomerDTO getCustomerById(Long id);

    CustomerDTO addNewCustomer(Customer customer);

    String deleteCustomerById(Long id);

    CustomerDTO fullUpdateCustomer(Customer newCustomerChanges, Long customerId);

    CustomerDTO partialUpdateCustomer(Map<String,Object> newCustomerChanges, Long customerId);

    CustomerDTO getCustomerId(String email, String password);

}
