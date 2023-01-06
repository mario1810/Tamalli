package com.accenture.tamalli.controllers;

import com.accenture.tamalli.dto.orders.OrderDTO;
import com.accenture.tamalli.dto.orders.OrderHistoryDTO;
import com.accenture.tamalli.dto.orders.ShoppingCartDTO;
import com.accenture.tamalli.services.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="/api/tamalli")
public class OrderController {
    @Autowired
    private IOrderService iOrderService;
    @PutMapping("/orders/paid/{customerId}")
    public OrderDTO changeApiShoppingCartStatus(@PathVariable Long customerId){
        return iOrderService.changeShoppingCartStatusToPaid(customerId);
    }

    @GetMapping("/orders/shoppingCart/{customerId}")
    public ShoppingCartDTO getApiCustomerShoppingCart(@PathVariable Long customerId){
        return  iOrderService.getShoppingCart(customerId);
    }

    @GetMapping("/orders/history/{customerId}")
    public List<OrderHistoryDTO> getApiCustomerHistory(@PathVariable Long customerId){
        return  iOrderService.getShoppingHistory(customerId);
    }

    @GetMapping("/orders")
    public List<OrderDTO> getApiAllOrdersPaid(){
        return iOrderService.getAllOrdersPaidStore();
    }

}
