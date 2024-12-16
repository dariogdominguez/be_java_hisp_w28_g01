package com.meli.be_java_hisp_w28_g01.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.meli.be_java_hisp_w28_g01.dto.PromoPostDto;
import com.meli.be_java_hisp_w28_g01.exception.NotFoundException;
import com.meli.be_java_hisp_w28_g01.model.PromoPost;
import com.meli.be_java_hisp_w28_g01.model.Seller;
import com.meli.be_java_hisp_w28_g01.repository.IPostRepository;
import com.meli.be_java_hisp_w28_g01.service.IPostService;
import com.meli.be_java_hisp_w28_g01.service.ISellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImpl implements IPostService {
    @Autowired
    ISellerService iSellerService;
    @Autowired
    IPostRepository iPostRepository;
    @Autowired
    ObjectMapper mapper;

    @Override
    public String addPromoPost(PromoPostDto promoPostDTO){
        Seller seller = iSellerService.findById(promoPostDTO.getUser_id())
                .orElseThrow(() -> new NotFoundException(promoPostDTO.getUser_id(), "Seller"));
        PromoPost promoPost = mapper.convertValue(promoPostDTO, PromoPost.class);
        promoPost.setSeller(seller);
        promoPost.setPrice(promoPost.getDiscount()*promoPost.getPrice());
        iPostRepository.addPromoPost(promoPost);
        return seller.getName();
    }
}
