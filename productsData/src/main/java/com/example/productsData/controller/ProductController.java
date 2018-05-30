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
        return productServiceInterface.addProduct(productDto);
    }

    @RequestMapping(value = "/add-merchant", method = RequestMethod.POST)
    public MerchantDto addProduct(@RequestBody MerchantDto merchantDto){
        return  productServiceInterface.addMerchant(merchantDto);
    }

    @RequestMapping(value = "/add-product-merchant", method = RequestMethod.POST)
    public ProductMerchantMapDto addProductMerchant(@RequestBody ProductMerchantMapDto productMerchantMapDto){
        return  productServiceInterface.addProductMerchant(productMerchantMapDto);
    }

    @RequestMapping(value = "/add-category", method = RequestMethod.POST)
    ResponseEntity<CategoryDto> addCategory(@RequestBody CategoryDto categoryDto){
        categoryDto = productServiceInterface.addCategory(categoryDto);
        return new ResponseEntity<>(categoryDto, HttpStatus.OK);
    }

    @RequestMapping(value = "/delete-product", method = RequestMethod.GET)
    public boolean deleteEmployee(@RequestParam Long productID){
        return productServiceInterface.deleteProduct(productID);
    }

    @RequestMapping(value = "/get-product-by-id",method = RequestMethod.GET)
    ResponseEntity<ProductDto> getProductById(Long productID){
//        System.out.println("Entered into get product by id");
        ProductDto productDto = productServiceInterface.getProductById(productID);

        if(productDto.getProductID() != null){
            return new ResponseEntity<>(productDto, HttpStatus.OK);
        }
        return new ResponseEntity<>(productDto, HttpStatus.NOT_FOUND);

     }

    @RequestMapping(value = "/get-products-by-cid",method = RequestMethod.GET)
    ResponseEntity<ArrayList<ProductDto>> getProductsByCategoryID(Long categoryID){
//        System.out.println("Entered into get product by category id");
        ArrayList<ProductDto> productDtos = productServiceInterface.getProductsByCategoryID(categoryID);

        if(productDtos.size() != 0){
            return new ResponseEntity<>(productDtos, HttpStatus.OK);
        }
        return new ResponseEntity<>(productDtos, HttpStatus.NOT_FOUND);

    }

    @RequestMapping(value = "/get-merchants-by-pid",method = RequestMethod.GET)
    ResponseEntity<ArrayList<ProductMerchantMapDto>> getMerchantsByProductID(Long productID){
//        System.out.println("Entered into get merchants by product id "+productID);
        ArrayList<ProductMerchantMapDto> productMerchantMapDtos = productServiceInterface.getMerchantsByProductID(productID);

        if(productMerchantMapDtos.size() != 0){
            return new ResponseEntity<>(productMerchantMapDtos, HttpStatus.OK);
        }
        return new ResponseEntity<>(productMerchantMapDtos, HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/update-stock", method = RequestMethod.POST)
    ResponseEntity<ProductCartService> updateStock(@RequestBody ProductCartRequestService productCartRequestService){
//        System.out.println("Entered into update stock");
        ProductCartService productCartService = new ProductCartService();
        StockResponseDto stockResponseDto = new StockResponseDto();
        List<UpdateStockDto> requestList = productCartRequestService.getUpdateStockDtoList();
        List<StockResponseDto> stockResponseDtos =new ArrayList<>();
        int nullResponseCount =0 ;
        for(int index=0; index < requestList.size(); index++){
            stockResponseDto = productServiceInterface.updateStock(requestList.get(index));
            if(stockResponseDto.getAvailable() == null)
            {
                nullResponseCount ++;
            }
            stockResponseDtos.add(stockResponseDto);
        }
        if(nullResponseCount > 0){
            productCartService.setSuccess(false);
            productCartService.setMessage("No such product exists");
            productCartService.setStockResponseDtoList(stockResponseDtos);
            return new ResponseEntity<>(productCartService, HttpStatus.OK);
        }
        productCartService.setSuccess(true);
        productCartService.setMessage("Success");
        productCartService.setStockResponseDtoList(stockResponseDtos);
        return new ResponseEntity<>(productCartService, HttpStatus.OK);
    }

    @RequestMapping(value = "/check-availability", method = RequestMethod.POST)
    ResponseEntity<CheckAvailabilityDto> checkAvailability(@RequestBody UpdateStockDto updateStockDto){
//        System.out.println("Entered into check availability");
        CheckAvailabilityDto checkAvailabilityDto = productServiceInterface.checkAvailability(updateStockDto);
        return new ResponseEntity<>(checkAvailabilityDto, HttpStatus.OK);
    }

    @RequestMapping(value = "/get-product-for-cart/{productID}", method = RequestMethod.GET)
    ResponseEntity<ProductCartDto> getProductForCart(@PathVariable Long productID){
//        System.out.println("Entered into get product for cart");

        ProductCartDto productCartDto = productServiceInterface.getProductForCart(productID);
        return new ResponseEntity<>(productCartDto, HttpStatus.OK);
    }

    @RequestMapping(value = "/add-into-search", method = RequestMethod.GET)
    ResponseEntity<ProductSearchDto> addProductForSearch(@RequestParam Long productID){
//        System.out.println("Entered into add into search");
        ProductSearchDto productSearchDto = productServiceInterface.addProductForSearch(productID);

        return new ResponseEntity<>(productSearchDto, HttpStatus.OK);
    }

    @RequestMapping(value = "/compute-wf", method = RequestMethod.GET)
    void computeWeightedFactor(@RequestParam Long productID, @RequestParam Long merchantID){
        productServiceInterface.computeWeightedFactor(productID,merchantID);
    }


}
