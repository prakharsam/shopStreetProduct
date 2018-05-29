package com.example.productsData.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = ProductMerchantMapModel.COLLECTION_NAME)
public class ProductMerchantMapModel {

    public static final String COLLECTION_NAME="productmerchantdetails1";
    @Id
    private Long rowIndex;
    private Long productID;
    private Long merchantID;
    private int weightedFactor;
    private Double productPrice;
    private Long productStock;

    private Long productSold;

    public Long getProductSold() {
        return productSold;
    }

    public void setProductSold(Long productSold) {
        this.productSold = productSold;
    }

    public Long getProductID() {
        return productID;
    }

    public void setProductID(Long productID) {
        this.productID = productID;
    }

    public Long getMerchantID() {
        return merchantID;
    }

    public void setMerchantID(Long merchantID) {
        this.merchantID = merchantID;
    }

    public int getWeightedFactor() {
        return weightedFactor;
    }

    public void setWeightedFactor(int weightedFactor) {
        this.weightedFactor = weightedFactor;
    }

    public Double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Double productPrice) {
        this.productPrice = productPrice;
    }

    public Long getProductStock() {
        return productStock;
    }

    public void setProductStock(Long productStock) {
        this.productStock = productStock;
    }

    public ProductMerchantMapModel() {
    }

    public Long getRowIndex() {
        return rowIndex;
    }

    public void setRowIndex(Long rowIndex) {
        this.rowIndex = rowIndex;
    }
}
