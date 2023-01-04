package com.accenture.tamalli.services;

import com.accenture.tamalli.dto.orderDetails.ProductOrderDTO;


public interface IOrderDetailService {

    ProductOrderDTO addProductToShoppingCart(Long customerId, Long productId, Integer quantity);
    String removeProductFromShoppingCart(Long customerId, Long productId);
    String removeAllProductsFromShoppingCart(Long customerId);
    ProductOrderDTO changeProductQuantityAtShoppingCart(Long customerId, Long productId, Integer newQuantity);


}
