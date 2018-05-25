package com.example.productsData.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = ProductModel.COLLECTION_NAME)
public class ProductModel {

    public static final String COLLECTION_NAME="productdetails1";

    @Id
    private Long productID;
    private Long categoryID;
    private String productName;
    private String productImgUrl;
    private String productDescription;
    private String productBrandName;

    private String productColor;
    private String productSize;
    private String productRamSize;
    private String productOS;

    private Double productPrice;
    private Long merchantID;
    private String merchantName;

    public static String getCollectionName() {
        return COLLECTION_NAME;
    }

    public Double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Double productPrice) {
        this.productPrice = productPrice;
    }

    public Long getMerchantID() {
        return merchantID;
    }

    public void setMerchantID(Long merchantID) {
        this.merchantID = merchantID;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public Long getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(Long categoryID) {
        this.categoryID = categoryID;
    }

    public Long getProductID() {
        return productID;
    }

    public void setProductID(Long productID) {
        this.productID = productID;
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

    public String getProductColor() {
        return productColor;
    }

    public void setProductColor(String productColor) {
        this.productColor = productColor;
    }

    public String getProductSize() {
        return productSize;
    }

    public void setProductSize(String productSize) {
        this.productSize = productSize;
    }

    public String getProductRamSize() {
        return productRamSize;
    }

    public void setProductRamSize(String productRamSize) {
        this.productRamSize = productRamSize;
    }

    public String getProductOS() {
        return productOS;
    }

    public void setProductOS(String productOS) {
        this.productOS = productOS;
    }

    public ProductModel() {
    }

    @Override
    public String toString() {
        return "ProductModel{" +
                "productID=" + productID +
                ", categoryID=" + categoryID +
                ", productName='" + productName + '\'' +
                ", productImgUrl='" + productImgUrl + '\'' +
                ", productDescription='" + productDescription + '\'' +
                ", productBrandName='" + productBrandName + '\'' +
                ", productColor='" + productColor + '\'' +
                ", productSize='" + productSize + '\'' +
                ", productRamSize='" + productRamSize + '\'' +
                ", productOS='" + productOS + '\'' +
                ", productPrice=" + productPrice +
                ", merchantID=" + merchantID +
                ", merchantName='" + merchantName + '\'' +
                '}';
    }
}
