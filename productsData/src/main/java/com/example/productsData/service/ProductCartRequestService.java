package com.example.productsData.service;

import com.example.productsData.dto.StockResponseDto;
import com.example.productsData.dto.UpdateStockDto;

import java.util.List;

public class ProductCartRequestService {

    private List<UpdateStockDto> updateStockDtoList;

    public List<UpdateStockDto> getUpdateStockDtoList() {
        return updateStockDtoList;
    }

    public void setUpdateStockDtoList(List<UpdateStockDto> updateStockDtoList) {
        this.updateStockDtoList = updateStockDtoList;
    }
}
