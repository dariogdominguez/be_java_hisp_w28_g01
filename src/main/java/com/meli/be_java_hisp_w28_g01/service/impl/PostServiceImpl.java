package com.meli.be_java_hisp_w28_g01.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.meli.be_java_hisp_w28_g01.dto.request.PostDto;
import com.meli.be_java_hisp_w28_g01.exception.NotFoundException;
import com.meli.be_java_hisp_w28_g01.model.Post;
import com.meli.be_java_hisp_w28_g01.model.Product;
import com.meli.be_java_hisp_w28_g01.model.Seller;
import com.meli.be_java_hisp_w28_g01.repository.IPostRepository;
import com.meli.be_java_hisp_w28_g01.service.IPostService;
import com.meli.be_java_hisp_w28_g01.service.IProductService;
import com.meli.be_java_hisp_w28_g01.service.ISellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PostServiceImpl implements IPostService {
    @Autowired
    ObjectMapper mapper;
    @Autowired
    IPostRepository repository;

    @Autowired
    IProductService productService;
    @Autowired
    ISellerService sellerService;


    @Override
    public List<Post> getAll() {
        return repository.getAll();
    }

    @Override
    public Post getByid(int id) {
        if(id<0){
            throw new IllegalArgumentException("Id no válido");
        }
        return repository.getAll().stream().filter(p -> p.getId()==id).findFirst().orElse(null);
    }

    @Override
    public String add(PostDto postDto) {
        Product p = productService.findById(postDto.getProductoDto().getId()).orElse(null);
        if (p.equals(null)){
            p = productService.add(postDto.getProductoDto());
        }
        Seller seller = sellerService.findById(postDto.getUserId()).orElse(null);
        if (seller.equals(null)){
            throw new NotFoundException(postDto.getUserId(), "Seller");
        }
        LocalDate formatedDate = LocalDate.of(postDto.getDate().getYear(),postDto.getDate().getMonth(), postDto.getDate().getDayOfMonth());
        Post post = new Post(
                repository.getAll().size()+1,
                seller,
                formatedDate,
                p,
                postDto.getCategory(),
                postDto.getPrice()
        );
        repository.add(post);
        return "Se creó la publicación correctamente con el id: " + post.getId();



    }
}
