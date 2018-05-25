package com.example.productsData.controller;

import com.example.productsData.dto.MerchantDto;
import com.example.productsData.dto.ProductDto;
import com.example.productsData.dto.ProductMerchantMapDto;
import com.example.productsData.service.ProductServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping("product")
public class ProductController {
    @Autowired
    private ProductServiceInterface productServiceInterface;

    @RequestMapping(value = "/add-product", method = RequestMethod.POST)
    public ProductDto addProduct(@RequestBody ProductDto productDto){
        System.out.println("Entered into add product");

        return productServiceInterface.addProduct(productDto);
    }

    @RequestMapping(value = "/add-merchant", method = RequestMethod.POST)
    public MerchantDto addProduct(@RequestBody MerchantDto merchantDto){
        System.out.println("Entered into add merchant");

        return  productServiceInterface.addMerchant(merchantDto);
    }

    @RequestMapping(value = "/add-product-merchant", method = RequestMethod.POST)
    public ProductMerchantMapDto addProductMerchant(@RequestBody ProductMerchantMapDto productMerchantMapDto){
        System.out.println("Entered into add product merchant");

        return  productServiceInterface.addProductMerchant(productMerchantMapDto);
    }

    @RequestMapping(value = "/get-product-by-id",method = RequestMethod.GET)
    public ProductDto getProductById(Long productID){
        System.out.println("Entered into get product by id");

        return productServiceInterface.getProductById(productID);
     }

    @RequestMapping(value = "/get-products-by-cid",method = RequestMethod.GET)
    public ArrayList<ProductDto> getProductsByCategoryID(Long categoryID){
        System.out.println("Entered into get product by category id");

        return productServiceInterface.getProductsByCategoryID(categoryID);
    }

    @RequestMapping(value = "/get-merchants-by-pid",method = RequestMethod.GET)
    public ArrayList<ProductMerchantMapDto> getMerchantsByProductID(Long productID){
        System.out.println("Entered into get merchants by product id "+productID);

        return productServiceInterface.getMerchantsByProductID(productID);
    }


}
