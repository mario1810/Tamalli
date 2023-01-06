package com.accenture.tamalli.controllers;


import com.accenture.tamalli.dto.orderDetails.ProductOrderDTO;
import com.accenture.tamalli.dto.orderDetails.ProductToOrderDTO;
import com.accenture.tamalli.services.IOrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/api/tamalli")
public class OrderDetailController {

    @Autowired
    private IOrderDetailService iOrderDetailService;


    @PostMapping("/orderDetail/{customerId}")
    public ProductOrderDTO addApiProductToShoppingCart(@PathVariable Long customerId, @RequestBody ProductToOrderDTO productToOrder){
            return iOrderDetailService.addProductToShoppingCart(customerId,productToOrder.getProductId(),productToOrder.getQuantity());
    }

    @DeleteMapping("/orderDetail/{customerId}")
    String removeApiProductFromShoppingCart(@PathVariable Long customerId,@RequestBody ProductToOrderDTO productToOrderDTO){
        return iOrderDetailService.removeProductFromShoppingCart(customerId,productToOrderDTO.getProductId());
    }

    @DeleteMapping("/orderDetail/all/{customerId}")
    String removeApiAllProductsFromShoppingCart(@PathVariable Long customerId){
        return iOrderDetailService.removeAllProductsFromShoppingCart(customerId);
    }

    @PutMapping("/orderDetail/{customerId}")
    ProductOrderDTO changeApiProductQuantityAtShoppingCart(@PathVariable Long customerId,@RequestBody ProductToOrderDTO productToOrderDTO){
        return iOrderDetailService.changeProductQuantityAtShoppingCart(customerId,productToOrderDTO.getProductId(),productToOrderDTO.getQuantity());
    }
}
