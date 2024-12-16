package com.meli.be_java_hisp_w28_g01.repository.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.meli.be_java_hisp_w28_g01.model.Post;
import com.meli.be_java_hisp_w28_g01.repository.IPostRepository;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PostRepositoryImpl implements IPostRepository {
    private List<Post> listOfPosts = new ArrayList<>();

    public PostRepositoryImpl() throws IOException {
        listOfPosts = loadDataBase();
    }

    @Override
    public List<Post> getAll() {
        return listOfPosts;
    }

    @Override
    public boolean add(Post post) {
        return listOfPosts.add(post);
    }


    public List<Post> loadDataBase() {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("post.json")) {
            if (inputStream == null) {
                throw new RuntimeException("JSON no encontrado.");
            }
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            TypeReference<List<Post>> typeRef = new TypeReference<>() {
            };
            List<Post> posts = null;
            try {
                posts = objectMapper.readValue(inputStream, typeRef);
            } catch (IOException e) {
                throw new RuntimeException("No se pusieron obtener los datos de als publicaciones");
            }
            return posts;
        } catch (IOException e) {
            throw new RuntimeException("Error al acceder al JSON.", e);
        }
    }
}
