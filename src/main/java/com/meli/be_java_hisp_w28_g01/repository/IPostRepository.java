package com.meli.be_java_hisp_w28_g01.repository;

import com.meli.be_java_hisp_w28_g01.model.Post;
import com.meli.be_java_hisp_w28_g01.model.PromoPost;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface IPostRepository {
    List<Post> getAll();
    void addPromoPost(PromoPost promoPost);
}
