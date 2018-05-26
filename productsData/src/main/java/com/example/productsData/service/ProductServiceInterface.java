package com.example.productsData.service;

import com.example.productsData.dto.*;

import java.util.ArrayList;

public interface ProductServiceInterface {
    public ProductDto addProduct(ProductDto productDto);

    public MerchantDto addMerchant(MerchantDto merchantDto);

    public ProductMerchantMapDto addProductMerchant(ProductMerchantMapDto productMerchantMapDto);

    public ProductDto getProductById(Long productID);

    public ArrayList<ProductDto> getProductsByCategoryID(Long categoryID);

    public ArrayList<ProductMerchantMapDto> getMerchantsByProductID(Long productID);

    public StockResponseDto updateStock(UpdateStockDto updateStockDto);

    public StockResponseDto checkAvailability(UpdateStockDto updateStockDto);

    public ProductDto updatePrice(Long productID);

    public CategoryDto addCategory(CategoryDto categoryDto);

}
