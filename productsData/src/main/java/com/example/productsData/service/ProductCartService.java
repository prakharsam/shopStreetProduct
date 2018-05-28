package com.example.productsData.service;

import com.example.productsData.dto.StockResponseDto;
import com.example.productsData.dto.UpdateStockDto;
import com.example.productsData.model.ProductMerchantMapModel;

import java.util.List;

public class ProductCartService {

    private List<StockResponseDto> stockResponseDtoList;

    public List<StockResponseDto> getStockResponseDtoList() {
        return stockResponseDtoList;
    }

    public void setStockResponseDtoList(List<StockResponseDto> stockResponseDtoList) {
        this.stockResponseDtoList = stockResponseDtoList;
    }
}
