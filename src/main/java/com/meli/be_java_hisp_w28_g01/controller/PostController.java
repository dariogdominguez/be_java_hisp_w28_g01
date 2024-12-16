package com.meli.be_java_hisp_w28_g01.controller;

import com.meli.be_java_hisp_w28_g01.dto.PromoPostDto;
import com.meli.be_java_hisp_w28_g01.service.IPostService;
import com.meli.be_java_hisp_w28_g01.service.impl.PostServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostController {
    IPostService postService;
    public PostController(IPostService postService) {
        this.postService = postService;
    }

    @PostMapping("products/promo-post")
    ResponseEntity<String> addPromoPost(@RequestBody PromoPostDto promoPostDto){
        return ResponseEntity.ok().body("La publicación con promoción por el vendedor " +
                        "'"+ postService.addPromoPost(promoPostDto) +"'" +
                        "se ha hecho con éxito."
                );
    }
}
