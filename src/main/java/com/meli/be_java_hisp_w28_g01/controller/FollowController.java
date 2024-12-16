package com.meli.be_java_hisp_w28_g01.controller;

import com.meli.be_java_hisp_w28_g01.model.Follow;
import com.meli.be_java_hisp_w28_g01.service.IFollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class FollowController {
    @Autowired
    IFollowService followService;


    @PostMapping("/users/{userId}/follow/{userIdToFollow}")
    public ResponseEntity<?> addFollow(@PathVariable int userId, @PathVariable int userIdToFollow){
        return ResponseEntity.status(HttpStatus.OK).body(followService.addFollow(userId,userIdToFollow));
    }

    @GetMapping("/users/{userId}/followers/count")
    public ResponseEntity<?> getFollowersCount(@PathVariable int userId){
        return new ResponseEntity<>(followService.getFollowersCount(userId), HttpStatus.OK);
    }

    @PostMapping("/users/{userId}/unfollow/{userIdToUnfollow}")
    public ResponseEntity<?> deleteFollow(@PathVariable int userId, @PathVariable int userIdToUnfollow){
        return new ResponseEntity<>(followService.deleteFollow(userId, userIdToUnfollow), HttpStatus.OK);
    }
}
