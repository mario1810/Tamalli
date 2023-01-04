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
@RequestMapping(path="/api/product")
public class ProductController{

    @Autowired
    private IProductService iProductService;

    @PostMapping("add/drink")
    Drink addApiDrink(@RequestBody Drink drink){
        return iProductService.addDrink(drink);
    }

    @PostMapping("/add/tamal")
    Tamal addApiTamal(@RequestBody Tamal tamal){
        return iProductService.addTamal(tamal);
    }

    @GetMapping("get/drink/{productId}")
    Drink getApiDrinkById(@PathVariable Long productId){
        return iProductService.getDrinkById(productId);
    }

    @GetMapping("/get/tamal/{productId}")
    Tamal getApiTamalById(@PathVariable Long productId){
        return iProductService.getTamalById(productId);
    }

    @GetMapping("/get/product/{productId}")
    Product getApiProductById(@PathVariable Long productId){
        return  iProductService.getProductById(productId);
    }

    @GetMapping("/get/all/drinks")
    List<Drink> getApiAllDrinks(){
        return  iProductService.getAllDrinks();
    }

    @GetMapping("/get/all/tamales")
    List<Tamal> getApiAllTamales(){
        return iProductService.getAllTamales();
    }

    @GetMapping("/get/all")
    List<Product> getAllProduct(){
        return iProductService.getAllProduct();
    }

    @PutMapping("/update/price/")
    Product changeProductPrice(@RequestBody ProductPriceDTO newProductChanges){
        return iProductService.changeProductPrice(newProductChanges);
    }

    @DeleteMapping("/delete/{productId}")
    String deleteProduct(@PathVariable Long productId){
        return iProductService.deleteProduct(productId);
    }

}
