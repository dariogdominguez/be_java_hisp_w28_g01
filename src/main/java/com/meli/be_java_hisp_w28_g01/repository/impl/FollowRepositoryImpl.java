package com.meli.be_java_hisp_w28_g01.repository.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.meli.be_java_hisp_w28_g01.model.Follow;
import com.meli.be_java_hisp_w28_g01.repository.IFollowRepository;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Repository
public class FollowRepositoryImpl implements IFollowRepository {
    List<Follow> follows = null;

    @Override
    public List<Follow> getAll() {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("follow.json")) {
            if (inputStream == null) {
                throw new RuntimeException("JSON no encontrado.");
            }
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            TypeReference<List<Follow>> typeRef = new TypeReference<>() {};
            try {
                follows = objectMapper.readValue(inputStream, typeRef);
            } catch (IOException e) {
                throw new RuntimeException("No se pusieron obtener los datos de follows");
            }
            return follows;
        } catch (IOException e) {
            throw new RuntimeException("Error al acceder al JSON.", e);
        }

    }

    public Follow addFollow(Follow newFollow){
        follows.add(newFollow);
        return newFollow;
    }

    @Override
    public Follow deleteFollow(int userId, int userIdToUnfollow) {
        Follow followToDelete = follows.stream().filter(f -> f.getBuyer().getId() == userId && f.getSeller().getId() == userIdToUnfollow).toList().getFirst();
        follows.remove(followToDelete);
        return followToDelete;
    }
}
