package com.example.productsData.client;

import com.example.productsData.dto.ProductSearchDto;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SearchClient implements SearchClientInterface {

    final String uri = "http://10.177.2.19:8080/add";

    public boolean sendToSearch(ProductSearchDto productSearchDto){
        System.out.println("In rest of search service");
        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<ProductSearchDto> request = new HttpEntity<>(productSearchDto);
        boolean result = restTemplate.postForObject( uri, request, Boolean.class);

        System.out.println("result "+result);

        return  true;
    }
}
