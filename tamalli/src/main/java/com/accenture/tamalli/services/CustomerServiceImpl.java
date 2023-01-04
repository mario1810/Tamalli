package com.accenture.tamalli.services;

import com.accenture.tamalli.exceptions.CustomerException;
import com.accenture.tamalli.dto.customers.CustomerDTO;
import com.accenture.tamalli.models.Customer;
import com.accenture.tamalli.models.Order;
import com.accenture.tamalli.repositories.ICustomerRepository;
import com.accenture.tamalli.repositories.IOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements ICustomerService{

   @Autowired
   private ICustomerRepository iCustomerRepository;

   @Autowired
   private IOrderRepository iOrderRepository;


   @Override
   public List<CustomerDTO> getAllCustomers() {
      List<Customer> customers= iCustomerRepository.findAll();
      List<CustomerDTO> customersDTO=customers.stream()
              .map((customer)-> mapCustomerToCustomerDTO(customer))
              .collect(Collectors.toList());
      return customersDTO;
   }

   @Override
   public CustomerDTO getCustomerById(Long customerId) {
      Customer customer=  iCustomerRepository.findByCustomerId(customerId).orElseThrow(()->new CustomerException("there is no customer with id:"+ customerId));
      return mapCustomerToCustomerDTO(customer);
   }

   @Transactional
   @Override
   public CustomerDTO addNewCustomer(Customer newCustomer) {
      //Does a user with the same email and password exist?
      if(!validateNewCustomer(newCustomer))
            throw  new CustomerException("There  is no enough information about the customer to be created");
      String email=newCustomer.getEmail();
      //Is there a customer with the same email and password?
      if(!Objects.isNull(iCustomerRepository.findFistByEmail(email).orElse(null)))
            throw  new CustomerException("Already exists a customer with the same email");
      //Add the new customer to the database
      newCustomer.setCustomerId(null);
      newCustomer  =  iCustomerRepository.saveAndFlush(newCustomer);
      //Create an empty order to the new customer
      Order emptyOrder = new Order();
      emptyOrder.setCustomer(newCustomer);
      iOrderRepository.saveAndFlush(emptyOrder);
      return mapCustomerToCustomerDTO(newCustomer);
   }

   /***
    * Function that check if the fields of the customer are present (excepts customerId)
    * @param customer Object to be validate
    * @return True if everything is ok, false otherwise;
    */
   private boolean validateNewCustomer(Customer customer){

      if(customer.getFirstName().isEmpty())
            return false;
      if(customer.getLastName().isEmpty())
            return false;
      if(customer.getEmail().isEmpty())
            return false;
      if(customer.getPassword().isEmpty())
            return false;
      if(customer.getPhoneNumber().isEmpty())
            return false;
      if(customer.getAddress().isEmpty())
            return false;
      return true;
   }

   @Override
   public void deleteCustomerById(Long customerId) {
      Customer customerToDelete= iCustomerRepository.findByCustomerId(customerId).orElseThrow(()->new CustomerException("there is no customer with id:"+ customerId));
      //It's important to keep information related with the orders of the users, so I am going to eliminate the information about him/her.(Update with null values to eliminate who he/she is but what he/she did)
      //iCustomerRepository.delete(storedCustomer);
      Customer emptyCustomer= new Customer();
      //Remember the  customer's id
      emptyCustomer.setCustomerId(customerToDelete.getCustomerId());
      iCustomerRepository.saveAndFlush(emptyCustomer);
   }

   @Override
   public CustomerDTO fullUpdateCustomer(Customer updatedCustomer) {
      //is there an ID?
      Long customerId=updatedCustomer.getCustomerId();
      if(customerId==null)
         throw new CustomerException("there is no id in the body request to identify a customer");
      //Does the ID exist?
      Customer currentCustomer=iCustomerRepository.findByCustomerId(customerId).orElseThrow(()->new CustomerException("there is no customer with id:"+ customerId));
      //validate customer
      if(!validateNewCustomer(updatedCustomer))
         throw  new CustomerException("There  is no enough information about the customer to be fully updated");
      //The new email is in use?
      String email=updatedCustomer.getEmail();
      iCustomerRepository.findFistByEmail(email).orElseThrow(()->new CustomerException("The new email: "+email+" is already in use"));
      //Update him/her
      updatedCustomer.setOrders(currentCustomer.getOrders());
      updatedCustomer= iCustomerRepository.saveAndFlush(updatedCustomer);
      return mapCustomerToCustomerDTO(updatedCustomer);
   }

   @Override
   public CustomerDTO partialUpdateCustomer(Map<String,Object> customerChanges, Long customerId) {
      if(customerId==null)
         throw new CustomerException("there is no id to identify a customer");
      //Does the ID exist?
      Customer currentCustomer=iCustomerRepository.findByCustomerId(customerId).orElseThrow(()->new CustomerException("there is no customer with id:"+ customerId));
      //Update fields
      updateCustomerFields(currentCustomer, customerChanges);
      //Commit changes
      Customer customer =iCustomerRepository.saveAndFlush(currentCustomer);
      return mapCustomerToCustomerDTO(customer);
   }

   private void updateCustomerFields(Customer customer, Map<String,Object>changes){

      changes.forEach((change,value)->{
         switch (change) {
            case "firstName":
               if(value!=null)
                  customer.setFirstName((String)value);
               break;
            case "lastName":
               if(value!=null)
                  customer.setLastName((String)value);
               break;
            case "email":
               if(value!=null){
                  iCustomerRepository.findFistByEmail((String)value).orElseThrow(()->new CustomerException("The new email: "+(String) value+" is already in use"));
                  customer.setEmail((String)value);
               }
               break;
            case "password":
               if(value!=null)
                  customer.setPassword((String)value);
               break;
            case "phoneNumber":
               if(value!=null)
                  customer.setPhoneNumber((String)value);
               break;
            case "address":
               if(value!=null)
                  customer.setAddress((String)value);
               break;
         }
      });


   }

   @Override
   public CustomerDTO getCustomerId(String email, String password) {
      Customer customer= iCustomerRepository.findFistByEmailAndPassword(email,password).orElseThrow(()-> new CustomerException("Email or password  is incorrect"));
      return mapCustomerToCustomerDTO(customer);
   }


   /***
    *  Map a Customer Object into A costumerDTO object (hidden fields )
    * @param customer object to be mapped
    * @return A CustomerDTO Object
    */
   private CustomerDTO mapCustomerToCustomerDTO(Customer customer){
      CustomerDTO filteredCustomer = new CustomerDTO();
      filteredCustomer.setCustomerId(customer.getCustomerId());
      filteredCustomer.setFirstName(customer.getFirstName());
      filteredCustomer.setLastName(customer.getLastName());
      filteredCustomer.setEmail(customer.getEmail());
      filteredCustomer.setPhoneNumber(customer.getPhoneNumber());
      filteredCustomer.setAddress(customer.getAddress());

      //find order
      Order order = iOrderRepository.findFirstByCustomerCustomerIdAndPaidFalse(customer.getCustomerId()).orElse(null);
      filteredCustomer.setOrderId(order.getOrderId());
      return filteredCustomer;
   }
}
