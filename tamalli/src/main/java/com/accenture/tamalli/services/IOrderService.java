package com.accenture.tamalli.services;

import com.accenture.tamalli.dto.orderDetails.OrderDetailDTO;
import com.accenture.tamalli.dto.orders.OrderDTO;
import com.accenture.tamalli.dto.orders.OrderHistoryDTO;
import com.accenture.tamalli.models.Customer;
import com.accenture.tamalli.models.Order;
import com.accenture.tamalli.models.OrderDetail;

import java.util.List;

public interface IOrderService {


    OrderDTO changeOrderStatus(Long customerId);

    public List<OrderDetailDTO> getShoppingCart(Long customerId);

    List<OrderHistoryDTO> getShoppingHistory(Long customerId);
}
