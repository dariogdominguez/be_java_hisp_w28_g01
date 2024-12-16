package com.meli.be_java_hisp_w28_g01.service;

import com.meli.be_java_hisp_w28_g01.dto.FollowDto;
import com.meli.be_java_hisp_w28_g01.dto.FollowersListDto;

import java.util.List;

public interface IFollowersListService {
    FollowersListDto getListOfFollowers (int userID);
}
