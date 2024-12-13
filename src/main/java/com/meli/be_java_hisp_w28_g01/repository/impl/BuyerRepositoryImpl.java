package com.meli.be_java_hisp_w28_g01.repository.impl;

import com.meli.be_java_hisp_w28_g01.model.Buyer;
import com.meli.be_java_hisp_w28_g01.repository.IBuyerRepository;
import com.meli.be_java_hisp_w28_g01.util.DataLoader;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.List;

@Repository
public class BuyerRepositoryImpl implements IBuyerRepository {
    private final List<Buyer> listOfBuyers;
    private final String filePath = "buyer.json";

    public BuyerRepositoryImpl(DataLoader dataLoader) throws IOException {
        listOfBuyers = dataLoader.loadDataBase(filePath);
    }

    @Override
    public List<Buyer> getAll() {
        return listOfBuyers;
    }

}
