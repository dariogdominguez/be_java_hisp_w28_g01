package com.meli.be_java_hisp_w28_g01.service;

import com.meli.be_java_hisp_w28_g01.model.Seller;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ISellerService {
    List<Seller> getAll();
    Seller findById(int id);
    }
