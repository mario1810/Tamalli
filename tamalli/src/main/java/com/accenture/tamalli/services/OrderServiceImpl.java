package com.accenture.tamalli.services;

import com.accenture.tamalli.dto.orderDetails.OrderDetailDTO;
import com.accenture.tamalli.dto.orders.OrderDTO;
import com.accenture.tamalli.dto.orders.OrderHistoryDTO;
import com.accenture.tamalli.dto.orders.ShoppingCartDTO;
import com.accenture.tamalli.exceptions.CustomerException;
import com.accenture.tamalli.exceptions.OrderException;
import com.accenture.tamalli.models.Customer;
import com.accenture.tamalli.models.Order;
import com.accenture.tamalli.models.OrderDetail;
import com.accenture.tamalli.repositories.ICustomerRepository;
import com.accenture.tamalli.repositories.IOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements IOrderService{

    @Autowired
    private IOrderRepository iOrderRepository;

    @Autowired
    private ICustomerRepository iCustomerRepository;


    private void createEmptyShoppingCart(Customer customer){
        Order emptyShoppingCart = new Order();
        emptyShoppingCart.setPaid(false);
        emptyShoppingCart.setCustomer(customer);
        iOrderRepository.saveAndFlush(emptyShoppingCart);
    }

    private OrderDTO mapOrderToOrderDTO(Order order){
        OrderDTO orderDTO= new OrderDTO();

        orderDTO.setOrderId(order.getOrderId());
        orderDTO.setPurchaseDate(order.getPurchaseDate());
        orderDTO.setTotalCost(order.getTotalCost());
        orderDTO.setCustomerId(order.getCustomer().getCustomerId());

        return orderDTO;
    }

    @Transactional
    @Override
    public OrderDTO changeShoppingCartStatusToPaid(Long customerId) throws RuntimeException{
        Customer customer =iCustomerRepository.findByCustomerId(customerId).orElseThrow(()->new CustomerException( "There is no a customer with Id:"+customerId));
        //Get the shopping cart order
        Order shoppingCart = customer.getOrders().stream().filter((order)->!order.getPaid()).findFirst().orElse(null);
        //Does shopping cart exist?
        if(shoppingCart==null){
            //if not
            createEmptyShoppingCart(customer);
            throw  new OrderException("Customer with id: "+customer.getCustomerId()+" didn't have a order to be bought, a empty order for adding products has been created for him/her");
        }
        //Is shoppingCartOrder empty?
        List<OrderDetail> shoppingCartDetails=shoppingCart.getOrdersDetail();
        if(shoppingCartDetails==null || shoppingCartDetails.size()==0)
            throw  new OrderException("Your shopping cart with Id:"+shoppingCart.getOrderId()+ " is empty, please add products");
        //get the total cost
        BigDecimal totalCost= calculateTotalCost(shoppingCartDetails);

        shoppingCart.setTotalCost(totalCost.setScale(2,RoundingMode.CEILING));
        shoppingCart.setPurchaseDate(LocalDateTime.now());
        shoppingCart.setPaid(true);
        shoppingCart=iOrderRepository.saveAndFlush(shoppingCart);
        //Assign an empty order ( new shopping cart)
        createEmptyShoppingCart(customer);

        return mapOrderToOrderDTO(shoppingCart);
    }

    @Override
    public ShoppingCartDTO getShoppingCart(Long customerId) throws RuntimeException{
        Customer customer =iCustomerRepository.findByCustomerId(customerId).orElseThrow(()->new CustomerException( "There is no a customer with Id:"+customerId));
        //Get the shopping cart order
        Order shoppingCart = customer.getOrders().stream().filter((order)->!order.getPaid()).findFirst().orElse(null);
        //Does shopping cart exist?
        if(shoppingCart==null){
            //if not
            createEmptyShoppingCart(customer);
            throw  new OrderException("Customer with id: "+customer.getCustomerId()+" didn't have a order to be bought, a empty order for adding products has been created for him/her");
        }
        //get shopping list
        List<OrderDetailDTO> shoppingCartDetailsDTO = mapOrderDetailsListToOrderDetailsDTOList(shoppingCart.getOrdersDetail());

        //get the total cost
        BigDecimal totalCost=calculateTotalCost(shoppingCart.getOrdersDetail());
        //Sets DTO
        ShoppingCartDTO shoppingCartDTO = new ShoppingCartDTO();
        shoppingCartDTO.setShoppingCartList(shoppingCartDetailsDTO);
        shoppingCartDTO.setTotalCost(totalCost.setScale(2,RoundingMode.CEILING));

        return  shoppingCartDTO;
    }

    private  BigDecimal calculateTotalCost(List<OrderDetail> list){
        BigDecimal sumCostBD=new BigDecimal("0.0");
        //Iterate through all products
        for(int i=0; i<list.size();i++){
            BigDecimal quantityBD = new BigDecimal(list.get(i).getQuantityOrdered().toString());
            BigDecimal priceBD = new BigDecimal(list.get(i).getProductPriceOrdered().toString());
            BigDecimal auxBD=priceBD.multiply(quantityBD);
            sumCostBD=sumCostBD.add(auxBD);
        }
        return sumCostBD;
    }

    private List<OrderDetailDTO> mapOrderDetailsListToOrderDetailsDTOList(List<OrderDetail> orderDetails){

        List<OrderDetailDTO> orderDetailsDTO=null;

        orderDetailsDTO=orderDetails.stream()
                .map((orderDetail -> {
                    OrderDetailDTO orderDetailDTO = new OrderDetailDTO();
                    orderDetailDTO.setDetailOrderId(orderDetail.getDetailOrderId());
                    orderDetailDTO.setProductOrdered(orderDetail.getProductOrdered());
                    orderDetailDTO.setProductPriceOrdered(orderDetail.getProductPriceOrdered());
                    orderDetailDTO.setQuantityOrdered(orderDetail.getQuantityOrdered());
                    orderDetailDTO.setProductLine(orderDetail.getProductLine());
                    return orderDetailDTO;
                }))
                .collect(Collectors.toList());
        return orderDetailsDTO;
    }

    @Override
    public List<OrderHistoryDTO> getShoppingHistory(Long customerId) throws RuntimeException{
        Customer customer =iCustomerRepository.findByCustomerId(customerId).orElseThrow(()->new CustomerException( "There is no a customer with Id:"+customerId));
        //Transform order objects to orderHistoryDTO
        return customer.getOrders()
                .stream()
                .filter(order->order.getPaid())
                .map((order)->{
                        OrderHistoryDTO shoppingHistoryDTO= new OrderHistoryDTO();

                        shoppingHistoryDTO.setOrderId(order.getOrderId());
                        shoppingHistoryDTO.setCustomerId(customerId);
                        shoppingHistoryDTO.setPurchaseDate(order.getPurchaseDate());
                        shoppingHistoryDTO.setTotalCost(order.getTotalCost());
                        //I need to transform this OrderDetail list to a OrderDetailDTO list
                        shoppingHistoryDTO.setOrderDetails( mapOrderDetailsListToOrderDetailsDTOList(order.getOrdersDetail()));

                        return  shoppingHistoryDTO;
                        //end  outer map
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderDTO> getAllOrdersPaidStore() {
        List<Order> orders= iOrderRepository.findByPaidTrue();
        return orders.stream()
                    .map(order -> mapOrderToOrderDTO(order))
                    .collect(Collectors.toList());
    }

}
