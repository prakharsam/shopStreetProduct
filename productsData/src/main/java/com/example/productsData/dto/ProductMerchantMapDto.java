package com.example.productsData.dto;

public class ProductMerchantMapDto {
    private Long rowIndex;
    private Long productID;//many to one
    private Long merchantID;

    private String merchantName;

    private int weightedFactor;
    private Double productPrice;
    private Long productStock;

    private Long productSold;
    private int merchantRating;

    public int getMerchantRating() {
        return merchantRating;
    }

    public void setMerchantRating(int merchantRating) {
        this.merchantRating = merchantRating;
    }

    public Long getProductSold() {
        return productSold;
    }

    public void setProductSold(Long productSold) {
        this.productSold = productSold;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public Long getRowIndex() {
        return rowIndex;
    }

    public void setRowIndex(Long rowIndex) {
        this.rowIndex = rowIndex;
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

    public ProductMerchantMapDto() {
    }

    @Override
    public String toString() {
        return "ProductMerchantMapDto{" +
                "rowIndex=" + rowIndex +
                ", productID=" + productID +
                ", merchantID=" + merchantID +
                ", weightedFactor=" + weightedFactor +
                ", productPrice=" + productPrice +
                ", productStock=" + productStock +
                '}';
    }
}
