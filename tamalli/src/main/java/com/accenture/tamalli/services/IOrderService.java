package com.accenture.tamalli.services;

import com.accenture.tamalli.dto.orders.OrderDTO;
import com.accenture.tamalli.dto.orders.OrderHistoryDTO;
import com.accenture.tamalli.dto.orders.ShoppingCartDTO;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

public interface IOrderService {


    OrderDTO changeShoppingCartStatusToPaid(Long customerId);

    public ShoppingCartDTO getShoppingCart(Long customerId);

    List<OrderHistoryDTO> getShoppingHistory(Long customerId);

    List<OrderDTO> getAllOrdersPaidStore();
    List<OrderDTO> getAllOrdersFrom(String date);
}
