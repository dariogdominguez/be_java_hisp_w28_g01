package com.meli.be_java_hisp_w28_g01.integrationTest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private static ObjectMapper objectMapper;

    public PostControllerTest() {
        this.objectMapper = new ObjectMapper();
    }

    @Test
    public void whenListAllPost_ShouldReturnListOk() throws Exception{
        mockMvc.perform(get("/products/all"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print());
    }
//T-0005 -> OK(WithoutOrder)
    @Test
    void whenGetProductsCalledWithoutOrder_shouldReturnPosts() throws Exception {
        int userId = 1;

        mockMvc.perform(get("/products/followed/{userId}/list", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.user_id").value(userId))
                .andExpect(jsonPath("$.posts").isArray())
                .andExpect(jsonPath("$.posts[0].date").value("31-12-2024"))
                .andExpect(jsonPath("$.posts[1].date").value("30-12-2024"))
                .andDo(print());
    }

    @Test
    public void whenGetProductsCalledWithUnexistingUserId_shouldReturnNotFound() throws Exception {
        int userId = -1;

        mockMvc.perform(get("/products/followed/{userId}/list", userId))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.message").value("El 'usuario' con el id '-1' no existe."));
    }
}
