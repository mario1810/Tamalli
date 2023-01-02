package com.accenture.tamalli.services;

import com.accenture.tamalli.customExceptions.CustomerException;
import com.accenture.tamalli.models.Customer;
import com.accenture.tamalli.repositories.ICustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class CustomerServiceImpl implements ICustomerService{

   @Autowired
   private ICustomerRepository iCustomerRepository;


   @Override
   public List<Customer> getAllCustomers() {
      return iCustomerRepository.findAll();
   }

   @Override
   public Customer getCustomerById(Long id) {
      return  iCustomerRepository.findByCustomerId(id).orElseThrow(()->new CustomerException("there is no customer with id:"+ id));
   }

   @Override
   public Customer addNewCustomer(Customer customer) {
      //Does a user with the same email and password exist?
      if(!validateNewCustomer(customer))
            throw  new CustomerException("There  is no enough information about the customer to be created");
      String email=customer.getEmail();
      //Is there a customer with the same email and password?
      if(!Objects.isNull(iCustomerRepository.findFistByEmail(email).orElse(null)))
            throw  new CustomerException("Already exists a customer with the same email");
      //Add the new customer to the database
      customer.setCustomerId(null);
      return iCustomerRepository.saveAndFlush(customer);
   }

   /***
    * Function that check if the fields of the customer are present (excepts customerId)
    * @param customer
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
   public void deleteCustomerById(Long id) {
      Customer storedCustomer= iCustomerRepository.findByCustomerId(id).orElseThrow(()->new CustomerException("there is no customer with id:"+ id));
      //It's important to keep information related with the orders of the users, so I am going to eliminate the information about him/her.(Update with null values to eliminate who he/she is but what he/she did)
      //iCustomerRepository.delete(storedCustomer);
      Customer emptyCustomer= new Customer();
      //Remember the  customer's id
      emptyCustomer.setCustomerId(storedCustomer.getCustomerId());
      iCustomerRepository.saveAndFlush(emptyCustomer);
   }

   @Override
   public Customer fullUpdateCustomer(Customer updatedCustomer) {
      //is there an ID?
      Long customerId=updatedCustomer.getCustomerId();
      if(customerId==null)
         throw new CustomerException("there is no id in the body request to identify a customer");
      //Does the ID exist?
      Customer currentCustomer=iCustomerRepository.findByCustomerId(customerId).orElseThrow(()->new CustomerException("there is no customer with id:"+ customerId));
      if(!validateNewCustomer(updatedCustomer))
         throw  new CustomerException("There  is no enough information about the customer to be fully updated");
      //The new email is in use?
      iCustomerRepository.findFistByEmail(updatedCustomer.getEmail()).orElseThrow(()->new CustomerException("The new email: "+updatedCustomer.getEmail()+" is already in use"));
      //Update him/her
      updatedCustomer.setOrders(currentCustomer.getOrders());
      return iCustomerRepository.saveAndFlush(updatedCustomer);
   }

   @Override
   public Customer partialUpdateCustomer(Map<String,Object> customerChanges, Long customerId) {
      if(customerId==null)
         throw new CustomerException("there is no id to identify a customer");
      //Does the ID exist?
      Customer currentCustomer=iCustomerRepository.findByCustomerId(customerId).orElseThrow(()->new CustomerException("there is no customer with id:"+ customerId));
      //Update fields
      updatedCustomerFields(currentCustomer, customerChanges);
      //Commit changes
      return iCustomerRepository.saveAndFlush(currentCustomer);
   }

   private void updatedCustomerFields(Customer customer, Map<String,Object>changes){

      changes.forEach((change,value)->{
         switch (change) {
            case "firstName":
               if(value!=null)
                  customer.setFirstName((String)value);
               break;
            case "lastname":
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
   public Customer getCustomerId(String email, String password) {
      return iCustomerRepository.findFistByEmailAndPassword(email,password).orElseThrow(()-> new CustomerException("Email or password  is incorrect"));
   }
}
