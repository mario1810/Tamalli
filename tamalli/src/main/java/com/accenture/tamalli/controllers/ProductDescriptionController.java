package com.accenture.tamalli.controllers;

import com.accenture.tamalli.dto.products.ProductAndDescriptionDTO;
import com.accenture.tamalli.models.ProductDescription;
import com.accenture.tamalli.services.IProductDescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tamalli")
public class ProductDescriptionController {

    @Autowired
    IProductDescriptionService iProductDescriptionService;

    @GetMapping("/descriptions/{productId}")
    ProductDescription getApiProductDescriptionById(@PathVariable Long productId){
        return iProductDescriptionService.getProductDescriptionById(productId);
    }
    @GetMapping("/descriptions")
    List<ProductDescription> getApiAllProductDescription(){
        return iProductDescriptionService.getAllProductDescription();
    }
    @PostMapping("/descriptions/{productId}")
    ProductDescription createApiProductDescription(@PathVariable Long productId, @RequestBody ProductDescription productDescription){
        return  iProductDescriptionService.createProductDescription(productId,productDescription);
    }
    @PutMapping("/descriptions/{productId}")
    ProductDescription updateApiProductDescription(@PathVariable Long productId, @RequestBody ProductDescription productDescription){
        return  iProductDescriptionService.updateProductDescription(productId,productDescription);
    }

    @PatchMapping("/descriptions/{productId}")
    ProductDescription updateApiProductDescriptionPartially(@RequestBody Map<String,Object> productDescriptionChanges,@PathVariable Long productId){
        return iProductDescriptionService.updateProductDescriptionPartially(productDescriptionChanges ,productId);
    }

    @DeleteMapping("/descriptions/{productId}")
    String deleteApiProductDescription(@PathVariable Long productId){
        return iProductDescriptionService.deleteProductDescription(productId);
    }
    @GetMapping("/descriptions/superProducts")
    List<ProductAndDescriptionDTO> getApiAllProductsAndDescription(){
        return iProductDescriptionService.getAllProductsAndDescription();
    }

}
