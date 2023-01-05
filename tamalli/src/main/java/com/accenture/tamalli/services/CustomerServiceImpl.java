package com.accenture.tamalli.services;

import com.accenture.tamalli.exceptions.CustomerException;
import com.accenture.tamalli.dto.customers.CustomerDTO;
import com.accenture.tamalli.exceptions.OrderException;
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
   public CustomerDTO getCustomerById(Long customerId) throws RuntimeException {
      Customer customer=  iCustomerRepository.findByCustomerId(customerId).orElseThrow(()->new CustomerException("there is no customer with id:"+ customerId));
      return mapCustomerToCustomerDTO(customer);
   }

   private void createEmptyShoppingCart(Customer customer){
      Order emptyOrder = new Order();
      emptyOrder.setCustomer(customer);
      emptyOrder.setPaid(false);
      iOrderRepository.saveAndFlush(emptyOrder);
   }

   @Transactional
   @Override
   public CustomerDTO addNewCustomer(Customer newCustomer) throws RuntimeException{
      //Does a user with the same email and password exist?
      if(newCustomer==null || !validateNewCustomer(newCustomer))
            throw  new CustomerException("There  is no enough information about the customer to be created");
      String email=newCustomer.getEmail();
      //Is there a customer with the same email and password?
      if(!Objects.isNull(iCustomerRepository.findFirstByEmail(email).orElse(null)))
            throw  new CustomerException("Already exists a customer with the same email");
      //Add the new customer to the database
      newCustomer.setCustomerId(null);
      newCustomer  =  iCustomerRepository.saveAndFlush(newCustomer);
      //Create an empty order to the new customer
      createEmptyShoppingCart(newCustomer);
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

   @Transactional
   @Override
   public String deleteCustomerById(Long customerId) throws RuntimeException{
      Customer customerToDelete= iCustomerRepository.findByCustomerId(customerId).orElseThrow(()->new CustomerException("there is no customer with id:"+ customerId));
      //It's important to keep information related with the orders of the users, so I am going to eliminate the information about him/her.(Update with null values to eliminate who he/she is but what he/she did)
      List<Order> customerOrders=customerToDelete.getOrders();

      //Disassociate Orders that have been paid from Customer
      for(int i=0; i<customerOrders.size();i++){
         Order customerOrder=customerOrders.get(i);
         if(customerOrder.getPaid()){
               customerOrder.setCustomer(null);
         }
      }

      //Update customer side relationship with only the shopping cart
      List<Order> orders= customerOrders.stream().filter(order -> order.getCustomer()!=null).collect(Collectors.toList());
      customerToDelete.setOrders(orders);
      iCustomerRepository.saveAndFlush(customerToDelete);
      //Delete customer and shopping cart (no orders that have been already paid)
      iCustomerRepository.delete(customerToDelete);
      return "Customer with id:"+customerId+" has been deleted successfully";
   }

   @Override
   public CustomerDTO fullUpdateCustomer(Customer updateCustomer) throws RuntimeException{
      //is there an ID?
      Long customerId=updateCustomer.getCustomerId();
      if(customerId==null)
         throw new CustomerException("there is no id in the body request to identify a customer");
      //Does the ID exist?
      Customer currentCustomer=iCustomerRepository.findByCustomerId(customerId).orElseThrow(()->new CustomerException("there is no customer with id:"+ customerId));
      //validate customer
      if(!validateNewCustomer(updateCustomer))
         throw  new CustomerException("There  is no enough information about the customer to be fully updated");
      //The new email is in use?
      String email=updateCustomer.getEmail();
      Customer emailOwnerCustomer=iCustomerRepository.findFirstByEmail(email).orElse(null);
      //if updateCustomer is the owner of the email or nobody uses that email, update
      if( emailOwnerCustomer==null || emailOwnerCustomer.getCustomerId().equals(updateCustomer.getCustomerId())){
         //Update him/her
         updateCustomer.setOrders(currentCustomer.getOrders());
         currentCustomer= iCustomerRepository.saveAndFlush(updateCustomer);
      }
      else
         throw new CustomerException("The new email: "+email+" is already in use");
      return mapCustomerToCustomerDTO(currentCustomer);
   }

   @Override
   public CustomerDTO partialUpdateCustomer(Map<String,Object> customerChanges, Long customerId) throws RuntimeException{
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
                  Customer emailOwnerCustomer=iCustomerRepository.findFirstByEmail((String)value).orElse(null);
                  //if updateCustomer is the owner of the email or nobody uses that email, update
                  if( emailOwnerCustomer==null || emailOwnerCustomer.getCustomerId().equals(customer.getCustomerId())){
                     //Update him/her
                     customer.setEmail((String)value);
                  }
                  else
                     throw new CustomerException("The new email: "+(String)value+" is already in use");
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
   public CustomerDTO getCustomerId(String email, String password) throws RuntimeException{
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
         if(order==null)
            throw new OrderException("create shopping cart, please");
      filteredCustomer.setOrderId(order.getOrderId());
      return filteredCustomer;
   }
}
