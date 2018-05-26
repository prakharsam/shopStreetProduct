package com.example.productsData.repository;

import com.example.productsData.model.CategoryModel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CategoryRepositoryInterface extends MongoRepository<CategoryModel,Long> {
}
