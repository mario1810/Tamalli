package com.accenture.tamalli.services;

import com.accenture.tamalli.dto.orderDetails.OrderDetailDTO;
import com.accenture.tamalli.dto.orderDetails.ProductOrderDTO;

import java.util.List;


public interface IOrderDetailService {

    ProductOrderDTO addProductToShoppingCart(Long customerId, Long productId, int quantity);
    String removeProductFromShoppingCart(Long customerId, Long productId);
    String removeAllProductsFromShoppingCart(Long customerId);
    ProductOrderDTO changeProductQuantityAtShoppingCart(Long customerId, Long productId, int newQuantity);

}
