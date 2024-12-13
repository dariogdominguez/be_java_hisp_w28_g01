package com.meli.be_java_hisp_w28_g01.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Component
public class DataLoader {
    private final ObjectMapper mapper;

    public DataLoader(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public <T> List<T> loadDataBase(String fileName) {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName)) {
            if (inputStream == null) {
                throw new RuntimeException("JSON no encontrado: " + fileName);
            }
            try {
                return mapper.readValue(inputStream, new TypeReference<List<T>>(){});
            } catch (IOException e) {
                throw new RuntimeException("No se pudieron obtener los datos del archivo: " + fileName, e);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error al acceder al JSON.", e);
        }
    }
}
