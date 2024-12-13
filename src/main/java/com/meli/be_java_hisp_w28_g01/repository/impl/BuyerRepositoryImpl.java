package com.meli.be_java_hisp_w28_g01.repository.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.meli.be_java_hisp_w28_g01.model.Buyer;
import com.meli.be_java_hisp_w28_g01.repository.IBuyerRepository;
import org.springframework.stereotype.Repository;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BuyerRepositoryImpl implements IBuyerRepository {
    private List<Buyer> listOfBuyers = new ArrayList<>();

    public BuyerRepositoryImpl() throws IOException {
        listOfBuyers = loadDataBase();
    }

    @Override
    public List<Buyer> getAll() {
        return listOfBuyers;
    }

    public List<Buyer> loadDataBase() {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("buyer.json")) {
            if (inputStream == null) {
                throw new RuntimeException("JSON no encontrado.");
            }
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            TypeReference<List<Buyer>> typeRef = new TypeReference<>() {
            };
            List<Buyer> buyers = null;
            try {
                buyers = objectMapper.readValue(inputStream, typeRef);
            } catch (IOException e) {
                throw new RuntimeException("No se pusieron obtener los datos de buyers");
            }
            return buyers;
        } catch (IOException e) {
            throw new RuntimeException("Error al acceder al JSON.", e);
        }
    }
}
