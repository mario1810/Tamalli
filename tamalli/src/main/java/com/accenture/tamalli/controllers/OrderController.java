package com.accenture.tamalli.controllers;

import com.accenture.tamalli.dto.orders.OrderDTO;
import com.accenture.tamalli.dto.orders.OrderHistoryDTO;
import com.accenture.tamalli.dto.orders.ShoppingCartDTO;
import com.accenture.tamalli.services.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="/api/order")
public class OrderController {


    @Autowired
    private IOrderService iOrderService;

    @PutMapping("/update/paid/{idCustomer}")
    public OrderDTO changeApiShoppingCartStatus(@PathVariable Long idCustomer){
        return iOrderService.changeShoppingCartStatusToPaid(idCustomer);
    }

    @GetMapping("/get/shoppingCart/{idCustomer}")
    public ShoppingCartDTO getApiCustomerShoppingCart(@PathVariable Long idCustomer){
        return  iOrderService.getShoppingCart(idCustomer);
    }

    @GetMapping("/get/history/{idCustomer}")
    public List<OrderHistoryDTO> getApiCustomerHistory(@PathVariable Long idCustomer){
        return  iOrderService.getShoppingHistory(idCustomer);
    }

    @GetMapping("/get/all/paid")
    public List<OrderDTO> getApiAllOrdersPaid(@PathVariable Long idCustomer){
        return iOrderService.getAllOrdersPaidStore();
    }

}
