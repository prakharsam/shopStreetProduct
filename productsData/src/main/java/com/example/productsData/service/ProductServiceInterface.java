package com.example.productsData.service;

import com.example.productsData.dto.*;

import java.util.ArrayList;

public interface ProductServiceInterface {
    /* Add a new product with product details */
    public ProductDto addProduct(ProductDto productDto);

    /* Delete a product */
    public boolean deleteProduct(Long productID);

    /* add a new merchant */
    public MerchantDto addMerchant(MerchantDto merchantDto);

    /* add product merchant mapping for a product */
    public ProductMerchantMapDto addProductMerchant(ProductMerchantMapDto productMerchantMapDto);

    public ProductDto getProductById(Long productID);

    public ArrayList<ProductDto> getProductsByCategoryID(Long categoryID);

    public ArrayList<ProductMerchantMapDto> getMerchantsByProductID(Long productID);

    public String getMerchantNameById(Long merchantID);

    /* Update stock details of the product during checkout */
    public StockResponseDto updateStock(UpdateStockDto updateStockDto);

    /* Check availability of the product during add to cart */
    public CheckAvailabilityDto checkAvailability(UpdateStockDto updateStockDto);

    public ProductDto updatePrice(Long productID);

    public CategoryDto addCategory(CategoryDto categoryDto);

    public ProductCartDto getProductForCart(Long productID);

    public  ProductSearchDto addProductForSearch(Long productID);

    public void computeWeightedFactor(Long productID,Long merchantID);

}
