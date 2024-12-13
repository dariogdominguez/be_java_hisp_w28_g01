package com.meli.be_java_hisp_w28_g01.controller;

import com.meli.be_java_hisp_w28_g01.service.IFollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class FollowController {
    @Autowired
    IFollowService followService;


    @PostMapping("/users/{userId}/follow/{userIdToFollow}")
    public ResponseEntity<?> addFollow(@PathVariable int userId, @PathVariable int userIdToFollow){
        return ResponseEntity.status(HttpStatus.OK).body(followService.addFollow(userId,userIdToFollow));
    }
}
