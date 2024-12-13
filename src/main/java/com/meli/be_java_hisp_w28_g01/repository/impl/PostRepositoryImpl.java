package com.meli.be_java_hisp_w28_g01.repository.impl;

import com.meli.be_java_hisp_w28_g01.model.Post;
import com.meli.be_java_hisp_w28_g01.repository.IPostRepository;
import com.meli.be_java_hisp_w28_g01.util.DataLoader;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PostRepositoryImpl implements IPostRepository {
    private final List<Post> listOfPosts;
    private final String filePath = "post.json";

    public PostRepositoryImpl(DataLoader dataLoader)  {
        listOfPosts = dataLoader.loadDataBase(filePath);
    }

    @Override
    public List<Post> getAll() {
        return listOfPosts;
    }

}
