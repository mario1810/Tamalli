package com.accenture.tamalli.services;

import com.accenture.tamalli.dto.orders.OrderDTO;
import com.accenture.tamalli.exceptions.CustomerException;
import com.accenture.tamalli.exceptions.OrderException;
import com.accenture.tamalli.models.Customer;
import com.accenture.tamalli.models.Order;
import com.accenture.tamalli.models.OrderDetail;
import com.accenture.tamalli.repositories.ICustomerRepository;
import com.accenture.tamalli.repositories.IOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class OrderServiceImpl implements IOrderService{

    @Autowired
    private IOrderRepository iOrderRepository;

    @Autowired
    private ICustomerRepository iCustomerRepository;


    private void createEmptyOrder(Customer customer){
        Order emptyOrder = new Order();
        emptyOrder.setPaid(false);
        emptyOrder.setCustomer(customer);
        iOrderRepository.saveAndFlush(emptyOrder);
    }

    private OrderDTO OrderToOrderDTO(Order order){
        OrderDTO orderDTO= new OrderDTO();

        orderDTO.setOrderId(order.getOrderId());
        orderDTO.setPurchaseDate(order.getPurchaseDate());
        orderDTO.setTotalCost(order.getTotalCost());
        orderDTO.setCustomerId(order.getCustomer().getCustomerId());

        return orderDTO;
    }

    @Transactional
    @Override
    public OrderDTO changeOrderStatus(Long customerId) {
        Customer customer =iCustomerRepository.findByCustomerId(customerId).orElseThrow(()->new CustomerException( "There is no a customer with Id:"+customerId));
        //Get the shopping cart order
        Order shoppingCartOrder = customer.getOrders().stream().filter((order)->!order.getPaid()).limit(1).collect(Collectors.toList()).get(0);
        //Does shopping cart exist?
        if(shoppingCartOrder==null){
            //if not
            createEmptyOrder(customer);
            throw  new OrderException("Customer with id: "+customer.getCustomerId()+" didn't have a order to be bought, a empty order for adding products has been created for him/her");
        }
        //Is shoppingCartOrder empty?
        List<OrderDetail> shoppingCartDetail=shoppingCartOrder.getOrdersDetail();
        if(shoppingCartDetail==null)
            throw  new OrderException("Order with Id:"+shoppingCartOrder.getOrderId()+ " is empty, please add products");

        //get the total cost
        BigDecimal sumCost=new BigDecimal("0.0");
        //Iterate through all products
        shoppingCartDetail.forEach((product)-> sumCost.add(sumCost).add(product.getProductPriceOrdered()));



        shoppingCartOrder.setTotalCost(sumCost.setScale(2,RoundingMode.CEILING));
        shoppingCartOrder.setPurchaseDate(LocalDateTime.now());
        shoppingCartOrder.setPaid(true);
        shoppingCartOrder=iOrderRepository.saveAndFlush(shoppingCartOrder);
        //Assign an empty order ( new shopping cart)
        createEmptyOrder(customer);

        return OrderToOrderDTO(shoppingCartOrder);
    }

    @Override
    public List<OrderDetail> getShoppingCart(Long customerId) {
        return null;
    }

    @Override
    public List<Order> getShoppingHistory(Long customerId) {
        return null;
    }


}
