package com.example.productsData.client;

import com.example.productsData.dto.ProductSearchDto;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SearchClient implements SearchClientInterface {

    //@Value("search.client.addProduct.url")
    private String uri ="http://10.177.1.106:8090/add" ;
    RestTemplate restTemplate = new RestTemplate();

    public boolean sendToSearch(ProductSearchDto productSearchDto){
        System.out.println("In rest of search service");
        System.out.println("id: "+productSearchDto.getProductID());
        HttpEntity<ProductSearchDto> request = new HttpEntity<>(productSearchDto);
        boolean result = restTemplate.postForObject( uri, request, Boolean.class);

        System.out.println("result "+result);

        return  result;
    }
}
