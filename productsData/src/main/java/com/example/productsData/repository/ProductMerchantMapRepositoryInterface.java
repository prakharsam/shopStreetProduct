package com.example.productsData.repository;

import com.example.productsData.model.ProductMerchantMapModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.ArrayList;

public interface ProductMerchantMapRepositoryInterface extends MongoRepository<ProductMerchantMapModel,Long> {

    public ArrayList<ProductMerchantMapModel> findByProductID(Long productID);

    public ProductMerchantMapModel findByProductIDAndMerchantID(Long productID ,Long merchantID);

    public Boolean existsByProductIDAndMerchantID(Long productID ,Long merchantID);

}
