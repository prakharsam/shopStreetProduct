package com.example.productsData.repository;

import com.example.productsData.model.ProductMerchantMapModel;
import com.example.productsData.model.ProductModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.ArrayList;

public interface ProductRepositoryInterface extends MongoRepository<ProductModel,Long> {

//    @Query("select count(*) from productdetails1 where categoryID = ?1")
    public ArrayList<ProductModel> findByCategoryID(Long categoryID);

 }
