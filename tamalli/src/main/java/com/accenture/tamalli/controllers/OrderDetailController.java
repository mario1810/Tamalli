package com.accenture.tamalli.controllers;


import com.accenture.tamalli.dto.orderDetails.ProductOrderDTO;
import com.accenture.tamalli.services.IOrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/api/orderDetail")
public class OrderDetailController {

    @Autowired
    private IOrderDetailService iOrderDetailService;


    @PostMapping("/add/{customerId}/{productId}/{quantity}")
    public ProductOrderDTO addApiProductToShoppingCart(@PathVariable Long customerId,@PathVariable Long productId,@PathVariable Integer quantity){
            return iOrderDetailService.addProductToShoppingCart(customerId,productId,quantity);
    }

    @DeleteMapping("/delete/{customerId}/{productId}")
    void removeApiProductFromShoppingCart(@PathVariable Long customerId,@PathVariable Long productId){
        iOrderDetailService.removeProductFromShoppingCart(customerId,productId);
    }

    @DeleteMapping("/delete/all/{customerId}")
    void removeApiAllProductsFromShoppingCart(@PathVariable Long customerId){
        iOrderDetailService.removeAllProductsFromShoppingCart(customerId);
    }

    @PutMapping("/update/{customerId}/{productId}/{newQuantity}")
    ProductOrderDTO changeApiProductQuantityAtShoppingCart(@PathVariable Long customerId,@PathVariable Long productId,@PathVariable Integer newQuantity){
        return iOrderDetailService.changeProductQuantityAtShoppingCart(customerId,productId,newQuantity);
    }
}
