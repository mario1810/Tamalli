package com.accenture.tamalli.services;

import com.accenture.tamalli.dto.orderDetails.ProductOrderDTO;


public interface IOrderDetailService {

    ProductOrderDTO addProduct(Long customerId, Long productId, Integer quantity);
    void removeProduct(Long customerId, Long productId);
    void removeAllProducts(Long customerId);
    ProductOrderDTO changeProductQuantity(Long customerId, Long productId, Integer quantity);


}
