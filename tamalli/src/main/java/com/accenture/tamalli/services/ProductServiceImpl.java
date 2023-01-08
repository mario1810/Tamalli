package com.accenture.tamalli.services;

import com.accenture.tamalli.dto.products.ProductPriceDTO;
import com.accenture.tamalli.exceptions.BadRequestProductException;
import com.accenture.tamalli.exceptions.NotFoundProductException;
import com.accenture.tamalli.exceptions.ProductException;
import com.accenture.tamalli.models.*;
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

    @Autowired
    IProductDescriptionRepository iProductDescriptionRepository;
    @Override
    public Drink addDrink(Drink drink) throws RuntimeException {
        if(drink.equals(null) || drink.getProductName()==null || drink.getCapacityLiters()<=0.0 || drink.getPrice()==null)
            throw  new BadRequestProductException("please, register a valid product");
        if(!iDrinkRepository.findByProductNameAndCapacityLiters(drink.getProductName(),drink.getCapacityLiters()).isEmpty())
            throw  new BadRequestProductException("This product is already in the database");
        drink.setProductId(null);
        return iDrinkRepository.saveAndFlush(drink);
    }

    @Override
    public Tamal addTamal(Tamal tamal) throws RuntimeException{
        if(tamal.equals(null) || tamal.getProductName()==null || tamal.getWeightKilogram()<=0.0 || tamal.getPrice()==null)
            throw  new BadRequestProductException("please, register a valid product");
        if(!iTamalRepository.findByProductNameAndWeightKilogram(tamal.getProductName(),tamal.getWeightKilogram()).isEmpty())
            throw  new BadRequestProductException("This product is already in the database");
        tamal.setProductId(null);
        return iTamalRepository.saveAndFlush(tamal);
    }

    @Override
    public Drink getDrinkById(Long productId) throws RuntimeException{
        if(productId==null)
            throw  new BadRequestProductException("Please, choose a valid id product");
        return iDrinkRepository.findByProductId(productId).orElseThrow(()->new NotFoundProductException("There is no product with id:"+productId));
    }

    @Override
    public Tamal getTamalById(Long productId) throws RuntimeException{
        if(productId==null)
            throw  new BadRequestProductException("Please, choose a valid id product");
        return iTamalRepository.findByProductId(productId).orElseThrow(()->new NotFoundProductException("There is no product with id:"+productId));
    }

    @Override
    public Product getProductById(Long productId) throws RuntimeException{
        if(productId==null)
            throw  new BadRequestProductException("Please, choose a valid id product");
        return iProductRepository.findByProductId(productId).orElseThrow(()->new NotFoundProductException("There is no product with id:"+productId));
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
            throw  new BadRequestProductException("please, register valid changes");
        Product productToUpdate =iProductRepository.findByProductId(changesProduct.getProductId()).orElseThrow(()->new NotFoundProductException("There is no product with id:"+changesProduct.getProductId()));
        //Changes price
        productToUpdate.setPrice(changesProduct.getPrice());
        return iProductRepository.saveAndFlush(productToUpdate);
    }

    @Transactional
    @Override
    public String deleteProduct(Long productId) throws RuntimeException{
        if(productId==null)
            throw  new BadRequestProductException("Please, choose a valid id product");
        //Find the product to delete
        Product productToDelete =iProductRepository.findByProductId(productId).orElseThrow(()->new NotFoundProductException("There is no product with id:"+productId));

        //Disassociate order details from this product (to store in history which product has been sold)
        List<OrderDetail> ordersDetail=iOrderDetailRepository.findByProductProductId(productId);
        for(int i=0; i<ordersDetail.size();i++){
            ordersDetail.get(i).setProduct(null);
        }

        //delete the product
        iProductRepository.delete(productToDelete);
        //delete from all ordersDetail that has not been paid
        List<OrderDetail> shoppingCartsDetail=iOrderDetailRepository.findByProductIsNullAndOrderPaidFalse();
        shoppingCartsDetail.forEach((shoppingCartDetail)->iOrderDetailRepository.delete(shoppingCartDetail));

        //Eliminate any product description
        try{
            ProductDescription description=iProductDescriptionRepository.findByProductId(productId).orElse(null);
            if(description!=null)
                iProductDescriptionRepository.delete(description);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return "The product with id:"+productId+" has been deleted";
    }
}
