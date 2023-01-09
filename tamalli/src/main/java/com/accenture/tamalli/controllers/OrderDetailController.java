package com.accenture.tamalli.controllers;


import com.accenture.tamalli.dto.orderDetails.ProductOrderDTO;
import com.accenture.tamalli.dto.orderDetails.ProductToOrderDTO;
import com.accenture.tamalli.services.IOrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/api/tamalli")
public class OrderDetailController {

    @Autowired
    private IOrderDetailService iOrderDetailService;


    @PostMapping("/orderDetail/{customerId}")
    public ResponseEntity<ProductOrderDTO> addApiProductToShoppingCart(@PathVariable Long customerId, @RequestBody ProductToOrderDTO productToOrder){
        return  new ResponseEntity<>(iOrderDetailService.addProductToShoppingCart(customerId,productToOrder.getProductId(),productToOrder.getQuantity()), HttpStatus.CREATED);
    }

    @DeleteMapping("/orderDetail/{customerId}/{productId}")
    public ResponseEntity<String> removeApiProductFromShoppingCart(@PathVariable Long customerId,@PathVariable Long productId){
        return  new ResponseEntity<>( iOrderDetailService.removeProductFromShoppingCart(customerId,productId),HttpStatus.OK);
    }

    @DeleteMapping("/orderDetail/all/{customerId}")
    public ResponseEntity<String> removeApiAllProductsFromShoppingCart(@PathVariable Long customerId){
        return  new ResponseEntity<>(iOrderDetailService.removeAllProductsFromShoppingCart(customerId),HttpStatus.OK);
    }

    @PutMapping("/orderDetail/{customerId}")
    public ResponseEntity<ProductOrderDTO> changeApiProductQuantityAtShoppingCart(@PathVariable Long customerId,@RequestBody ProductToOrderDTO productToOrderDTO){
        return  new ResponseEntity<>(iOrderDetailService.changeProductQuantityAtShoppingCart(customerId,productToOrderDTO.getProductId(),productToOrderDTO.getQuantity()),HttpStatus.OK);
    }
}
