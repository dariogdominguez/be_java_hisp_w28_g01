package com.meli.be_java_hisp_w28_g01.integrationTest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.meli.be_java_hisp_w28_g01.dto.request.PostDto;
import com.meli.be_java_hisp_w28_g01.dto.request.ProductDto;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@SpringBootTest
@AutoConfigureMockMvc
public class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private static ObjectMapper objectMapper;

    public PostControllerTest() {
        this.objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    public void whenListAllPost_ShouldReturnListOk() throws Exception{
        mockMvc.perform(get("/products/all"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print());
    }

//Bonus - Add Post

    @Test
    @Order(1)
    void whenAddPostCalled_withValidPostDto_shouldReturnCreatedPost() throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        PostDto postDto = new PostDto();
        postDto.setDate(LocalDate.parse(LocalDate.now().minusDays(3).format(formatter),formatter));
        postDto.setUserId(1);
        postDto.setProductDto(new ProductDto(150,"Aire Acondicionado Split","A","Acme","Black","notes" ));
        postDto.setPrice(350000.0);
        postDto.setCategory(1);

        String payloadJson = objectMapper.writeValueAsString(postDto);
        
        mockMvc.perform(post("/products/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payloadJson)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/plain;charset=UTF-8"))
                .andDo(print());
    }


//T-0005 -> OK(WithoutOrder)
    @Test
    void whenGetProductsCalledWithoutOrder_shouldReturnPosts() throws Exception {
        int userId = 1;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        mockMvc.perform(get("/products/followed/{userId}/list", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.user_id").value(userId))
                .andExpect(jsonPath("$.posts").isArray())
                .andExpect(jsonPath("$.posts[0].date").value("31-12-2024"))

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
