package com.accenture.tamalli.controllers;

import com.accenture.tamalli.dto.orderDetails.OrderDetailDTO;
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

    @PutMapping("/paid/{idCustomer}")
    public OrderDTO changeOrderPaidStatus(@PathVariable Long idCustomer){
        return iOrderService.changeOrderStatus(idCustomer);
    }

    @GetMapping("/shoppingCart/{idCustomer}")
    public ShoppingCartDTO getCustomerShoppingCart(@PathVariable Long idCustomer){
        return  iOrderService.getShoppingCart(idCustomer);
    }

    @GetMapping("/history/{idCustomer}")
    public List<OrderHistoryDTO> getCustomerHistory(@PathVariable Long idCustomer){
        return  iOrderService.getShoppingHistory(idCustomer);
    }

    @GetMapping("/all/paid")
    public List<OrderDTO> getAllOrdersPaidApi(@PathVariable Long idCustomer){
        return iOrderService.getAllOrdersPaid();
    }

}
