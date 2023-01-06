package com.accenture.tamalli.controllers;

import com.accenture.tamalli.dto.products.ProductPriceDTO;
import com.accenture.tamalli.models.Drink;
import com.accenture.tamalli.models.Product;
import com.accenture.tamalli.models.Tamal;
import com.accenture.tamalli.services.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="/api/tamalli")
public class ProductController{

    @Autowired
    private IProductService iProductService;

    @PostMapping("/products/drink")
    Drink addApiDrink(@RequestBody Drink drink){
        return iProductService.addDrink(drink);
    }

    @PostMapping("/products/tamal")
    Tamal addApiTamal(@RequestBody Tamal tamal){
        return iProductService.addTamal(tamal);
    }

    @GetMapping("/products/drink/{productId}")
    Drink getApiDrinkById(@PathVariable Long productId){
        return iProductService.getDrinkById(productId);
    }

    @GetMapping("/products/tamal/{productId}")
    Tamal getApiTamalById(@PathVariable Long productId){
        return iProductService.getTamalById(productId);
    }

    @GetMapping("/products/{productId}")
    Product getApiProductById(@PathVariable Long productId){
        return  iProductService.getProductById(productId);
    }

    @GetMapping("/products/drink")
    List<Drink> getApiAllDrinks(){
        return  iProductService.getAllDrinks();
    }

    @GetMapping("/products/tamal")
    List<Tamal> getApiAllTamales(){
        return iProductService.getAllTamales();
    }

    @GetMapping("/products")
    List<Product> getAllProduct(){
        return iProductService.getAllProduct();
    }

    @PutMapping("/products/price")
    Product changeProductPrice(@RequestBody ProductPriceDTO newProductChanges){
        return iProductService.changeProductPrice(newProductChanges);
    }

    @DeleteMapping("/products/{productId}")
    String deleteProduct(@PathVariable Long productId){
        return iProductService.deleteProduct(productId);
    }

}
