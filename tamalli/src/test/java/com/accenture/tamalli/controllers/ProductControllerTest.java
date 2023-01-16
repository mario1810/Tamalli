package com.accenture.tamalli.controllers;

import com.accenture.tamalli.dto.products.ProductPriceDTO;
import com.accenture.tamalli.models.*;
import com.accenture.tamalli.services.IProductService;
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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ProductController.class)
public class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @MockBean
    private IProductService iProductService;

    @Test
    void addDrinkTest()throws Exception{

        String inputJson="{\n" +
                "    \"productId\": 4,\n" +
                "    \"productName\": \"Chocolate\",\n" +
                "    \"price\": 10.00,\n" +
                "    \"productType\": \"Drink\",\n" +
                "    \"capacityLiters\": 0.3\n" +
                "}";

        String json="{\"productId\":7,\"productName\":\"Chocolate\",\"price\":10.00,\"productType\":\"Drink\",\"capacityLiters\":0.3}";

        Drink drink= mapper.readValue(json, Drink.class);

        when(iProductService.addDrink(any(Drink.class))).thenReturn(drink);

        MockHttpServletRequestBuilder mockRequest =
                MockMvcRequestBuilders.post("/api/tamalli/products/drink")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(inputJson)
                        .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(mockRequest)
                .andExpect(status().isCreated());
    }

    @Test
    void addTamalTest() throws Exception{

        String inputJson="{\"productId\":4,\"productName\":\"Tamal dulce\",\"price\":13.00,\"productType\":\"Food\",\"weightKilogram\":0.25}";

        String json= "{\"productId\":8,\"productName\":\"Tamal dulce\",\"price\":13.00,\"productType\":\"Food\",\"weightKilogram\":0.25}";
        Tamal tamal= mapper.readValue(json, Tamal.class);

        when(iProductService.addTamal(any(Tamal.class))).thenReturn(tamal);

        MockHttpServletRequestBuilder mockRequest =
                MockMvcRequestBuilders.post("/api/tamalli/products/tamal")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(inputJson)
                        .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(mockRequest)
                .andExpect(status().isCreated());
    }

    @Test
    void getDrinkByIdTest() throws Exception{

        String json= "{\"productId\":4,\"productName\":\"Café\",\"price\":10.00,\"productType\":\"Drink\",\"capacityLiters\":0.3}";

        Drink drink = mapper.readValue(json, Drink.class);
        when(iProductService.getDrinkById(4L)).thenReturn(drink);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/tamalli/products/drink/4"))
                .andExpect(status().isOk()); //check is response status is 200

    }

    @Test
    void getTamalByIdTest() throws Exception{

        String json= "{\"productId\":2,\"productName\":\"Tamal rajas\",\"price\":13.00,\"productType\":\"Food\",\"weightKilogram\":0.25}";

        Tamal tamal = mapper.readValue(json, Tamal.class);
        when(iProductService.getTamalById(2L)).thenReturn(tamal);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/tamalli/products/1"))
                .andExpect(status().isOk()); //check is response status is 200

    }

    @Test
    void getProductByIdTest() throws Exception{

        String json= "{\"productId\":1,\"productName\":\"Tamal verde\",\"price\":12.50,\"productType\":\"Food\",\"weightKilogram\":0.25}\n";

        Product tamal = mapper.readValue(json, Tamal.class);
        when(iProductService.getProductById(1L)).thenReturn(tamal);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/tamalli/products/1"))
                .andExpect(status().isOk()); //check is response status is 200

    }

    @Test
    void getAllDrinksTest() throws Exception{

        String json = "[{\"productId\":4,\"productName\":\"Café\",\"price\":10.00,\"productType\":\"Drink\",\"capacityLiters\":0.3},{\"productId\":5,\"productName\":\"Atole Champurrado\",\"price\":12.00,\"productType\":\"Drink\",\"capacityLiters\":0.3},{\"productId\":6,\"productName\":\"Atole galleta\",\"price\":14.00,\"productType\":\"Drink\",\"capacityLiters\":0.3}]";

        List<Drink> drinks = mapper.readValue(json, new TypeReference<List<Drink>>() {});
        when(iProductService.getAllDrinks()).thenReturn(drinks);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/tamalli/products/drink"))
                .andExpect(status().isOk()); //check is response status is 200

    }

    @Test
    void getAllProductTest() throws Exception{

        String jsonDrinks = "[{\"productId\":4,\"productName\":\"Café\",\"price\":10.00,\"productType\":\"Drink\",\"capacityLiters\":0.3},{\"productId\":5,\"productName\":\"Atole Champurrado\",\"price\":12.00,\"productType\":\"Drink\",\"capacityLiters\":0.3},{\"productId\":6,\"productName\":\"Atole galleta\",\"price\":14.00,\"productType\":\"Drink\",\"capacityLiters\":0.3}]";
        List<Drink> drinks = mapper.readValue(jsonDrinks, new TypeReference<List<Drink>>() {});

        String jsonTamales = "[{\"productId\":1,\"productName\":\"Tamal verde\",\"price\":12.50,\"productType\":\"Food\",\"weightKilogram\":0.25},{\"productId\":2,\"productName\":\"Tamal rajas\",\"price\":13.00,\"productType\":\"Food\",\"weightKilogram\":0.25},{\"productId\":3,\"productName\":\"Tamal al pastor\",\"price\":17.25,\"productType\":\"Food\",\"weightKilogram\":0.25}]";
        List<Tamal> tamales = mapper.readValue(jsonTamales, new TypeReference<List<Tamal>>() {});

        List<Product> products = new ArrayList<>(tamales);
        products.addAll(drinks.stream().map(drink->(Product)drink).collect(Collectors.toList()));


        when(iProductService.getAllProduct()).thenReturn(products);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/tamalli/products"))
                .andExpect(status().isOk()); //check is response status is 200

    }

    @Test
    void getAllTamalesTest() throws Exception{

        String json = "[{\"productId\":1,\"productName\":\"Tamal verde\",\"price\":12.50,\"productType\":\"Food\",\"weightKilogram\":0.25},{\"productId\":2,\"productName\":\"Tamal rajas\",\"price\":13.00,\"productType\":\"Food\",\"weightKilogram\":0.25},{\"productId\":3,\"productName\":\"Tamal al pastor\",\"price\":17.25,\"productType\":\"Food\",\"weightKilogram\":0.25}]";

        List<Tamal> tamales = mapper.readValue(json, new TypeReference<List<Tamal>>() {});
        when(iProductService.getAllTamales()).thenReturn(tamales);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/tamalli/products/tamal"))
                .andExpect(status().isOk()); //check is response status is 200


    }

    @Test
    void changeProductPriceTest() throws Exception{

        String jsonInput="{\n" +
                "    \"productId\":20,\n" +
                "    \"price\":100.0\n" +
                "}";

        String json= "{\"productId\":2,\"productName\":\"Tamal rajas\",\"price\":100.0,\"productType\":\"Food\",\"weightKilogram\":0.25}";
        Product product= mapper.readValue(json,Tamal.class);

        when(iProductService.changeProductPrice(any(ProductPriceDTO.class))).thenReturn(product);

        MockHttpServletRequestBuilder mockRequest =
                MockMvcRequestBuilders.put("/api/tamalli/products/price")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonInput)
                        .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk()); //check is response status is 200

    }

    @Test
    void deleteProductTest() throws Exception{

        when(iProductService.deleteProduct(1L)).thenReturn("The product with id:1 has been deleted");

        MvcResult mvcResult=mockMvc.perform(MockMvcRequestBuilders.delete("/api/tamalli/products/1"))
                .andExpect(status().isOk()) //check is response status is 200
                .andReturn();
        assertEquals("The product with id:1 has been deleted", mvcResult.getResponse().getContentAsString());
    }


}
