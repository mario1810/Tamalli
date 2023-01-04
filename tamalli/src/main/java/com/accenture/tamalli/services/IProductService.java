package com.accenture.tamalli.services;

import com.accenture.tamalli.dto.products.ProductPriceDTO;
import com.accenture.tamalli.models.Drink;
import com.accenture.tamalli.models.Product;
import com.accenture.tamalli.models.Tamal;

import java.util.List;

public interface IProductService {

    Drink addDrink(Drink drink);

    Tamal addTamal(Tamal tamal);

    Drink getDrinkById(Long productId);

    Tamal getTamalById(Long productId);

    Product getProductById(Long productId);

    List<Drink> getAllDrinks();

    List<Tamal> getAllTamales();

    List<Product> getAllProduct();

    Product changePrice(ProductPriceDTO product);

    void deleteProduct(Long productId);


}
