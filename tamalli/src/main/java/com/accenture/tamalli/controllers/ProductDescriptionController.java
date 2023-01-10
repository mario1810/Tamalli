package com.accenture.tamalli.controllers;

import com.accenture.tamalli.dto.products.ProductAndDescriptionDTO;
import com.accenture.tamalli.models.ProductDescription;
import com.accenture.tamalli.services.IProductDescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tamalli/descriptions")
public class ProductDescriptionController {

    @Autowired
    IProductDescriptionService iProductDescriptionService;

    @GetMapping("/{productId}")
    public ResponseEntity<ProductDescription> getApiProductDescriptionById(@PathVariable Long productId){
        return  new ResponseEntity<>(iProductDescriptionService.getProductDescriptionById(productId), HttpStatus.OK);
    }
    @GetMapping("")
    public ResponseEntity<List<ProductDescription>> getApiAllProductDescription(){
        return  new ResponseEntity<>( iProductDescriptionService.getAllProductDescription(),HttpStatus.OK);
    }
    @PostMapping("/{productId}")
    public ResponseEntity<ProductDescription> createApiProductDescription(@PathVariable Long productId, @RequestBody ProductDescription productDescription){
        return  new ResponseEntity<>(iProductDescriptionService.createProductDescription(productId,productDescription),HttpStatus.CREATED);
    }
    @PutMapping("/{productId}")
    public ResponseEntity<ProductDescription> updateApiProductDescription(@PathVariable Long productId, @RequestBody ProductDescription productDescription){
        return  new ResponseEntity<>(iProductDescriptionService.updateProductDescription(productId,productDescription),HttpStatus.OK);
    }

    @PatchMapping("/{productId}")
    public ResponseEntity<ProductDescription> updateApiProductDescriptionPartially(@RequestBody Map<String,Object> productDescriptionChanges,@PathVariable Long productId){
        return  new ResponseEntity<>(iProductDescriptionService.updateProductDescriptionPartially(productDescriptionChanges ,productId),HttpStatus.OK);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<String> deleteApiProductDescription(@PathVariable Long productId){
        return  new ResponseEntity<>(iProductDescriptionService.deleteProductDescription(productId),HttpStatus.OK);
    }
    @GetMapping("/superProducts")
    public ResponseEntity<List<ProductAndDescriptionDTO>> getApiAllProductsAndDescription(){
        return  new ResponseEntity<>(iProductDescriptionService.getAllProductsAndDescription(),HttpStatus.OK);
    }

}
