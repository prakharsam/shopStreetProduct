package com.example.productsData.service;

import com.example.productsData.dto.MerchantDto;
import com.example.productsData.dto.ProductDto;
import com.example.productsData.dto.ProductMerchantMapDto;
import com.example.productsData.dto.UpdateStockDto;
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

        ProductDto productDto_new = updatePrice(productID);
        productDtoDetails.setMerchantID(productDto_new.getMerchantID());
        productDtoDetails.setMerchantName(productDto_new.getMerchantName());
        productDtoDetails.setProductPrice(productDto_new.getProductPrice());

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
        ArrayList<ProductDto> productDtos =new ArrayList<>();
        productModelToDto(productModels,productDtos);

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
        ArrayList<ProductMerchantMapDto> productMerchantMapDtos =new ArrayList<>();
        productMerchantModelToDto(productMerchantMapModels,productMerchantMapDtos);

        return(productMerchantMapDtos);
    }

    public Boolean updateStock(UpdateStockDto updateStockDto){
        System.out.println("update stock by pid");

        ProductMerchantMapModel productMerchantMapModel = new ProductMerchantMapModel();
        productMerchantMapModel.setProductID(updateStockDto.getProductID());
        productMerchantMapModel.setMerchantID(updateStockDto.getMerchantID());

        productMerchantMapModel = productMerchantMapRepositoryInterface.findByProductIDAndMerchantID(
                productMerchantMapModel.getProductID(),productMerchantMapModel.getMerchantID());

        if(!(( productMerchantMapModel.getProductStock() - updateStockDto.getQty() ) < 0 )){
            System.out.println("in stock : "+productMerchantMapModel.getProductStock()+
                    "in quantity : "+updateStockDto.getQty());
            //decrement stock in mapping table
            return true;
        }
        return false;
    }

    public ProductDto updatePrice(Long productID){
        ArrayList<ProductMerchantMapDto> productMerchantMapDtos = new ArrayList<>();
        productMerchantMapDtos = getMerchantsByProductID(productID);
        int maxWeightFactor =0 ;
        Long merchantIDIndex = null;
        Double productPrice = null;
        Double merchantName;
        MerchantModel merchantModel =new MerchantModel();

        for (int loopIndex=0; loopIndex < productMerchantMapDtos.size();loopIndex++){

            if(productMerchantMapDtos.get(loopIndex).getWeightedFactor() > maxWeightFactor){
                maxWeightFactor = productMerchantMapDtos.get(loopIndex).getWeightedFactor();
                merchantIDIndex = productMerchantMapDtos.get(loopIndex).getMerchantID();
                productPrice =productMerchantMapDtos.get(loopIndex).getProductPrice();
                System.out.println("merchantIDIndex "+merchantIDIndex);
                System.out.println("maxWeightFactor "+maxWeightFactor);
            }
        }

        merchantModel.setMerchantID(merchantIDIndex);

        merchantModel = merchantRepositoryInterface.findById(merchantModel.getMerchantID()).get();
        MerchantDto merchantDto = new MerchantDto();
        BeanUtils.copyProperties(merchantModel,merchantDto);
        ProductDto productDto = new ProductDto();

        productDto.setProductPrice(productPrice);
        productDto.setMerchantName(merchantDto.getMerchantName());
        productDto.setMerchantID(merchantDto.getMerchantID());
        return (productDto);
    }



    public void productModelToDto(ArrayList<ProductModel> productModels, ArrayList<ProductDto> productDtos){
        for(int i=0; i < productModels.size(); i++){
            ProductDto productDto =new ProductDto();

            productDto.setProductID(productModels.get(i).getProductID());
            productDto.setCategoryID(productModels.get(i).getCategoryID());
            productDto.setProductDescription(productModels.get(i).getProductDescription());
            productDto.setProductName(productModels.get(i).getProductName());
            productDto.setProductBrandName(productModels.get(i).getProductBrandName());
            productDto.setProductColor(productModels.get(i).getProductColor());
            productDto.setProductImgUrl(productModels.get(i).getProductImgUrl());
            productDto.setProductOS(productModels.get(i).getProductOS());
            productDto.setProductRamSize(productModels.get(i).getProductRamSize());
            productDto.setProductSize(productModels.get(i).getProductSize());
            productDto.setMerchantID(productModels.get(i).getMerchantID());
            productDto.setMerchantName(productModels.get(i).getMerchantName());
            productDto.setProductPrice(productModels.get(i).getProductPrice());

            productDtos.add(productDto);

        }
    }

    public void productMerchantModelToDto(ArrayList<ProductMerchantMapModel> productMerchantModels,
                                          ArrayList<ProductMerchantMapDto> productMerchantMapDtos){
        for(int i=0; i < productMerchantModels.size(); i++){
            ProductMerchantMapDto productMerchantDto =new ProductMerchantMapDto();
            productMerchantDto.setProductID(productMerchantModels.get(i).getProductID());
            productMerchantDto.setMerchantID(productMerchantModels.get(i).getMerchantID());
            productMerchantDto.setProductPrice(productMerchantModels.get(i).getProductPrice());
            productMerchantDto.setProductStock(productMerchantModels.get(i).getProductStock());
            productMerchantDto.setWeightedFactor(productMerchantModels.get(i).getWeightedFactor());
            productMerchantDto.setRowIndex(productMerchantModels.get(i).getRowIndex());

            productMerchantMapDtos.add(productMerchantDto);

        }
    }
}

