package com.accenture.tamalli.services;

import com.accenture.tamalli.models.Customer;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ICustomerService {

    List<Customer> getAllCustomers();

    Customer getCustomerById(Long id);

    Customer addNewCustomer(Customer customer);

    void deleteCustomerById(Long id);

    Customer fullUpdateCustomer(Customer updatedCustomer);

    Customer partialUpdateCustomer(Map<String,Object> customerChanges, Long customerId);

    Customer getCustomerId(String email, String password);

}
