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
@RequestMapping(path="/api/products")
public class ProductController{

    @Autowired
    private IProductService iProductService;

    @PostMapping("/drink")
    Drink addApiDrink(@RequestBody Drink drink){
        return iProductService.addDrink(drink);
    }

    @PostMapping("/tamal")
    Tamal addApiTamal(@RequestBody Tamal tamal){
        return iProductService.addTamal(tamal);
    }

    @GetMapping("/drink/{productId}")
    Drink getApiDrinkById(@PathVariable Long productId){
        return iProductService.getDrinkById(productId);
    }

    @GetMapping("/tamal/{productId}")
    Tamal getApiTamalById(@PathVariable Long productId){
        return iProductService.getTamalById(productId);
    }

    @GetMapping("/product/{productId}")
    Product getApiProductById(@PathVariable Long productId){
        return  iProductService.getProductById(productId);
    }

    @GetMapping("/drinks")
    List<Drink> getApiAllDrinks(){
        return  iProductService.getAllDrinks();
    }

    @GetMapping("/tamales")
    List<Tamal> getApiAllTamales(){
        return iProductService.getAllTamales();
    }

    @GetMapping("/")
    List<Product> getAllProduct(){
        return iProductService.getAllProduct();
    }

    @PutMapping("/update/price")
    Product changeProductPrice(@RequestBody ProductPriceDTO newProductChanges){
        return iProductService.changeProductPrice(newProductChanges);
    }

    @DeleteMapping("/{productId}")
    String deleteProduct(@PathVariable Long productId){
        return iProductService.deleteProduct(productId);
    }

}
