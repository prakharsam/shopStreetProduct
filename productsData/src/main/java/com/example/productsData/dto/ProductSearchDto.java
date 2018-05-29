package com.example.productsData.dto;

public class ProductSearchDto {

    private Long productID;
    private  String productCategoryName;
    private String productName;
    private String productImgUrl;
    private String productDescription;
    private String productBrandName;

    private Long merchantID;
    private String productMerchantName;
    private  Double productPrice;
    private  int merchantCount;

    public int getMerchantCount() {
        return merchantCount;
    }

    public void setMerchantCount(int merchantCount) {
        this.merchantCount = merchantCount;
    }

    public Long getMerchantID() {
        return merchantID;
    }

    public void setMerchantID(Long merchantID) {
        this.merchantID = merchantID;
    }

    public String getProductMerchantName() {
        return productMerchantName;
    }

    public void setProductMerchantName(String productMerchantName) {
        this.productMerchantName = productMerchantName;
    }

    public Double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Double productPrice) {
        this.productPrice = productPrice;
    }

    public ProductSearchDto() {
    }

    public ProductSearchDto(Long productID, String productCategoryName, String productName, String productImgUrl, String productDescription, String productBrandName, Long merchantID, String productMerchantName, Double productPrice, int merchantCount) {
        this.productID = productID;
        this.productCategoryName = productCategoryName;
        this.productName = productName;
        this.productImgUrl = productImgUrl;
        this.productDescription = productDescription;
        this.productBrandName = productBrandName;
        this.merchantID = merchantID;
        this.productMerchantName = productMerchantName;
        this.productPrice = productPrice;
        this.merchantCount = merchantCount;
    }

    public Long getProductID() {
        return productID;
    }

    public void setProductID(Long productID) {
        this.productID = productID;
    }

    public String getProductCategoryName() {
        return productCategoryName;
    }

    public void setProductCategoryName(String productCategoryName) {
        this.productCategoryName = productCategoryName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductImgUrl() {
        return productImgUrl;
    }

    public void setProductImgUrl(String productImgUrl) {
        this.productImgUrl = productImgUrl;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getProductBrandName() {
        return productBrandName;
    }

    public void setProductBrandName(String productBrandName) {
        this.productBrandName = productBrandName;
    }

}
