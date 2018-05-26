package com.example.productsData.dto;

public class ProductSearchDto {

    private Long productId;
    //private Long categoryID;
    private  String productCategoryName;
    private String productName;
    private String productImgUrl;
    private String productDescription;
    private String productBrandName;

    public ProductSearchDto(Long productID, String categoryName, String productName, String productImgUrl, String productDescription, String productBrandName) {
        this.productId = productID;
        this.productCategoryName = categoryName;
        this.productName = productName;
        this.productImgUrl = productImgUrl;
        this.productDescription = productDescription;
        this.productBrandName = productBrandName;
    }

    public ProductSearchDto() {
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
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
