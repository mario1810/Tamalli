package com.accenture.tamalli.services;

import com.accenture.tamalli.dto.orders.OrderDTO;
import com.accenture.tamalli.models.Customer;
import com.accenture.tamalli.models.Order;
import com.accenture.tamalli.models.OrderDetail;

import java.util.List;

public interface IOrderService {


    OrderDTO changeOrderStatus(Long customerId);

    List<OrderDetail> getShoppingCart(Long customerId);

    List<Order> getShoppingHistory(Long customerId);
}
