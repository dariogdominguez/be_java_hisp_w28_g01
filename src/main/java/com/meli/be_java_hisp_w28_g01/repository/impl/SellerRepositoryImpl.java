package com.meli.be_java_hisp_w28_g01.repository.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.meli.be_java_hisp_w28_g01.model.Seller;
import com.meli.be_java_hisp_w28_g01.repository.ISellerRepository;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Repository
public class SellerRepositoryImpl implements ISellerRepository {
    private List<Seller> sellers = new ArrayList<>();

    public SellerRepositoryImpl() {
        sellers = loadDataBase();
    }

    @Override
    public List<Seller> getAll() {
        return sellers;
    }

    public List<Seller> loadDataBase() {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("seller.json")) {
            if (inputStream == null) {
                throw new RuntimeException("JSON no encontrado.");
            }
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            TypeReference<List<Seller>> typeRef = new TypeReference<>() {
            };
            List<Seller> sellers = null;
            try {
                sellers = objectMapper.readValue(inputStream, typeRef);
            } catch (IOException e) {
                throw new RuntimeException("No se pusieron obtener los datos de buyers");
            }
            return sellers;
        } catch (IOException e) {
            throw new RuntimeException("Error al acceder al JSON.", e);
        }
    }
}
