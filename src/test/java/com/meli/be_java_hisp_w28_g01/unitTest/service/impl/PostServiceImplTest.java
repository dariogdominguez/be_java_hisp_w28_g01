package com.meli.be_java_hisp_w28_g01.unitTest.service.impl;

import com.meli.be_java_hisp_w28_g01.dto.response.FollowedSellersDto;
import com.meli.be_java_hisp_w28_g01.dto.response.PostByUserDto;
import com.meli.be_java_hisp_w28_g01.dto.response.SellerDto;
import com.meli.be_java_hisp_w28_g01.model.Post;
import com.meli.be_java_hisp_w28_g01.model.Product;
import com.meli.be_java_hisp_w28_g01.model.Seller;
import com.meli.be_java_hisp_w28_g01.repository.impl.PostRepositoryImpl;
import com.meli.be_java_hisp_w28_g01.service.impl.FollowServiceImpl;
import com.meli.be_java_hisp_w28_g01.service.impl.PostServiceImpl;
import org.assertj.core.api.AbstractLocalDateAssert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.Assert;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PostServiceImplTest {
    @Mock
    private FollowServiceImpl followService;
    @Mock
    private PostRepositoryImpl postRepository;
    @InjectMocks
    private PostServiceImpl postService;

    private List<Post> postList;

    @BeforeEach
    void setUp(){
        postList = new ArrayList<>();
        Seller seller1 = new Seller();
        seller1.setId(1);
        seller1.setName("Pedro");
        Post post1 = new Post();
        post1.setId(1);
        post1.setSeller(seller1);
        post1.setDate(LocalDate.now().minusDays(3));
        post1.setProduct(new Product(1,"Product A","A","BrandA","Blue","notes"));
        post1.setCategory(2);
        post1.setPrice(80.0);

        Post post2 = new Post();
        post2.setId(2);
        post2.setSeller(seller1);
        post2.setDate(LocalDate.now().minusDays(5));
        post2.setProduct(new Product(2,"Product B","B","BrandB","Red","notes"));
        post2.setCategory(4);
        post2.setPrice(50.0);

        postList.add(post1);
        postList.add(post2);

    }

//T-0005 -> OK
    @ParameterizedTest
    @ValueSource(strings = {"date_asc", "date_desc"})
    void whenGetPostByUserOrderedByDateIsCalled_shouldAcceptValidOrderingTypes(String order) {
        int userId = 1;
        FollowedSellersDto followedSellersDto = new FollowedSellersDto(1,"Tomas",List.of(new SellerDto(1,"Pedro")));
        when(followService.getFollowedSeller(1)).thenReturn(followedSellersDto);
        when(postRepository.getAll()).thenReturn(postList);

        assertDoesNotThrow(() -> {
            postService.getPostByUserOrderedByDate(userId, order);
        });
    }
//T-0005 -> NO_OK
    @Test
    void whenGetPostByUserOrderedByDateIsCalledWithInvalidOrder_shouldThrowException() {
        int userId = 3;
        String order = "invalid_order";

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            postService.getPostByUserOrderedByDate(userId, order);
        });
        assertEquals("Se ingresó un tipo de ordenamiento incorrecto", thrown.getMessage());
    }
//T-0006 -> OK
    @ParameterizedTest
    @ValueSource(strings = {"date_asc", "date_desc"})
    void whenGetPostByUserOrderedByDateIsCalledWithAscOrder_shouldReturnOrderedList(String order){
        int userId = 1;
        FollowedSellersDto followedSellersDto = new FollowedSellersDto(1,"Tomas",List.of(new SellerDto(1,"Pedro")));
        when(followService.getFollowedSeller(1)).thenReturn(followedSellersDto);
        when(postRepository.getAll()).thenReturn(postList);
        PostByUserDto postByUserDtoFound = postService.getPostByUserOrderedByDate(userId,order);
        Assert.notEmpty(postByUserDtoFound.getPosts(),"La lista de posts se encuentra vacia");
        validatePostDates(postByUserDtoFound,order);
    }

    private AbstractLocalDateAssert validatePostDates(PostByUserDto postByUserDto,String order){
        if (order.equals("date_asc")) {
            return assertThat(postByUserDto.getPosts().get(0).getDate()).isBefore(postByUserDto.getPosts().get(1).getDate());
        }else{
            return assertThat(postByUserDto.getPosts().get(0).getDate()).isAfter(postByUserDto.getPosts().get(1).getDate());
        }
    }

}
