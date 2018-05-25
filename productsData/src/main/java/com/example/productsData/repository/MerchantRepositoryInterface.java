package com.example.productsData.repository;

import com.example.productsData.model.MerchantModel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MerchantRepositoryInterface extends MongoRepository<MerchantModel,Long> {
}
