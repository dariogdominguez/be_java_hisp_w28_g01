package com.meli.be_java_hisp_w28_g01.service.impl;

import com.meli.be_java_hisp_w28_g01.model.Seller;
import com.meli.be_java_hisp_w28_g01.service.ISellerService;

import java.util.List;

public class SellerServiceImpl implements ISellerService {
    @Override
    public List<Seller> getAll(){
        return sellerRepository.findAll();
    }

    @Override
    public Seller findById(int id){
        return sellerRepository.findAll()
                .stream()
                .filter(m -> m.getId() == id)
                .findFirst()
                .orElse(null);
    };

}
