package com.meli.be_java_hisp_w28_g01.repository.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.meli.be_java_hisp_w28_g01.model.Follow;
import com.meli.be_java_hisp_w28_g01.repository.IFollowRepository;
import org.springframework.stereotype.Repository;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Repository
public class FollowRepositoryImpl implements IFollowRepository {
    private List<Follow> follows = new ArrayList<>();

    public FollowRepositoryImpl() {
        this.follows = loadFollows();
    }

    // Método para cargar la lista desde el archivo JSON
    private List<Follow> loadFollows() {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("follow.json")) {
            if (inputStream == null) {
                throw new RuntimeException("JSON no encontrado.");
            }
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            TypeReference<List<Follow>> typeRef = new TypeReference<>() {};
            return objectMapper.readValue(inputStream, typeRef);
        } catch (IOException e) {
            throw new RuntimeException("Error al acceder al JSON.", e);
        }
    }

    @Override
    public List<Follow> getAll() {
        return follows;
    }

    @Override
    public Follow addFollow(Follow newFollow) {
        follows.add(newFollow);
        return newFollow;
    }

}
