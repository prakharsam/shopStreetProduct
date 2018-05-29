package com.example.productsData.service;

import com.example.productsData.dto.*;

import java.util.ArrayList;

public interface ProductServiceInterface {
    public ProductDto addProduct(ProductDto productDto);

    public boolean deleteProduct(Long productID);

    public MerchantDto addMerchant(MerchantDto merchantDto);

    public ProductMerchantMapDto addProductMerchant(ProductMerchantMapDto productMerchantMapDto);

    public ProductDto getProductById(Long productID);

    public ArrayList<ProductDto> getProductsByCategoryID(Long categoryID);

    public ArrayList<ProductMerchantMapDto> getMerchantsByProductID(Long productID);

    public StockResponseDto updateStock(UpdateStockDto updateStockDto);

    public StockResponseDto checkAvailability(UpdateStockDto updateStockDto);

    public ProductDto updatePrice(Long productID);

    public CategoryDto addCategory(CategoryDto categoryDto);

    public ProductCartDto getProductForCart(Long productID);

    public  ProductSearchDto addProductForSearch(Long productID);

    public void computeWeightedFactor(Long productID,Long merchantID);

}
