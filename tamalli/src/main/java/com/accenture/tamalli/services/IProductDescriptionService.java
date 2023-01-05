package com.accenture.tamalli.services;

import com.accenture.tamalli.models.ProductDescription;

import java.util.List;
import java.util.Map;
import java.util.Objects;


public interface IProductDescriptionService {

    ProductDescription getProductDescriptionById(Long productId);
    List<ProductDescription> getAllProductDescription();
    ProductDescription createProductDescription(ProductDescription productDescription);
    ProductDescription updateProductDescription(ProductDescription productDescription);

    ProductDescription updateProductDescriptionPartially(Map<String,Object> productDescriptionChanges, Long productId);
    String deleteProductDescription(Long productId);

}
