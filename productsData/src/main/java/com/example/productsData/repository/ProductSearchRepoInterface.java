package com.example.productsData.repository;

import com.example.productsData.dto.ProductSearchDto;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductSearchRepoInterface {

    public boolean addProductToSearch(ProductSearchDto productSearchDto);

}
