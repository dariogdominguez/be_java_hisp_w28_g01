package com.meli.be_java_hisp_w28_g01.integrationTest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

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
    public void whenListAllPostThenReturnListOk() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/products/all"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print());
    }

    @Test
    void  whenGet_listPostByProductType_shouldResponseAList () throws Exception {
        String productType = "Gamer";

        ResultMatcher status = status().isOk();
        ResultMatcher contentType = content().contentType("application/json");

        mockMvc.perform(MockMvcRequestBuilders.get("/products/type/{productType}", productType))
                .andExpect(status)
                .andExpect(contentType);
    }

    @Test
    void  whenGet_listPostByProductType_shouldThowException () throws Exception {
        String productType = "adafasf";

        ResultMatcher status = status().isBadRequest();
        ResultMatcher contentType = content().contentType("application/json");

        mockMvc.perform(MockMvcRequestBuilders.get("/products/type/{productType}", productType))
                .andExpect(status)
                .andExpect(contentType)
                .andExpect(jsonPath("$.message").value("La lista de 'productos de tipo adafasf' está vacía."));
    }


}
