package com.accenture.tamalli.controllers;

import com.accenture.tamalli.dto.orders.OrderDTO;
import com.accenture.tamalli.dto.orders.OrderHistoryDTO;
import com.accenture.tamalli.dto.orders.ShoppingCartDTO;
import com.accenture.tamalli.services.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="/api/tamalli")
public class OrderController {
    @Autowired
    private IOrderService iOrderService;
    @PutMapping("/orders/paid/{customerId}")
    public ResponseEntity<OrderDTO> changeApiShoppingCartStatus(@PathVariable Long customerId){
        return  new ResponseEntity<>(iOrderService.changeShoppingCartStatusToPaid(customerId), HttpStatus.OK);
    }

    @GetMapping("/orders/shoppingCart/{customerId}")
    public ResponseEntity<ShoppingCartDTO> getApiCustomerShoppingCart(@PathVariable Long customerId){
        return  new ResponseEntity<>(iOrderService.getShoppingCart(customerId),HttpStatus.OK);
    }

    @GetMapping("/orders/history/{customerId}")
    public ResponseEntity<List<OrderHistoryDTO>> getApiCustomerHistory(@PathVariable Long customerId){
        return  new ResponseEntity<>(iOrderService.getShoppingHistory(customerId),HttpStatus.OK);
    }

    @GetMapping("/orders")
    public ResponseEntity<List<OrderDTO>> getApiAllOrdersPaid(){
        return  new ResponseEntity<>(iOrderService.getAllOrdersPaidStore(),HttpStatus.OK);
    }

}
