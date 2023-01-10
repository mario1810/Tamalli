package com.accenture.tamalli.services;

//test services outside Spring Context, mocking every dependency and testing them like regular Java classes rather than Spring Beans.


import com.accenture.tamalli.exceptions.NotFoundProductDescriptionException;
import com.accenture.tamalli.exceptions.NotFoundProductException;
import com.accenture.tamalli.exceptions.ProductDescriptionException;
import com.accenture.tamalli.models.Product;
import com.accenture.tamalli.models.ProductDescription;
import com.accenture.tamalli.models.Tamal;
import com.accenture.tamalli.repositories.IProductDescriptionRepository;
import com.accenture.tamalli.repositories.IProductRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductDescriptionTest {

    @InjectMocks
    private IProductDescriptionService iProductDescriptionService = new ProductDescriptionServiceImpl();

    @Mock
    private IProductDescriptionRepository iProductDescriptionRepository;

    @Mock
    private IProductRepository iProductRepository;
    private static ObjectMapper objectMapper;

    private  static List<ProductDescription> productsDescription;

    @BeforeAll
    public static  void setup() throws  Exception{

        String json ="[{\"productId\":1,\"descriptionHead\":\"El mejor tamal de México\",\"descriptionBody\":\"Elaborado  al estilo CDMX\",\"urlImage\":\"https://img.freepik.com/fotos-premium/nacatamal-servido-hoja-platano-cerca-nacatamal-nicaraguense-comida-nicaraguense-nacatamal_550253-296.jpg?w=996\"},{\"productId\":2,\"descriptionHead\":\"El mejor tamal del norte\",\"descriptionBody\":\"Elaborado  al estilo Monterrey\",\"urlImage\":\"https://img.freepik.com/fotos-premium/nacatamal-servido-hoja-platano-cerca-nacatamal-nicaraguense-comida-nicaraguense-nacatamal_550253-296.jpg?w=996\"}]";

        objectMapper = new ObjectMapper();
        productsDescription=objectMapper.readValue(json,new TypeReference<List<ProductDescription>>() {});
    }


    private boolean compareProductsDescription(ProductDescription expected, ProductDescription actual){
        if(!expected.getProductId().equals(actual.getProductId()) ||
            !expected.getDescriptionHead().equals(actual.getDescriptionHead()) ||
            !expected.getDescriptionBody().equals(actual.getDescriptionBody()) ||
            !expected.getUrlImage().equals(actual.getUrlImage()))
            return false;
        return  true;
    }

    @Test
    void getProductDescriptionByIdTest(){

        when(iProductDescriptionRepository.findByProductId(1L)).thenReturn(Optional.of(productsDescription.get(0)));
        //doReturn(Optional.of(productsDescription.get(0))).when(iProductDescriptionRepository).findByProductId(1L);

        ProductDescription currentProductDescription=iProductDescriptionService.getProductDescriptionById(1L);
        assertNotNull(currentProductDescription);
        assertTrue(compareProductsDescription(productsDescription.get(0),currentProductDescription));
        verify(iProductDescriptionRepository, times(1)).findByProductId(any());

    }

    @Test
    void getProductDescriptionByIdExceptionTest(){

        when(iProductDescriptionRepository.findByProductId(3L)).thenThrow(NotFoundProductDescriptionException.class);

        assertThrows(NotFoundProductDescriptionException.class,()->{
            ProductDescription currentProductDescription= iProductDescriptionService.getProductDescriptionById(3L);
        });
    }

    @Test
    void getAllProductDescriptionTest(){

        when(iProductDescriptionRepository.findAll()).thenReturn(productsDescription);

        List<ProductDescription> currentProductsDescription = iProductDescriptionService.getAllProductDescription();
        assertNotNull(currentProductsDescription);
        assertEquals(productsDescription.size(),currentProductsDescription.size());
        assertTrue(compareProductsDescription(productsDescription.get(0),currentProductsDescription.get(0)));
        assertTrue(compareProductsDescription(productsDescription.get(1),currentProductsDescription.get(1)));

        verify(iProductDescriptionRepository, times(1)).findAll();

    }

    @Test
    void createProductDescriptionTest(){

        Product tamal = new Tamal();
        tamal.setProductId(1L);
        tamal.setProductName("Tamal verde");
        tamal.setProductType("Food");
        tamal.setPrice(new BigDecimal("12.50"));


        when(iProductRepository.existsByProductId(1L)).thenReturn(true);
        when(iProductDescriptionRepository.existsByProductId(1L)).thenReturn(false);
        when(iProductDescriptionRepository.save(any(ProductDescription.class))).thenAnswer(invocationOnMock -> invocationOnMock.getArgument(0,ProductDescription.class));


        ProductDescription currentProductDescription= iProductDescriptionService.createProductDescription(1L, productsDescription.get(0));

        assertNotNull(currentProductDescription);
        assertTrue(compareProductsDescription(productsDescription.get(0),currentProductDescription));

    }

    @Test
    void createProductDescriptionException1Test(){
        //there is no  product
        when(iProductRepository.existsByProductId(anyLong())).thenReturn(false);

        assertThrows(NotFoundProductException.class,()->{
            ProductDescription currentProductDescription= iProductDescriptionService.createProductDescription(3L,productsDescription.get(0));
        });
    }

    @Test
    void createProductDescriptionException2Test(){
        //there is description
        when(iProductRepository.existsByProductId(anyLong())).thenReturn(true);
        when(iProductDescriptionRepository.existsByProductId(anyLong())).thenReturn(true);

        assertThrows(ProductDescriptionException.class,()->{
            ProductDescription currentProductDescription= iProductDescriptionService.createProductDescription(3L,productsDescription.get(0));
        });
    }

    @Test
    void updateProductDescriptionTest() throws Exception{

        //id 1, body of product description with id 1
        String json="{\"productId\":1,\"descriptionHead\":\"El mejor tamal del norte\",\"descriptionBody\":\"Elaborado  al estilo Monterrey\",\"urlImage\":\"https://img.freepik.com/fotos-premium/nacatamal-servido-hoja-platano-cerca-nacatamal-nicaraguense-comida-nicaraguense-nacatamal_550253-296.jpg?w=996\"}";

        ProductDescription expectedUpdateProductDescription= objectMapper.readValue(json, ProductDescription.class);

        when(iProductDescriptionRepository.findByProductId(1L)).thenReturn(Optional.of(productsDescription.get(0)));

        when(iProductDescriptionRepository.save(any(ProductDescription.class))).thenAnswer(invocationOnMock -> invocationOnMock.getArgument(0,ProductDescription.class));


        ProductDescription currentProductDescription=iProductDescriptionService.updateProductDescription(1L, productsDescription.get(1));
        assertEquals(objectMapper.writeValueAsString(expectedUpdateProductDescription),objectMapper.writeValueAsString(currentProductDescription));
        assertTrue(compareProductsDescription(expectedUpdateProductDescription,currentProductDescription));
    }

    @Test
    void updateProductDescriptionExceptionTest() throws Exception{

        String json="{\"productId\":1,\"descriptionHead\":\"El mejor tamal del norte\",\"descriptionBody\":\"Elaborado  al estilo Monterrey\",\"urlImage\":\"https://img.freepik.com/fotos-premium/nacatamal-servido-hoja-platano-cerca-nacatamal-nicaraguense-comida-nicaraguense-nacatamal_550253-296.jpg?w=996\"}";
        ProductDescription expectedUpdateProductDescription= objectMapper.readValue(json, ProductDescription.class);
        when(iProductDescriptionRepository.findByProductId(anyLong())).thenThrow(NotFoundProductDescriptionException.class);

        assertThrows(NotFoundProductDescriptionException.class,()->{
            ProductDescription currentProductDescription= iProductDescriptionService.updateProductDescription(3L, productsDescription.get(1));
        });

    }

    @Test
    void updateProductDescriptionPartiallyTest() throws  Exception{

        String json="{\"productId\":1,\"descriptionHead\":\"El mejor tamal de Monterrey\",\"descriptionBody\":\"Elaborado  al estilo CDMX\",\"urlImage\":\"https://img.freepik.com/fotos-premium/nacatamal-servido-hoja-platano-cerca-nacatamal-nicaraguense-comida-nicaraguense-nacatamal_550253-296.jpg?w=996\"}";
        String jsonInput="{\"productId\":1,\"descriptionHead\":\"El mejor tamal del norte\",\"descriptionBody\":\"Elaborado  al estilo CDMX\",\"urlImage\":\"https://img.freepik.com/fotos-premium/nacatamal-servido-hoja-platano-cerca-nacatamal-nicaraguense-comida-nicaraguense-nacatamal_550253-296.jpg?w=996\"}";

        ProductDescription expectedProductDescription=objectMapper.readValue(json,ProductDescription.class);
        ProductDescription inputProductDescription=objectMapper.readValue(jsonInput,ProductDescription.class);



        when(iProductDescriptionRepository.findByProductId(1L)).thenReturn(Optional.of(inputProductDescription));
        when(iProductDescriptionRepository.save(any(ProductDescription.class))).thenAnswer(invocationOnMock -> invocationOnMock.getArgument(0,ProductDescription.class));


        Map<String, Object> changes= new HashMap<>();
        changes.put("descriptionHead", "El mejor tamal de Monterrey");

        ProductDescription currentProductDescription=iProductDescriptionService.updateProductDescriptionPartially(changes,1L);

        assertEquals(objectMapper.writeValueAsString(expectedProductDescription),objectMapper.writeValueAsString(currentProductDescription));
        assertTrue(compareProductsDescription(expectedProductDescription,currentProductDescription));

    }

    @Test
    void updateProductDescriptionPartially2Test() throws  Exception{

        String json="{\"productId\":1,\"descriptionHead\":\"El mejor tamal de Monterrey\",\"descriptionBody\":\"muy rico\",\"urlImage\":\"no available.jpg\"}";
        String jsonInput="{\"productId\":1,\"descriptionHead\":\"El mejor tamal del norte\",\"descriptionBody\":\"Elaborado  al estilo CDMX\",\"urlImage\":\"https://img.freepik.com/fotos-premium/nacatamal-servido-hoja-platano-cerca-nacatamal-nicaraguense-comida-nicaraguense-nacatamal_550253-296.jpg?w=996\"}";

        ProductDescription expectedProductDescription=objectMapper.readValue(json,ProductDescription.class);
        ProductDescription inputProductDescription=objectMapper.readValue(jsonInput,ProductDescription.class);



        when(iProductDescriptionRepository.findByProductId(1L)).thenReturn(Optional.of(inputProductDescription));
        when(iProductDescriptionRepository.save(any(ProductDescription.class))).thenAnswer(invocationOnMock -> invocationOnMock.getArgument(0,ProductDescription.class));


        Map<String, Object> changes= new HashMap<>();
        changes.put("descriptionHead", "El mejor tamal de Monterrey");
        changes.put("descriptionBody","muy rico");
        changes.put("urlImage","no available.jpg");

        ProductDescription currentProductDescription=iProductDescriptionService.updateProductDescriptionPartially(changes,1L);

        assertEquals(objectMapper.writeValueAsString(expectedProductDescription),objectMapper.writeValueAsString(currentProductDescription));
        assertTrue(compareProductsDescription(expectedProductDescription,currentProductDescription));

    }

    @Test
    void deleteProductDescriptionTest(){

        when(iProductDescriptionRepository.findByProductId(anyLong())).thenReturn(Optional.of(productsDescription.get(0)));
        doNothing().when(iProductDescriptionRepository).delete(any(ProductDescription.class));
        String message=iProductDescriptionService.deleteProductDescription(1L);
        assertEquals("The description for the product with id:1 has been deleted",message);
        verify(iProductDescriptionRepository,times(1)).delete(any(ProductDescription.class));
    }

    @Test
    void deleteProductDescriptionExceptionTest(){
        when(iProductDescriptionRepository.findByProductId(anyLong())).thenThrow(NotFoundProductDescriptionException.class);

        assertThrows(NotFoundProductDescriptionException.class,()->{
            String message = iProductDescriptionService.deleteProductDescription(3L);
        });
    }





}
