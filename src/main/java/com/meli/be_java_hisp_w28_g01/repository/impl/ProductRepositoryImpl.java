package com.meli.be_java_hisp_w28_g01.repository.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.meli.be_java_hisp_w28_g01.model.Product;
import com.meli.be_java_hisp_w28_g01.repository.IProductRepository;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Repository
class ProductRepositoryImpl implements IProductRepository {

    private List<Product> listOfProduct = new ArrayList<>();

    public ProductRepositoryImpl() throws IOException {
        listOfProduct = loadDataBase();
    }

    @Override
    public List<Product> getAll() {
        return listOfProduct;
    }

    @Override
    public Product add(Product product) {
        listOfProduct.add(product);
        return product;
    }

    public List<Product> loadDataBase() {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("product.json")) {
            if (inputStream == null) {
                throw new RuntimeException("JSON no encontrado.");
            }
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            TypeReference<List<Product>> typeRef = new TypeReference<>() {
            };
            List<Product> products = null;
            try {
                products = objectMapper.readValue(inputStream, typeRef);
            } catch (IOException e) {
                throw new RuntimeException("No se pusieron obtener los datos de los productos.");
            }
            return products;
        } catch (IOException e) {
            throw new RuntimeException("Error al acceder al JSON.", e);
        }
    }
}

