package com.accenture.tamalli.controllers;

import com.accenture.tamalli.dto.products.ProductPriceDTO;
import com.accenture.tamalli.models.Drink;
import com.accenture.tamalli.models.Product;
import com.accenture.tamalli.models.Tamal;
import com.accenture.tamalli.services.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="/api/tamalli/products")
public class ProductController{

    @Autowired
    private IProductService iProductService;

    @PostMapping("/drink")
    public ResponseEntity<Drink> addApiDrink(@RequestBody Drink drink){
        return  new ResponseEntity<>(iProductService.addDrink(drink), HttpStatus.CREATED);
    }

    @PostMapping("/tamal")
    public ResponseEntity<Tamal> addApiTamal(@RequestBody Tamal tamal){
        return  new ResponseEntity<>(iProductService.addTamal(tamal), HttpStatus.CREATED);
    }

    @GetMapping("/drink/{productId}")
    public ResponseEntity<Drink> getApiDrinkById(@PathVariable Long productId){
        return  new ResponseEntity<>(iProductService.getDrinkById(productId),HttpStatus.OK);
    }

    @GetMapping("/tamal/{productId}")
    public ResponseEntity<Tamal> getApiTamalById(@PathVariable Long productId){
        return  new ResponseEntity<>(iProductService.getTamalById(productId),HttpStatus.OK);
    }

    @GetMapping("/{productId}")
    public  ResponseEntity<Product> getApiProductById(@PathVariable Long productId){
        return  new ResponseEntity<>(iProductService.getProductById(productId),HttpStatus.OK);
    }

    @GetMapping("/drink")
    public ResponseEntity<List<Drink>> getApiAllDrinks(){
        return  new ResponseEntity<>(iProductService.getAllDrinks(),HttpStatus.OK);
    }

    @GetMapping("/tamal")
    public ResponseEntity<List<Tamal>> getApiAllTamales(){
        return  new ResponseEntity<>(iProductService.getAllTamales(),HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<List<Product>> getAllProduct(){
        return  new ResponseEntity<>(iProductService.getAllProduct(),HttpStatus.OK);
    }

    @PutMapping("/price")
    public ResponseEntity<Product> changeProductPrice(@RequestBody ProductPriceDTO newProductChanges){
        return  new ResponseEntity<>(iProductService.changeProductPrice(newProductChanges),HttpStatus.OK);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long productId){
        return  new ResponseEntity<>(iProductService.deleteProduct(productId),HttpStatus.OK);
    }

}
