package com.example.productsData.controller;

import com.example.productsData.dto.*;
import com.example.productsData.service.ProductCartRequestService;
import com.example.productsData.service.ProductCartService;
import com.example.productsData.service.ProductServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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
    ResponseEntity<ProductCartService> updateStock(@RequestBody ProductCartRequestService productCartRequestService){
        System.out.println("Entered into update stock");
        StockResponseDto isAvailable = new StockResponseDto();
        List<UpdateStockDto> requestList = productCartRequestService.getUpdateStockDtoList();

        ProductCartService productCartService = new ProductCartService();

        List<StockResponseDto> stockResponseDtos =new ArrayList<>();

        for(int index=0; index < requestList.size(); index++){
            isAvailable = productServiceInterface.updateStock(requestList.get(index));
            stockResponseDtos.add(isAvailable);
        }

        productCartService.setStockResponseDtoList(stockResponseDtos);
        System.out.println("done with setting");
        return new ResponseEntity<>(productCartService, HttpStatus.OK);
    }

    @RequestMapping(value = "/check-availability", method = RequestMethod.POST)
    ResponseEntity<StockResponseDto> checkAvailability(@RequestBody UpdateStockDto updateStockDto){
        System.out.println("Entered into check availability");
        StockResponseDto isAvailable = new StockResponseDto();

        isAvailable = productServiceInterface.updateStock(updateStockDto);
        System.out.println("after check: "+isAvailable.getAvailable());

        return new ResponseEntity<>(isAvailable, HttpStatus.OK);
    }

    @RequestMapping(value = "/get-product-for-cart/{productID}", method = RequestMethod.GET)
    ResponseEntity<ProductCartDto> getProductForCart(@PathVariable Long productID){
        System.out.println("Entered into get product for cart");

        ProductCartDto productCartDto = productServiceInterface.getProductForCart(productID);

        return new ResponseEntity<>(productCartDto, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/add-category", method = RequestMethod.POST)
    ResponseEntity<CategoryDto> addCategory(@RequestBody CategoryDto categoryDto){
        System.out.println("Entered into add category");
        categoryDto = productServiceInterface.addCategory(categoryDto);

        return new ResponseEntity<>(categoryDto, HttpStatus.OK);
    }
}
