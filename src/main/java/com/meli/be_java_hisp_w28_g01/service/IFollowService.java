package com.meli.be_java_hisp_w28_g01.service;

import com.meli.be_java_hisp_w28_g01.dto.FollowDto;
import com.meli.be_java_hisp_w28_g01.dto.FollowersDto;
import com.meli.be_java_hisp_w28_g01.model.Follow;

import java.util.List;

public interface IFollowService {
    FollowDto addFollow(int userId, int userIdToFollow);
    List<Follow> getAll();
    FollowersDto getFollowersCount(int userId);
}
