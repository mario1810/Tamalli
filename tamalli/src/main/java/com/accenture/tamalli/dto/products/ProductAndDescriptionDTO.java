package com.accenture.tamalli.dto.products;

import com.accenture.tamalli.models.Product;
import com.accenture.tamalli.models.ProductDescription;

import java.io.Serializable;

public class ProductAndDescriptionDTO implements Serializable {

    Product product;
    ProductDescription description;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public ProductDescription getDescription() {
        return description;
    }

    public void setDescription(ProductDescription description) {
        this.description = description;
    }
}
