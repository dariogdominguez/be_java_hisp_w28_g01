package com.meli.be_java_hisp_w28_g01.service.impl;

import com.meli.be_java_hisp_w28_g01.model.Follow;
import com.meli.be_java_hisp_w28_g01.repository.IBuyerRepository;
import com.meli.be_java_hisp_w28_g01.repository.IFollowRepository;
import com.meli.be_java_hisp_w28_g01.service.IBuyerService;
import com.meli.be_java_hisp_w28_g01.service.IFollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FollowServiceImpl implements IFollowService {

    @Autowired
    IFollowRepository followRepository;
    @Autowired
    IBuyerService buyerService;
    @Autowired
    IBuyerRepository buyerRepository;

    @Override
    public Follow addFollow(int userId, int userIdToFollow) {
        List<Follow> followList = followRepository.getAll();

        boolean alredyExistFollow = followList.stream().anyMatch( f -> f.getBuyer().getId() == userId && f.getSeller().getId() == userIdToFollow);
        if(alredyExistFollow){
            throw new RuntimeException("Este coprador ya sigue a este vendedor");
        }
        Boolean thereIsBuyer = buyerService.findById(userId).isPresent();
        return null;
    }

    @Override
    public List<Follow> getAll() {
        return followRepository.getAll();
    }
}
