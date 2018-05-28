package com.example.productsData.client;

import com.example.productsData.dto.ProductSearchDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SearchClient implements SearchClientInterface {

    @Value("search.client.addProduct.url")
    private String uri;

    public boolean sendToSearch(ProductSearchDto productSearchDto){
        System.out.println("In rest of search service");
        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<ProductSearchDto> request = new HttpEntity<>(productSearchDto);
        boolean result = restTemplate.postForObject( uri, request, Boolean.class);

        System.out.println("result "+result);

        return  true;
    }
}
