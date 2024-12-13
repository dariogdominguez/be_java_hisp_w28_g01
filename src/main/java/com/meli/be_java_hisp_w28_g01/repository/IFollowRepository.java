package com.meli.be_java_hisp_w28_g01.repository;

import com.meli.be_java_hisp_w28_g01.model.Follow;

import java.util.List;

public interface IFollowRepository {
    List<Follow> getAllFollows();
}
