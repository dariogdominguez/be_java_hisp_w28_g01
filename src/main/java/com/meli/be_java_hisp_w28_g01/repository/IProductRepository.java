package com.meli.be_java_hisp_w28_g01.repository;

import com.meli.be_java_hisp_w28_g01.model.Product;

import java.util.List;

public interface IProductRepository {
    List<Product> getAll();
    /*Product getById(Long id);*/
}
