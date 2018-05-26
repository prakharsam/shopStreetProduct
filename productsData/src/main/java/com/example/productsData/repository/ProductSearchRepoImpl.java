package com.example.productsData.repository;

import com.example.productsData.dto.ProductDto;
import com.example.productsData.dto.ProductSearchDto;
import org.springframework.stereotype.Service;

@Service
public class ProductSearchRepoImpl implements ProductSearchRepoInterface {

    @Override
    public boolean addProductToSearch(ProductSearchDto productSearchDto) {

        return false;
    }
}
