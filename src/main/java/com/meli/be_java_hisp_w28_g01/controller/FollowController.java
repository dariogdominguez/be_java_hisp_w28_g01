package com.meli.be_java_hisp_w28_g01.controller;

import com.meli.be_java_hisp_w28_g01.service.IFollowService;
import com.meli.be_java_hisp_w28_g01.service.IFollowersListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class FollowController {
    @Autowired
    IFollowService followService;
    @Autowired
    IFollowersListService followersListService;


    @PostMapping("/users/{userId}/follow/{userIdToFollow}")
    public ResponseEntity<?> addFollow(@PathVariable int userId, @PathVariable int userIdToFollow){
        return ResponseEntity.status(HttpStatus.OK).body(followService.addFollow(userId,userIdToFollow));
    }

    @GetMapping("/users/{userId}/followed/list")
    public ResponseEntity<?> getFollowedSellers(@PathVariable int userId){
        return new ResponseEntity<>(followService.getFollowedSeller(userId),HttpStatus.OK);
    }


    @GetMapping("/users/{userId}/followers/count")
    public ResponseEntity<?> getFollowersCount(@PathVariable int userId){
        return new ResponseEntity<>(followService.getFollowersCount(userId), HttpStatus.OK);
    }

    @GetMapping("users/{userId}/followers/list")
    public ResponseEntity<?> getFollowerList(@PathVariable int userId){
        return ResponseEntity.status(HttpStatus.OK).body(followersListService.getListOfFollowers(userId));
    }
}
