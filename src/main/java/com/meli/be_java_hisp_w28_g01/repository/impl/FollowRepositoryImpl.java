package com.meli.be_java_hisp_w28_g01.repository.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.meli.be_java_hisp_w28_g01.model.Follow;
import com.meli.be_java_hisp_w28_g01.repository.IFollowRepository;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class FollowRepositoryImpl implements IFollowRepository {

    @Override
    public List<Follow> getAllFollows() {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("follow.json")) {
            if (inputStream == null) {
                throw new RuntimeException("JSON no encontrado.");
            }
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            TypeReference<List<Follow>> typeRef = new TypeReference<>() {};
            List<Follow> Follows = null;
            try {
                Follows = objectMapper.readValue(inputStream, typeRef);
            } catch (IOException e) {
                throw new RuntimeException("No se pusieron obtener los datos de follows");
            }
            return Follows;
        } catch (IOException e) {
            throw new RuntimeException("Error al acceder al JSON.", e);
        }


    }
}
