package com.meli.be_java_hisp_w28_g01.repository.impl;

import com.meli.be_java_hisp_w28_g01.model.Product;
import com.meli.be_java_hisp_w28_g01.repository.IProductRepository;
import com.meli.be_java_hisp_w28_g01.util.DataLoader;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
class ProductRepositoryImpl implements IProductRepository {
    private final List<Product> listOfProduct;
    private final String filePath = "product.json";

    public ProductRepositoryImpl(DataLoader dataLoader) {
        listOfProduct = dataLoader.loadDataBase(filePath);
    }

    @Override
    public List<Product> getAll() {
        return listOfProduct;
    }

}

