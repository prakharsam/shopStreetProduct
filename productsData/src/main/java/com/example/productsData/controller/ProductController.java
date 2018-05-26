package com.example.productsData.controller;

import com.example.productsData.dto.*;
import com.example.productsData.service.ProductServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    ResponseEntity<ProductDto> getProductById(Long productID){
        System.out.println("Entered into get product by id");
        ProductDto productDto = productServiceInterface.getProductById(productID);
        return new ResponseEntity<>(productDto, HttpStatus.OK);
     }

    @RequestMapping(value = "/get-products-by-cid",method = RequestMethod.GET)
    ResponseEntity<ArrayList<ProductDto>> getProductsByCategoryID(Long categoryID){
        System.out.println("Entered into get product by category id");
        ArrayList<ProductDto> productDtos = productServiceInterface.getProductsByCategoryID(categoryID);

        return new ResponseEntity<>(productDtos, HttpStatus.OK);
    }

    @RequestMapping(value = "/get-merchants-by-pid",method = RequestMethod.GET)
    ResponseEntity<ArrayList<ProductMerchantMapDto>> getMerchantsByProductID(Long productID){
        System.out.println("Entered into get merchants by product id "+productID);
        ArrayList<ProductMerchantMapDto> productMerchantMapDtos = productServiceInterface.getMerchantsByProductID(productID);
        System.out.println("size of list in merchants "+productMerchantMapDtos.size());
        return new ResponseEntity<>(productMerchantMapDtos, HttpStatus.OK);
    }

    @RequestMapping(value = "/update-stock", method = RequestMethod.POST)
    ResponseEntity<ArrayList<StockResponseDto>> updateStock(@RequestBody ArrayList<UpdateStockDto> updateStockDto){
        System.out.println("Entered into update stock");
        StockResponseDto isAvailable = new StockResponseDto();
        ArrayList<StockResponseDto> isAvailableList =new ArrayList<>();

        for(int index=0; index < updateStockDto.size(); index++){
            isAvailable = productServiceInterface.updateStock(updateStockDto.get(index));
            isAvailableList.add(isAvailable);
        }

        return new ResponseEntity<>(isAvailableList, HttpStatus.OK);
    }

    @RequestMapping(value = "/check-availability", method = RequestMethod.POST)
    ResponseEntity<ArrayList<StockResponseDto>> checkAvailability(@RequestBody ArrayList<UpdateStockDto> updateStockDto){
        System.out.println("Entered into update stock");
        StockResponseDto isAvailable = new StockResponseDto();
        ArrayList<StockResponseDto> isAvailableList =new ArrayList<>();

        for(int index=0; index < updateStockDto.size(); index++){
            isAvailable = productServiceInterface.updateStock(updateStockDto.get(index));
            isAvailableList.add(isAvailable);
        }

        return new ResponseEntity<>(isAvailableList, HttpStatus.OK);
    }

    @RequestMapping(value = "/add-category", method = RequestMethod.POST)
    ResponseEntity<CategoryDto> addCategory(@RequestBody CategoryDto categoryDto){
        System.out.println("Entered into add category");
        categoryDto = productServiceInterface.addCategory(categoryDto);

        return new ResponseEntity<>(categoryDto, HttpStatus.OK);
    }
}
