package com.meli.be_java_hisp_w28_g01.service;

import com.meli.be_java_hisp_w28_g01.dto.request.PostDto;
import com.meli.be_java_hisp_w28_g01.dto.response.PostByUserDto;
import com.meli.be_java_hisp_w28_g01.dto.request.PromoPostDto;
import com.meli.be_java_hisp_w28_g01.dto.response.PostDtoResponse;
import com.meli.be_java_hisp_w28_g01.dto.response.ResponsePostDto;
import com.meli.be_java_hisp_w28_g01.model.Post;

import java.util.List;

public interface IPostService {
    List<ResponsePostDto> getAll();
    PostDtoResponse getByid(int id);
    String add(PostDto postDto);
    String addPromoPost(PromoPostDto promoPostDto);
    PostByUserDto getPostsByUser(int userId);
    PostByUserDto getPostByUserOrderedByDate(int userId, String order);
}
