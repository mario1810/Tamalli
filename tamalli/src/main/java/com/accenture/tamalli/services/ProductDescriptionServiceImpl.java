package com.accenture.tamalli.services;

import com.accenture.tamalli.dto.products.ProductAndDescriptionDTO;
import com.accenture.tamalli.exceptions.NotFoundProductDescriptionException;
import com.accenture.tamalli.exceptions.NotFoundProductException;
import com.accenture.tamalli.exceptions.ProductDescriptionException;
import com.accenture.tamalli.models.Product;
import com.accenture.tamalli.models.ProductDescription;
import com.accenture.tamalli.repositories.IProductDescriptionRepository;
import com.accenture.tamalli.repositories.IProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProductDescriptionServiceImpl implements  IProductDescriptionService {

    @Autowired
    IProductDescriptionRepository iProductDescriptionRepository;
    @Autowired
    IProductRepository iProductRepository;

    @Override
    public ProductDescription getProductDescriptionById(Long productId) throws RuntimeException {
        if(productId==null)
            throw new NotFoundProductDescriptionException("There is no description for a product with id:"+productId);
        //find
        return iProductDescriptionRepository.findByProductId(productId).orElseThrow(()->new NotFoundProductDescriptionException("There is no a description for a product with id:"+productId));
    }

    @Override
    public List<ProductDescription> getAllProductDescription() throws RuntimeException{
        return iProductDescriptionRepository.findAll();
    }

    @Override
    public ProductDescription createProductDescription(Long productId,ProductDescription productDescription) throws RuntimeException{
        productDescription.setProductId(productId);
        if(productId==null)
            throw new ProductDescriptionException("There is no a valid id product");
        //Does that id product exits?
        if(!iProductRepository.existsByProductId(productId))
            throw  new NotFoundProductException("There is not a product with id:"+productId);
        //Does a description exists?
        if(iProductDescriptionRepository.existsByProductId(productDescription.getProductId()))
            throw new ProductDescriptionException("There already exist a description for a product with id:"+productId);
         return iProductDescriptionRepository.save(productDescription);
    }

    @Override
    public ProductDescription updateProductDescription(Long productId,ProductDescription productDescriptionChanges) throws RuntimeException{
        productDescriptionChanges.setProductId(productId);
        if(productId==null)
            throw new NotFoundProductDescriptionException("There is no description for a product with id:"+productId);
        //Doesn't that id product description exits?
        ProductDescription currentProductDescription =iProductDescriptionRepository.findByProductId(productId).orElseThrow(()->new NotFoundProductDescriptionException("There is no a description for a product with id:"+productId));
        //Update description
        return iProductDescriptionRepository.save(productDescriptionChanges);
    }

    @Override
    public ProductDescription updateProductDescriptionPartially(Map<String, Object> productDescriptionChanges, Long productId) throws RuntimeException{

        if(productId==null)
            throw new NotFoundProductDescriptionException("There is no description for a product with id:"+productId);
        //Does that id product description exits?
        ProductDescription currentProductDescription =iProductDescriptionRepository.findByProductId(productId).orElseThrow(()->new NotFoundProductDescriptionException("There is no a description for a product with id:"+productId));
        updateProductDescriptionFields(currentProductDescription,productDescriptionChanges);
        //Update description
        return iProductDescriptionRepository.save(currentProductDescription);
    }

    private void updateProductDescriptionFields(ProductDescription currentProductDescription,Map<String,Object> changes){
        changes.forEach((change,value)->{
            switch (change) {
                case "descriptionHead":
                    if(value!=null)
                        currentProductDescription.setDescriptionHead((String)value);
                    break;
                case "descriptionBody":
                    if(value!=null)
                        currentProductDescription.setDescriptionBody((String)value);
                    break;
                case "urlImage":
                    if(value!=null)
                        currentProductDescription.setUrlImage((String)value);
                    break;
            }
        });
    }

    @Override
    public String deleteProductDescription(Long productId) throws RuntimeException{
        if(productId==null)
            throw new NotFoundProductDescriptionException("There is no a description for a product with id:"+productId);
        //find the description to delete
        ProductDescription productDescription=iProductDescriptionRepository.findByProductId(productId).orElseThrow(()->new NotFoundProductDescriptionException("There is no a description for a product with id:"+productId));
        //delete it
        iProductDescriptionRepository.delete(productDescription);
        return "The description for the product with id:"+productId+" has been deleted";
    }

    public List<ProductAndDescriptionDTO> getAllProductsAndDescription(){
        List<Product> products = iProductRepository.findAll();
        return products.stream().map(product->{
            ProductAndDescriptionDTO superProduct = new ProductAndDescriptionDTO();
            superProduct.setProduct(product);
            superProduct.setDescription(iProductDescriptionRepository.findByProductId(product.getProductId()).orElse(null));
            return superProduct;
        }).collect(Collectors.toList());
    }
}
