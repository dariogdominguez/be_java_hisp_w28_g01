package com.meli.be_java_hisp_w28_g01.repository.impl;

import com.meli.be_java_hisp_w28_g01.model.Follow;
import com.meli.be_java_hisp_w28_g01.repository.IFollowRepository;
import com.meli.be_java_hisp_w28_g01.util.DataLoader;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class FollowRepositoryImpl implements IFollowRepository {
    private final List<Follow> listOfFollows;
    private final String filePath = "follow.json";

    public FollowRepositoryImpl(DataLoader dataLoader) {
        this.listOfFollows = new ArrayList<>();
    }

    @Override
    public List<Follow> getAll() {
            return listOfFollows;
    }
}
