package com.meli.be_java_hisp_w28_g01.controller;

import com.meli.be_java_hisp_w28_g01.dto.request.PostDto;
import com.meli.be_java_hisp_w28_g01.service.IPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class PostController {

    IPostService service;

    public PostController(IPostService service) {
        this.service = service;
    }

    @GetMapping("/all")
    public ResponseEntity<?> listAllPosts (){
        return ResponseEntity.ok().body(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> listAllPosts (@PathVariable int id){
        return ResponseEntity.ok().body(service.getByid(id));
    }

    @PostMapping("/post")
    public ResponseEntity<?> addPost(@RequestBody PostDto postDto){
        return ResponseEntity.ok().body(service.add(postDto));
    }




}
