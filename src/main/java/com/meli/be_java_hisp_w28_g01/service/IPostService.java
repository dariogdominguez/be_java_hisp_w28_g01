package com.meli.be_java_hisp_w28_g01.service;

import com.meli.be_java_hisp_w28_g01.dto.request.PostDto;
import com.meli.be_java_hisp_w28_g01.model.Post;

import java.util.List;

public interface IPostService {
    List<Post> getAll();
    Post getByid(int id);
    String add(PostDto postDto);
}
