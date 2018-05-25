package com.example.productsData.service;

import com.example.productsData.dto.MerchantDto;
import com.example.productsData.dto.ProductDto;
import com.example.productsData.dto.ProductMerchantMapDto;
import com.example.productsData.dto.UpdateStockDto;

import java.util.ArrayList;

public interface ProductServiceInterface {
    public ProductDto addProduct(ProductDto productDto);

    public MerchantDto addMerchant(MerchantDto merchantDto);

    public ProductMerchantMapDto addProductMerchant(ProductMerchantMapDto productMerchantMapDto);

    public ProductDto getProductById(Long productID);

    public ArrayList<ProductDto> getProductsByCategoryID(Long categoryID);

    public ArrayList<ProductMerchantMapDto> getMerchantsByProductID(Long productID);

    public Boolean updateStock(UpdateStockDto updateStockDto);

}
