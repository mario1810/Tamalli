package com.accenture.tamalli.controllers;

import com.accenture.tamalli.models.ProductDescription;
import com.accenture.tamalli.services.IProductDescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/descriptions")
public class ProductDescriptionController {

    @Autowired
    IProductDescriptionService iProductDescriptionService;

    @GetMapping("/{productId}")
    ProductDescription getApiProductDescriptionById(@PathVariable Long productId){
        return iProductDescriptionService.getProductDescriptionById(productId);
    }
    @GetMapping("/")
    List<ProductDescription> getApiAllProductDescription(){
        return iProductDescriptionService.getAllProductDescription();
    }
    @PostMapping("/{productId}")
    ProductDescription createApiProductDescription(@PathVariable Long productId, @RequestBody ProductDescription productDescription){
        return  iProductDescriptionService.createProductDescription(productId,productDescription);
    }
    @PutMapping("/{productId}")
    ProductDescription updateApiProductDescription(@PathVariable Long productId, @RequestBody ProductDescription productDescription){
        return  iProductDescriptionService.updateProductDescription(productId,productDescription);
    }

    @PatchMapping("/{productId}")
    ProductDescription updateApiProductDescriptionPartially(@RequestBody Map<String,Object> productDescriptionChanges,@PathVariable Long productId){
        return iProductDescriptionService.updateProductDescriptionPartially(productDescriptionChanges ,productId);
    }

    @DeleteMapping("/{productId}")
    String deleteApiProductDescription(@PathVariable Long productId){
        return iProductDescriptionService.deleteProductDescription(productId);
    }
}
