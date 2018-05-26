package com.example.productsData.dto;

public class StockResponseDto {
    private Long productID;
    private Boolean available;

    public Long getProductID() {
        return productID;
    }

    public void setProductID(Long productID) {
        this.productID = productID;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public StockResponseDto() {
    }
}
