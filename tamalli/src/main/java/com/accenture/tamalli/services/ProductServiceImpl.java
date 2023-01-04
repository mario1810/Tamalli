package com.accenture.tamalli.services;

import com.accenture.tamalli.dto.products.ProductPriceDTO;
import com.accenture.tamalli.exceptions.ProductException;
import com.accenture.tamalli.models.Drink;
import com.accenture.tamalli.models.OrderDetail;
import com.accenture.tamalli.models.Product;
import com.accenture.tamalli.models.Tamal;
import com.accenture.tamalli.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductServiceImpl implements IProductService {


    @Autowired
    IProductRepository iProductRepository;

    @Autowired
    IDrinkRepository iDrinkRepository;

    @Autowired
    ITamalRepository iTamalRepository;

    @Autowired
    IOrderDetailRepository iOrderDetailRepository;

    @Override
    public Drink addDrink(Drink drink) throws RuntimeException {
        if(drink.equals(null))
            throw  new ProductException("please, register a valid product");
        drink.setProductId(null);
        return iDrinkRepository.saveAndFlush(drink);
    }

    @Override
    public Tamal addTamal(Tamal tamal) throws RuntimeException{
        if(tamal.equals(null))
            throw  new ProductException("please, register a valid product");
        tamal.setProductId(null);
        return iTamalRepository.saveAndFlush(tamal);
    }

    @Override
    public Drink getDrinkById(Long productId) throws RuntimeException{
        if(productId==null)
            throw  new ProductException("Please, choose a valid id product");
        return iDrinkRepository.findByProductId(productId).orElseThrow(()->new ProductException("There is no product with id:"+productId));
    }

    @Override
    public Tamal getTamalById(Long productId) throws RuntimeException{
        if(productId==null)
            throw  new ProductException("Please, choose a valid id product");
        return iTamalRepository.findByProductId(productId).orElseThrow(()->new ProductException("There is no product with id:"+productId));
    }

    @Override
    public Product getProductById(Long productId) throws RuntimeException{
        if(productId==null)
            throw  new ProductException("Please, choose a valid id product");
        return iProductRepository.findByProductId(productId).orElseThrow(()->new ProductException("There is no product with id:"+productId));
    }

    @Override
    public List<Drink> getAllDrinks() {
        return iDrinkRepository.findAll();
    }

    @Override
    public List<Tamal> getAllTamales() {
        return iTamalRepository.findAll();
    }

    @Override
    public List<Product> getAllProduct() {
        return iProductRepository.findAll();
    }

    @Override
    public Product changeProductPrice(ProductPriceDTO changesProduct) throws RuntimeException{
        if(changesProduct.equals(null))
            throw  new ProductException("please, register valid changes");
        Product productToUpdate =iProductRepository.findByProductId(changesProduct.getProductId()).orElseThrow(()->new ProductException("There is no product with id:"+changesProduct.getProductId()));
        //Changes price
        productToUpdate.setPrice(changesProduct.getPrice());
        return iProductRepository.saveAndFlush(productToUpdate);
    }

    @Transactional
    @Override
    public void deleteProduct(Long productId) throws RuntimeException{
        if(productId==null)
            throw  new ProductException("Please, choose a valid id product");
        //Find the product to delete
        Product productToDelete =iProductRepository.findByProductId(productId).orElseThrow(()->new ProductException("There is no product with id:"+productId));
        //delete the product
        iProductRepository.delete(productToDelete);
        //delete from all ordersDetail that has not been paid
        List<OrderDetail> shoppingCartsDetail=iOrderDetailRepository.findByProductProductIdAndOrderPaidFalse(productId);
        shoppingCartsDetail.forEach((shoppingCartDetail)->iOrderDetailRepository.delete(shoppingCartDetail));
    }
}
