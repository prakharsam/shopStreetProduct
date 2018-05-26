package com.example.productsData.client;

import com.example.productsData.dto.ProductSearchDto;

public interface SearchClientInterface {

    public boolean sendToSearch(ProductSearchDto productSearchDto);
}
