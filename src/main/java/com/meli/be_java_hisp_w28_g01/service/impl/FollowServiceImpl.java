package com.meli.be_java_hisp_w28_g01.service.impl;

import com.meli.be_java_hisp_w28_g01.model.Follow;
import com.meli.be_java_hisp_w28_g01.repository.IFollowRepository;
import com.meli.be_java_hisp_w28_g01.service.IBuyerService;
import com.meli.be_java_hisp_w28_g01.service.IFollowService;
import com.meli.be_java_hisp_w28_g01.service.ISellerService;
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
    ISellerService sellerService;

    @Override
    public Follow addFollow(int userId, int userIdToFollow) {
        List<Follow> followList = followRepository.getAll();

        boolean thereIsBuyer = buyerService.findById(userId).isPresent();
        boolean thereIsSeller = sellerService.findById(userIdToFollow).isPresent();
        if (!thereIsBuyer) throw new RuntimeException("No se encontraon vendedores con ese id");
        if (!thereIsSeller) throw new RuntimeException("No se encontraon vendedores con ese id");

        boolean alredyExistFollow = followList.stream().anyMatch( f -> f.getBuyer().getId() == userId && f.getSeller().getId() == userIdToFollow);
        if(alredyExistFollow){
            throw new RuntimeException("Este coprador ya sigue a este vendedor");
        }

        return followRepository.addFollow(buyerService.findById(userId).get(), sellerService.findById(userIdToFollow).get());
    }

    @Override
    public List<Follow> getAll() {
        return followRepository.getAll();
    }
}
