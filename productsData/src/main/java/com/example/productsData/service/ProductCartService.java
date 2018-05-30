package com.example.productsData.service;

import com.example.productsData.dto.StockResponseDto;
import com.example.productsData.dto.UpdateStockDto;
import com.example.productsData.model.ProductMerchantMapModel;

import java.util.List;

public class ProductCartService {

    private List<StockResponseDto> stockResponseDtoList;

    private boolean success;
    private String message;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<StockResponseDto> getStockResponseDtoList() {
        return stockResponseDtoList;
    }

    public void setStockResponseDtoList(List<StockResponseDto> stockResponseDtoList) {
        this.stockResponseDtoList = stockResponseDtoList;
    }
}
