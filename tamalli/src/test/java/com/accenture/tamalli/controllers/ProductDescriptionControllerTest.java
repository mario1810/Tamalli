package com.accenture.tamalli.controllers;

import com.accenture.tamalli.models.ProductDescription;
import com.accenture.tamalli.services.IProductDescriptionService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ProductDescriptionController.class)
public class ProductDescriptionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @MockBean
    private IProductDescriptionService iProductDescriptionService;

    @Test
    void getProductDescriptionByIdTest() throws Exception{

        String json ="{\"productId\":1,\"descriptionHead\":\"El mejor tamal de México\",\"descriptionBody\":\"Elaborado  al estilo CDMX\",\"urlImage\":\"https://img.freepik.com/fotos-premium/nacatamal-servido-hoja-platano-cerca-nacatamal-nicaraguense-comida-nicaraguense-nacatamal_550253-296.jpg?w=996\"}";

        ProductDescription productDescription= mapper.readValue(json, ProductDescription.class);
        when(iProductDescriptionService.getProductDescriptionById(1L)).thenReturn(productDescription);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/tamalli/descriptions/1"))
                .andExpect(status().isOk()); //check is response status is 200

    }

    @Test
    void getAllProductDescriptionTest() throws Exception{

        String json ="[{\"productId\":1,\"descriptionHead\":\"El mejor tamal de México\",\"descriptionBody\":\"Elaborado  al estilo CDMX\",\"urlImage\":\"https://img.freepik.com/fotos-premium/nacatamal-servido-hoja-platano-cerca-nacatamal-nicaraguense-comida-nicaraguense-nacatamal_550253-296.jpg?w=996\"},{\"productId\":2,\"descriptionHead\":\"El mejor tamal del norte\",\"descriptionBody\":\"Elaborado  al estilo Monterrey\",\"urlImage\":\"https://img.freepik.com/fotos-premium/nacatamal-servido-hoja-platano-cerca-nacatamal-nicaraguense-comida-nicaraguense-nacatamal_550253-296.jpg?w=996\"}]";
        List<ProductDescription> productDescriptionList=mapper.readValue(json, new TypeReference<List<ProductDescription>>() {});

        when(iProductDescriptionService.getAllProductDescription()).thenReturn(productDescriptionList);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/tamalli/descriptions"))
                .andExpect(status().isOk()); //check is response status is 200

    }

    @Test
    void createProductDescriptionTest() throws Exception{

        String jsonInput ="{\"descriptionHead\":\"El mejor tamal de México\",\"descriptionBody\":\"Elaborado  al estilo CDMX\",\"urlImage\":\"https://img.freepik.com/fotos-premium/nacatamal-servido-hoja-platano-cerca-nacatamal-nicaraguense-comida-nicaraguense-nacatamal_550253-296.jpg?w=996\"}";
        String json ="{\"productId\":1,\"descriptionHead\":\"El mejor tamal de México\",\"descriptionBody\":\"Elaborado  al estilo CDMX\",\"urlImage\":\"https://img.freepik.com/fotos-premium/nacatamal-servido-hoja-platano-cerca-nacatamal-nicaraguense-comida-nicaraguense-nacatamal_550253-296.jpg?w=996\"}";
        ProductDescription input= mapper.readValue(jsonInput, ProductDescription.class);
        ProductDescription productDescription= mapper.readValue(json, ProductDescription.class);

        when(iProductDescriptionService.createProductDescription(1L,input)).thenReturn(productDescription);

        MockHttpServletRequestBuilder mockRequest =
                MockMvcRequestBuilders.post("/api/tamalli/descriptions/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonInput)
                        .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(mockRequest)
                .andExpect(status().isCreated());

    }

    @Test
    void updateProductDescriptionTest() throws Exception{

        String jsonInput ="{\"descriptionHead\":\"El mejor tamal de Monterrey\",\"descriptionBody\":\"Elaborado  al estilo CDMX\",\"urlImage\":\"https://img.freepik.com/fotos-premium/nacatamal-servido-hoja-platano-cerca-nacatamal-nicaraguense-comida-nicaraguense-nacatamal_550253-296.jpg?w=996\"}";
        String json ="{\"productId\":1,\"descriptionHead\":\"El mejor tamal de Monterrey\",\"descriptionBody\":\"Elaborado  al estilo CDMX\",\"urlImage\":\"https://img.freepik.com/fotos-premium/nacatamal-servido-hoja-platano-cerca-nacatamal-nicaraguense-comida-nicaraguense-nacatamal_550253-296.jpg?w=996\"}";
        ProductDescription input= mapper.readValue(jsonInput, ProductDescription.class);
        ProductDescription productDescription= mapper.readValue(json, ProductDescription.class);

        when(iProductDescriptionService.updateProductDescription(1L,input)).thenReturn(productDescription);
        MockHttpServletRequestBuilder mockRequest =
                MockMvcRequestBuilders.put("/api/tamalli/descriptions/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonInput)
                        .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk()); //check is response status is 200
    }

    @Test
    void updateProductDescriptionPartiallyTest() throws Exception{

        String jsonInput ="{\"descriptionHead\":\"El mejor tamal de México\"}";
        String json ="{\"productId\":1,\"descriptionHead\":\"El mejor tamal de México\",\"descriptionBody\":\"Elaborado  al estilo CDMX\",\"urlImage\":\"https://img.freepik.com/fotos-premium/nacatamal-servido-hoja-platano-cerca-nacatamal-nicaraguense-comida-nicaraguense-nacatamal_550253-296.jpg?w=996\"}";

        Map<String,Object> changes= new HashMap<>();
        changes.put("descriptionHead","El mejor tamal de México");

        ProductDescription productDescription= mapper.readValue(json, ProductDescription.class);

        when(iProductDescriptionService.updateProductDescriptionPartially(changes,1L)).thenReturn(productDescription);

        MockHttpServletRequestBuilder mockRequest =
                MockMvcRequestBuilders.patch("/api/tamalli/descriptions/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonInput)
                        .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk()); //check is response status is 200
    }

    @Test
    void deleteProductDescriptionTest() throws Exception{

        when(iProductDescriptionService.deleteProductDescription(1L)).thenReturn("Product has been deleted");
        MvcResult mvcResult=mockMvc.perform(MockMvcRequestBuilders.delete("/api/tamalli/descriptions/1"))
                .andExpect(status().isOk()) //check is response status is 200
                .andReturn();
        assertEquals("Product has been deleted", mvcResult.getResponse().getContentAsString());
    }


}
