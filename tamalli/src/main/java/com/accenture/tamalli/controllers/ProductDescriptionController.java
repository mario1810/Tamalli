package com.accenture.tamalli.controllers;

import com.accenture.tamalli.models.ProductDescription;
import com.accenture.tamalli.services.IProductDescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/productDescription")
public class ProductDescriptionController {

    @Autowired
    IProductDescriptionService iProductDescriptionService;

    @GetMapping("/get/{productId}")
    ProductDescription getApiProductDescriptionById(@PathVariable Long productId){
        return iProductDescriptionService.getProductDescriptionById(productId);
    }
    @GetMapping("/getAll")
    List<ProductDescription> getApiAllProductDescription(){
        return iProductDescriptionService.getAllProductDescription();
    }
    @PostMapping("/add")
    ProductDescription createApiProductDescription(@RequestBody ProductDescription productDescription){
        return  iProductDescriptionService.createProductDescription(productDescription);
    }
    @PutMapping("/update")
    ProductDescription updateApiProductDescription(@RequestBody ProductDescription productDescription){
        return  iProductDescriptionService.updateProductDescription(productDescription);
    }

    @PatchMapping("/update/{productId}")
    ProductDescription updateApiProductDescriptionPartially(@RequestBody Map<String,Object> productDescriptionChanges,@PathVariable Long productId){
        return iProductDescriptionService.updateProductDescriptionPartially(productDescriptionChanges ,productId);
    }

    @DeleteMapping("/delete/{productId}")
    String deleteApiProductDescription(@PathVariable Long productId){
        return iProductDescriptionService.deleteProductDescription(productId);
    }
}
