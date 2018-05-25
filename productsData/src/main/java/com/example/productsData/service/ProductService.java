package com.example.productsData.service;

import com.example.productsData.dto.MerchantDto;
import com.example.productsData.dto.ProductDto;
import com.example.productsData.dto.ProductMerchantMapDto;
import com.example.productsData.model.MerchantModel;
import com.example.productsData.model.ProductMerchantMapModel;
import com.example.productsData.model.ProductModel;
import com.example.productsData.repository.MerchantRepositoryInterface;
import com.example.productsData.repository.ProductMerchantMapRepositoryInterface;
import com.example.productsData.repository.ProductRepositoryInterface;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;

@Service
public class ProductService implements ProductServiceInterface{

    @Autowired
    public ProductRepositoryInterface productRepositoryInterface;

    @Autowired
    public MerchantRepositoryInterface merchantRepositoryInterface;

    @Autowired
    public ProductMerchantMapRepositoryInterface productMerchantMapRepositoryInterface;

    @Override
    public ProductDto addProduct(ProductDto productDto){
        System.out.println("Adding new product");
        ProductModel productModel = new ProductModel();
        BeanUtils.copyProperties(productDto,productModel);
        ProductModel productModelDetails = productRepositoryInterface.save(productModel);
        ProductDto productDtoDetails = new ProductDto();
        BeanUtils.copyProperties(productModelDetails,productDtoDetails);
        return(productDtoDetails);
    }

    @Override
    public MerchantDto addMerchant(MerchantDto merchantDto){
        System.out.println("Adding new merchant");

        MerchantModel merchantModel = new MerchantModel();
        BeanUtils.copyProperties(merchantDto,merchantModel);
        MerchantModel merchantModelDetails = merchantRepositoryInterface.save(merchantModel);
        MerchantDto merchantDtoDetails = new MerchantDto();
        BeanUtils.copyProperties(merchantModelDetails,merchantDtoDetails);

        return (merchantDtoDetails);
    }

    @Override
    public ProductMerchantMapDto addProductMerchant(ProductMerchantMapDto productMerchantMapDto){
        System.out.println("Adding new product merchant detail");

        ProductMerchantMapModel productMerchantMapModel = new ProductMerchantMapModel();
        BeanUtils.copyProperties(productMerchantMapDto,productMerchantMapModel);
        ProductMerchantMapModel productMerchantMapModelDetails =
                productMerchantMapRepositoryInterface.save(productMerchantMapModel);
        ProductMerchantMapDto productMerchantMapDtoDetails = new ProductMerchantMapDto();
        BeanUtils.copyProperties(productMerchantMapModelDetails,productMerchantMapDtoDetails);

        return (productMerchantMapDtoDetails);
    }

    @Override
    public ProductDto getProductById(Long productID){
        System.out.println("Getting product by id");

        ProductDto productDto = new ProductDto();
        productDto.setProductID(productID);
        ProductModel productModel = new ProductModel();
        BeanUtils.copyProperties(productDto,productModel);
        ProductModel productModelDetails = productRepositoryInterface.findById(productModel.getProductID()).get();
        ProductDto productDtoDetails = new ProductDto();
        BeanUtils.copyProperties(productModelDetails,productDtoDetails);

        return (productDtoDetails);
    }

    @Override
    public ArrayList<ProductDto> getProductsByCategoryID(Long categoryID){
        System.out.println("Getting products by category");

        ProductDto productDto = new ProductDto();
        productDto.setCategoryID(categoryID);
        ProductModel productModel = new ProductModel();
        BeanUtils.copyProperties(productDto,productModel);
        System.out.println("Product id in model of cat "+productModel.getCategoryID());

        ArrayList<ProductModel> productModels = new ArrayList<>();
        productModels = productRepositoryInterface.findByCategoryID(productModel.getCategoryID());
        System.out.println("countofproducts");
        ArrayList<ProductDto> productDtos = new ArrayList(productModels);

        return (productDtos);
    }

    public ArrayList<ProductMerchantMapDto> getMerchantsByProductID(Long productID){
        System.out.println("Getting merchants by product id "+ productID);

        ProductMerchantMapDto productMerchantMapDto = new ProductMerchantMapDto();
        productMerchantMapDto.setProductID(productID);
        ProductMerchantMapModel productMerchantMapModel = new ProductMerchantMapModel();
        BeanUtils.copyProperties(productMerchantMapDto,productMerchantMapModel);

        ArrayList<ProductMerchantMapModel> productMerchantMapModels =new ArrayList<>();
        productMerchantMapModels =
                productMerchantMapRepositoryInterface.findByProductID(productMerchantMapModel.getProductID());
        ArrayList<ProductMerchantMapDto> productMerchantMapDtos =new ArrayList(productMerchantMapModels);

        return(productMerchantMapDtos);
    }

}
