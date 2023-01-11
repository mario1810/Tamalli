package com.accenture.tamalli.services;

import com.accenture.tamalli.dto.orderDetails.OrderDetailDTO;
import com.accenture.tamalli.dto.orderDetails.ProductOrderDTO;

import com.accenture.tamalli.exceptions.*;
import com.accenture.tamalli.models.*;
import com.accenture.tamalli.repositories.ICustomerRepository;
import com.accenture.tamalli.repositories.IOrderDetailRepository;
import com.accenture.tamalli.repositories.IOrderRepository;
import com.accenture.tamalli.repositories.IProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;


@Service
public class OrderDetailServiceImpl implements IOrderDetailService {

    private static final int MAX_QUANTITY= 30;

    @Autowired
    IOrderDetailRepository iOrderDetailRepository;
    @Autowired
    ICustomerRepository iCustomerRepository;

    @Autowired
    IOrderRepository iOrderRepository;
    @Autowired
    IProductRepository iProductRepository;

    private Order createEmptyShoppingCart(Customer customer){
        Order emptyOrder = new Order();
        emptyOrder.setCustomer(customer);
        emptyOrder.setPaid(false);
        return iOrderRepository.saveAndFlush(emptyOrder);
    }


    @Override
    public ProductOrderDTO addProductToShoppingCart(Long customerId, Long productId, int quantity) throws RuntimeException{
        //find order
        Order shoppingCart = findShoppingCart(customerId);

        //does an order detail for the id product exists?
        OrderDetail shoppingCartDetail=shoppingCart.getOrdersDetail()
                .stream()
                .filter(orderDetail -> productId.equals(orderDetail.getProduct().getProductId()))
                .findFirst()
                .orElse(null);
        //If shoppingCartDetail for that product does not exist, add  the product with a new shoppingCartDetail
        if(shoppingCartDetail==null){
            //Find the product
            Product product= iProductRepository.findByProductId(productId).orElseThrow(()->new NotFoundProductException("Product with Id:"+productId+" is not in our system"));

            //create the new orderDetail
            OrderDetail newShoppingCartDetail= new OrderDetail();

            newShoppingCartDetail.setDetailOrderId(null);
            newShoppingCartDetail.setProduct(product);
            newShoppingCartDetail.setOrder(shoppingCart);
            newShoppingCartDetail.setProductOrdered(product.getProductName());
            newShoppingCartDetail.setQuantityOrdered(quantity);
            newShoppingCartDetail.setProductLine(product.getProductType());
            newShoppingCartDetail.setProductPriceOrdered(product.getPrice());

            //store the new order detail
            return changeProductQuantityAtShoppingCartInternal(newShoppingCartDetail, quantity, shoppingCart.getOrderId());

        }
        //If shoppingCartDetail for that product exists, Optionally: update quantity
        else {
            throw  new OrderDetailException("There is already a product with id:"+productId+" in your shopping cart. You can remove it or change the quantity");
            //return changeProductQuantityAtShoppingCartInternal(shoppingCartDetail, quantity, shoppingCart.getOrderId());
        }
    }

    @Override
    public String removeProductFromShoppingCart(Long customerId, Long productId) throws RuntimeException{
        Order shoppingCart= findShoppingCart(customerId);

        //Find the order detail that contain the idProduct
        OrderDetail shoppingCartDetailToDelete=shoppingCart.getOrdersDetail()
                    .stream()
                    .filter(orderDetail -> productId.equals(orderDetail.getProduct().getProductId()))
                    .findFirst().orElseThrow(()-> new NotFoundOrderDetailException("There is no a product with id:"+productId+" in the shopping cart"));
        //Delete de order detail
        iOrderDetailRepository.delete(shoppingCartDetailToDelete);
        return "the product with Id:"+productId+" has been deleted from your shopping bag";
    }

    @Transactional
    @Override
    public String removeAllProductsFromShoppingCart(Long customerId) throws RuntimeException{
        Order shoppingCart= findShoppingCart(customerId);
        //get all the orders details
        List<OrderDetail> shoppingCartDetails= shoppingCart.getOrdersDetail();
        //is empty?
        if(shoppingCartDetails==null || shoppingCartDetails.isEmpty())
            throw new OrderDetailException("The shopping cart is already empty");
        //delete each order detail
        shoppingCartDetails.forEach((shoppingDetail -> iOrderDetailRepository.delete(shoppingDetail)));
         return "All products have been deleted from your shopping bag";
    }

    private Order findShoppingCart(Long customerId) throws RuntimeException{
        if(customerId==null)
            throw new BadRequestCustomerException("there is no id to identify a customer");
        //Does the ID exist?
        Customer customer=iCustomerRepository.findByCustomerId(customerId).orElseThrow(()->new NotFoundCustomerException("there is no customer with id:"+ customerId));
        //Find the shopping order where paid==false
        Order shoppingCart = customer.getOrders().stream().filter(order->!order.getPaid()).findFirst().orElse(null);
        //There is no shopping cart?
        if(shoppingCart==null){
            shoppingCart=createEmptyShoppingCart(customer);
        }
        return shoppingCart;
    }

    @Override
    public ProductOrderDTO changeProductQuantityAtShoppingCart(Long customerId, Long productId, int newQuantity) throws RuntimeException{
        //find the order
        Order shoppingCart= findShoppingCart(customerId);
        //Find the order detail that contains the idProduct
        OrderDetail currentShoppingCartDetail= shoppingCart.getOrdersDetail().stream()
                                                        .filter(shoppingCartDetail ->productId.equals(shoppingCartDetail.getProduct().getProductId() ))
                                                        .findFirst()
                                                        .orElseThrow(()-> new NotFoundOrderDetailException("There is no a product with id:"+productId+" in the shopping cart"));
        //perform the quantity update
        return changeProductQuantityAtShoppingCartInternal(currentShoppingCartDetail, newQuantity, shoppingCart.getOrderId());
    }

    private ProductOrderDTO changeProductQuantityAtShoppingCartInternal(OrderDetail currentOrderDetail, int newQuantity, Long orderId) throws RuntimeException{
        if(newQuantity<= 0 || newQuantity>MAX_QUANTITY)
            throw new BadRequestProductException("Quantity is no valid, please choose a value between 1 and "+MAX_QUANTITY);

        //The product price has been updated at some point in the time?
        BigDecimal currentPriceAtOrderDetail=currentOrderDetail.getProductPriceOrdered();
        BigDecimal currentProductPrice=currentOrderDetail.getProduct().getPrice();
        if(!currentPriceAtOrderDetail.equals(currentProductPrice))
            throw  new ProductException("The product's price has changed so we are going to respect the previous price for the quantity you have ordered. Delete this product with id:"+ currentOrderDetail.getProduct().getProductId()+" if you need more.");

        //update orderDetail
        currentOrderDetail.setQuantityOrdered(newQuantity);
        currentOrderDetail=iOrderDetailRepository.saveAndFlush(currentOrderDetail);

        //setting DTO
        ProductOrderDTO currentOrderDetailDTO = new ProductOrderDTO();
        currentOrderDetailDTO.setOrderId(orderId);
        currentOrderDetailDTO.setDetailOrderId(currentOrderDetail.getDetailOrderId());
        currentOrderDetailDTO.setQuantityOrdered(currentOrderDetail.getQuantityOrdered());
        currentOrderDetailDTO.setProductLine(currentOrderDetail.getProductLine());
        currentOrderDetailDTO.setProductPriceOrdered(currentOrderDetail.getProductPriceOrdered());
        currentOrderDetailDTO.setProductOrdered(currentOrderDetail.getProductOrdered());

        return  currentOrderDetailDTO;

    }
}
