package com.meli.be_java_hisp_w28_g01.service.impl;

import com.meli.be_java_hisp_w28_g01.model.Seller;
import com.meli.be_java_hisp_w28_g01.repository.ISellerRepository;
import com.meli.be_java_hisp_w28_g01.service.ISellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SellerServiceImpl implements ISellerService {
    @Autowired
    ISellerRepository sellerRepository;

    @Override
    public List<Seller> getAll(){
        return sellerRepository.getAll();
    }

    @Override
    public Seller findById(int id){
        return sellerRepository.getAll()
                .stream()
                .filter(m -> m.getId() == id)
                .findFirst()
                .orElse(null);
    }

}
