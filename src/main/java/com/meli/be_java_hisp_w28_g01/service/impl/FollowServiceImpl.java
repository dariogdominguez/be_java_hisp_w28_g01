package com.meli.be_java_hisp_w28_g01.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.meli.be_java_hisp_w28_g01.dto.FollowDto;
import com.meli.be_java_hisp_w28_g01.dto.FollowersDto;
import com.meli.be_java_hisp_w28_g01.model.Follow;
import com.meli.be_java_hisp_w28_g01.model.Seller;
import com.meli.be_java_hisp_w28_g01.repository.IFollowRepository;
import com.meli.be_java_hisp_w28_g01.service.IBuyerService;
import com.meli.be_java_hisp_w28_g01.service.IFollowService;
import com.meli.be_java_hisp_w28_g01.service.ISellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FollowServiceImpl implements IFollowService {

    ObjectMapper mapper = new ObjectMapper();

    @Autowired
    IFollowRepository followRepository;

    @Autowired
    IBuyerService buyerService;

    @Autowired
    ISellerService sellerService;

    @Override
    public FollowDto addFollow(int userId, int userIdToFollow) {
        List<Follow> followList = followRepository.getAll();

        boolean thereIsBuyer = buyerService.findById(userId).isPresent();
        boolean thereIsSeller = sellerService.findById(userIdToFollow).isPresent();
        if (!thereIsBuyer) throw new RuntimeException("No se encontraon vendedores con ese id");
        if (!thereIsSeller) throw new RuntimeException("No se encontraon vendedores con ese id");

        boolean alredyExistFollow = followList.stream().anyMatch( f -> f.getBuyer().getId() == userId && f.getSeller().getId() == userIdToFollow);
        if(alredyExistFollow){
            throw new RuntimeException("Este coprador ya sigue a este vendedor");
        }

        return mapper.convertValue(followRepository.addFollow(new Follow(buyerService.findById(userId).get(), sellerService.findById(userIdToFollow).get())), FollowDto.class);
    }

    @Override
    public List<Follow> getAll() {
        return followRepository.getAll();
    }

    @Override
    public FollowersDto getFollowersCount(int userId) {
        List<Follow> followList = getAll();
        Optional<Seller> seller = sellerService.findById(userId);

        if (seller.isEmpty()) {
            // Throw exception id not found
        }

        followList = followList.stream().filter(f -> f.getSeller().getId() == userId).toList();
        return new FollowersDto(seller.get().getId(), seller.get().getName(), followList.size());
    }
}
