package com.meli.be_java_hisp_w28_g01.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.meli.be_java_hisp_w28_g01.dto.FollowersListDto;
import com.meli.be_java_hisp_w28_g01.dto.response.BuyerDto;
import com.meli.be_java_hisp_w28_g01.exception.NotFoundException;
import com.meli.be_java_hisp_w28_g01.model.Follow;
import com.meli.be_java_hisp_w28_g01.model.Seller;
import com.meli.be_java_hisp_w28_g01.repository.IFollowRepository;
import com.meli.be_java_hisp_w28_g01.service.IFollowersListService;
import com.meli.be_java_hisp_w28_g01.service.ISellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FollowersListService implements IFollowersListService {

    @Autowired
    IFollowRepository followRepository;

    @Autowired
    ISellerService sellerService;

    @Override
    public FollowersListDto getListOfFollowers(int userID) {
        Seller seller = null;
        try {
             seller = sellerService.getAll().stream().filter(f -> f.getId() == userID).findFirst().get();
        }catch (Exception e){
            throw new  NotFoundException(userID, "Vendedor");
        }
        List<Follow> sellerFollowsList = followRepository.getAll().stream().filter( f -> f.getSeller().getId() == userID).toList();
        List<BuyerDto> buyersFollowers = sellerFollowsList.stream().map(Follow::getBuyer).map( b -> new BuyerDto(b.getId(), b.getName())).toList();

        return new FollowersListDto(seller.getId(), seller.getName(), buyersFollowers);
    }
}
