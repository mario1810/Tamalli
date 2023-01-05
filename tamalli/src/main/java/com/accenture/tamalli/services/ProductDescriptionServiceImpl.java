package com.accenture.tamalli.services;

import com.accenture.tamalli.exceptions.ProductDescriptionException;
import com.accenture.tamalli.models.ProductDescription;
import com.accenture.tamalli.repositories.IProductDescriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ProductDescriptionServiceImpl implements  IProductDescriptionService {

    @Autowired
    IProductDescriptionRepository iProductDescriptionRepository;

    @Override
    public ProductDescription getProductDescriptionById(Long productId) throws RuntimeException {
        if(productId==null)
            throw new ProductDescriptionException("There is no description for a product with id:"+productId);
        //find
        return iProductDescriptionRepository.findByProductId(productId).orElseThrow(()->new ProductDescriptionException("There is no a description for a product with id:"+productId));
    }

    @Override
    public List<ProductDescription> getAllProductDescription() throws RuntimeException{
        return iProductDescriptionRepository.findAll();
    }

    @Override
    public ProductDescription createProductDescription(ProductDescription productDescription) throws RuntimeException{
        Long productId=productDescription.getProductId();
        if(productId==null)
            throw new ProductDescriptionException("There is no description for a product with id:"+productId);
        //Does that id product exits?
        if(iProductDescriptionRepository.findByProductId(productDescription.getProductId()).orElse(null)!=null)
            throw new ProductDescriptionException("There already exist a description for a product with id:"+productId);
         return iProductDescriptionRepository.save(productDescription);
    }

    @Override
    public ProductDescription updateProductDescription(ProductDescription productDescriptionChanges) throws RuntimeException{
        Long productId=productDescriptionChanges.getProductId();
        if(productId==null)
            throw new ProductDescriptionException("There is no description for a product with id:"+productId);
        //Doesn't that id product exits?
        ProductDescription currentProductDescription =iProductDescriptionRepository.findByProductId(productId).orElseThrow(()->new ProductDescriptionException("There is no a description for a product with id:"+productId));
        //Update description
        return iProductDescriptionRepository.save(productDescriptionChanges);
    }

    @Override
    public ProductDescription updateProductDescriptionPartially(Map<String, Object> productDescriptionChanges, Long productId) throws RuntimeException{
        if(productId==null)
            throw new ProductDescriptionException("There is no description for a product with id:"+productId);
        //Does that id product exits?
        ProductDescription currentProductDescription =iProductDescriptionRepository.findByProductId(productId).orElseThrow(()->new ProductDescriptionException("There is no a description for a product with id:"+productId));
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
            throw new ProductDescriptionException("There is no a description for a product with id:"+productId);
        //Does that id product exits?
        if(iProductDescriptionRepository.findByProductId(productId).orElse(null)==null)
            throw new ProductDescriptionException("There is no a description for a product with id:"+productId+" to delete");
        //find the description to delete
        ProductDescription productDescription=iProductDescriptionRepository.findByProductId(productId).orElseThrow(()->new ProductDescriptionException("There is no a description for a product with id:"+productId));
        //delete it
        iProductDescriptionRepository.delete(productDescription);
        return "The description for the product with id:"+productId+" has been deleted";
    }
}
