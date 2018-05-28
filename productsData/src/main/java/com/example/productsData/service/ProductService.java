package com.example.productsData.service;

import com.example.productsData.client.SearchClientInterface;
import com.example.productsData.dto.*;
import com.example.productsData.model.CategoryModel;
import com.example.productsData.model.MerchantModel;
import com.example.productsData.model.ProductMerchantMapModel;
import com.example.productsData.model.ProductModel;
import com.example.productsData.repository.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.UUID;

@Service
public class ProductService implements ProductServiceInterface{

    @Autowired
    public ProductRepositoryInterface productRepositoryInterface;

    @Autowired
    public MerchantRepositoryInterface merchantRepositoryInterface;

    @Autowired
    public ProductMerchantMapRepositoryInterface productMerchantMapRepositoryInterface;

    @Autowired
    public ProductSearchRepoInterface productSearchRepoInterface;

    @Autowired
    public CategoryRepositoryInterface categoryRepositoryInterface;

    @Autowired
    public SearchClientInterface searchClientInterface;

    @Override
    public ProductDto addProduct(ProductDto productDto){
        System.out.println("Adding new product");
//        UUID uuid = UUID.randomUUID();
//        productDto.setProductID(uuid.getMostSignificantBits());
//
//        System.out.println("setting product id: "+productDto.getProductID());
        ProductModel productModel = new ProductModel();
        BeanUtils.copyProperties(productDto,productModel);
        productModel = productRepositoryInterface.save(productModel);
        BeanUtils.copyProperties(productModel,productDto);

        CategoryDto categoryDto = new CategoryDto();
        CategoryModel categoryModel =new CategoryModel();
        categoryDto.setCategoryID(productDto.getCategoryID());
        BeanUtils.copyProperties(categoryDto,categoryModel);
        categoryModel = categoryRepositoryInterface.findById(categoryModel.getCategoryID()).get();
        BeanUtils.copyProperties(categoryModel,categoryDto);

        ProductSearchDto productSearchDto =new ProductSearchDto();
        productSearchDto.setProductCategoryName(categoryDto.getCategoryName());
        productSearchDto.setProductId(productDto.getProductID());
        productSearchDto.setProductName(productDto.getProductName());
        productSearchDto.setProductImgUrl(productDto.getProductImgUrl());
        productSearchDto.setProductDescription(productDto.getProductDescription());
        productSearchDto.setProductBrandName(productDto.getProductBrandName());

//        boolean responseSearch = searchClientInterface.sendToSearch(productSearchDto);
//
//        if(!responseSearch){
//            throw new RuntimeException("Something wrong with search micro service...");
//        }
        System.out.println("Going good with search micro service..");
        System.out.println("product id in db and search: "+ productDto.getProductID() +"  "+productSearchDto.getProductId());
        return(productDto);
    }

    @Override
    public MerchantDto addMerchant(MerchantDto merchantDto){
        System.out.println("Adding new merchant");

        MerchantModel merchantModel = new MerchantModel();
        BeanUtils.copyProperties(merchantDto,merchantModel);
        merchantModel = merchantRepositoryInterface.save(merchantModel);
        BeanUtils.copyProperties(merchantModel,merchantDto);

        return (merchantDto);
    }

    @Override
    public ProductMerchantMapDto addProductMerchant(ProductMerchantMapDto productMerchantMapDto){
        System.out.println("Adding new product merchant detail");

        ProductMerchantMapModel productMerchantMapModel = new ProductMerchantMapModel();
        BeanUtils.copyProperties(productMerchantMapDto,productMerchantMapModel);
        productMerchantMapModel = productMerchantMapRepositoryInterface.save(productMerchantMapModel);
        BeanUtils.copyProperties(productMerchantMapModel,productMerchantMapDto);

        return (productMerchantMapDto);
    }

    @Override
    public CategoryDto addCategory(CategoryDto categoryDto){
        System.out.println("Adding category");

        CategoryModel categoryModel = new CategoryModel();
        BeanUtils.copyProperties(categoryDto,categoryModel);
        categoryModel = categoryRepositoryInterface.save(categoryModel);
        BeanUtils.copyProperties(categoryModel,categoryDto);

        return categoryDto;
    }
    @Override
    public ProductDto getProductById(Long productID){
        System.out.println("Getting product by id "+productID);

        ProductDto productDto = new ProductDto();
        productDto.setProductID(productID);
        ProductModel productModel = new ProductModel();
        BeanUtils.copyProperties(productDto,productModel);
        productModel = productRepositoryInterface.findById(productModel.getProductID()).get();
        BeanUtils.copyProperties(productModel,productDto);

        ProductDto productDto_new = updatePrice(productID); //Dto with only price,merchant name and merchant id
        productDto.setMerchantID(productDto_new.getMerchantID());
        productDto.setMerchantName(productDto_new.getMerchantName());
        productDto.setProductPrice(productDto_new.getProductPrice());

        return (productDto);
    }

    @Override
    public ArrayList<ProductDto> getProductsByCategoryID(Long categoryID){
        System.out.println("Getting products by category");

        ProductDto productDto = new ProductDto();
        productDto.setCategoryID(categoryID);
        ProductModel productModel = new ProductModel();
        BeanUtils.copyProperties(productDto,productModel);

        ArrayList<ProductModel> productModels = new ArrayList<>();
        productModels = productRepositoryInterface.findByCategoryID(productModel.getCategoryID());

        ArrayList<ProductDto> productDtos =new ArrayList<>();
        productModelToDto(productModels,productDtos);

        ArrayList<ProductDto> productDtos_list =new ArrayList<>();

        for(int i=0;i <productDtos.size();i++ ){
           ProductDto productDto_new = getProductById(productDtos.get(i).getProductID());
           // To update price,merchant name and id
           productDtos_list.add(productDto_new);
        }

        return (productDtos_list);
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

        for (int i=0;i < productMerchantMapDtos.size(); i++){
            productMerchantMapDtos.get(i).setMerchantName(getMerchantNameById(productMerchantMapDtos.get(i).getMerchantID()));
        }
        return(productMerchantMapDtos);
    }

    public String getMerchantNameById(Long merchantID){

        MerchantDto merchantDto = new MerchantDto();
        merchantDto.setMerchantID(merchantID);
        MerchantModel merchantModel = new MerchantModel();
        BeanUtils.copyProperties(merchantDto,merchantModel);
        merchantModel = merchantRepositoryInterface.findById(merchantModel.getMerchantID()).get();
        BeanUtils.copyProperties(merchantModel,merchantDto);

        return (merchantDto.getMerchantName());
    }

    public StockResponseDto updateStock(UpdateStockDto updateStockDto){
        System.out.println(" in check availability ");

        ProductMerchantMapModel productMerchantMapModel = new ProductMerchantMapModel();
        productMerchantMapModel.setProductID(updateStockDto.getProductID());
        productMerchantMapModel.setMerchantID(updateStockDto.getMerchantID());

        productMerchantMapModel = productMerchantMapRepositoryInterface.findByProductIDAndMerchantID(
                productMerchantMapModel.getProductID(),productMerchantMapModel.getMerchantID());

        StockResponseDto stockResponseDto =new StockResponseDto();

        if(!(( productMerchantMapModel.getProductStock() - updateStockDto.getQty() ) < 0 )){
            System.out.println("in stock : "+productMerchantMapModel.getProductStock()+
                    "in quantity : "+updateStockDto.getQty());
            //Decrement in stock
            productMerchantMapModel.setProductStock(productMerchantMapModel.getProductStock() - updateStockDto.getQty());

            productMerchantMapModel = productMerchantMapRepositoryInterface.save(productMerchantMapModel);

            System.out.println("after reducing in stock: "+productMerchantMapModel.getProductStock());
            stockResponseDto.setAvailable(true);
            stockResponseDto.setProductID(productMerchantMapModel.getProductID());
            System.out.println("id  and available "+stockResponseDto.getProductID() + "  "+stockResponseDto.getAvailable());
            return stockResponseDto ;
        }
        System.out.println("not available response");
        stockResponseDto.setAvailable(false);
        stockResponseDto.setProductID(productMerchantMapModel.getProductID());

        return stockResponseDto;
    }


    public StockResponseDto checkAvailability(UpdateStockDto updateStockDto){
        System.out.println(" in check availability ");

        ProductMerchantMapModel productMerchantMapModel = new ProductMerchantMapModel();
        productMerchantMapModel.setProductID(updateStockDto.getProductID());
        productMerchantMapModel.setMerchantID(updateStockDto.getMerchantID());

        productMerchantMapModel = productMerchantMapRepositoryInterface.findByProductIDAndMerchantID(
                productMerchantMapModel.getProductID(),productMerchantMapModel.getMerchantID());

        StockResponseDto stockResponseDto =new StockResponseDto();

        if(!(( productMerchantMapModel.getProductStock() - updateStockDto.getQty() ) < 0 )){
            System.out.println("in stock : "+productMerchantMapModel.getProductStock()+
                    "in quantity : "+updateStockDto.getQty());
            stockResponseDto.setAvailable(true);
            stockResponseDto.setProductID(productMerchantMapModel.getProductID());

            System.out.println("id  and available "+stockResponseDto.getProductID() + "  "+
                    stockResponseDto.getAvailable());
            return stockResponseDto ;
        }
        System.out.println("not available response");
        stockResponseDto.setAvailable(false);
        stockResponseDto.setProductID(productMerchantMapModel.getProductID());
        return stockResponseDto;
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
//                System.out.println("merchantIDIndex "+merchantIDIndex);
//                System.out.println("maxWeightFactor "+maxWeightFactor);
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
//            productDto.setMerchantID(productModels.get(i).getMerchantID());
//            productDto.setMerchantName(productModels.get(i).getMerchantName());
//            productDto.setProductPrice(productModels.get(i).getProductPrice());

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

    @Override
    public ProductCartDto getProductForCart(Long productID){

        ProductDto productDto = new ProductDto();
        productDto.setProductID(productID);
        ProductModel productModel = new ProductModel();
        //bean
        BeanUtils.copyProperties(productDto,productModel);
        productModel = productRepositoryInterface.findById(productModel.getProductID()).get();

        ProductCartDto productCartDto =new ProductCartDto();
        productCartDto.setProductID(productModel.getProductID());
        productCartDto.setProductName(productModel.getProductName());
        productCartDto.setImage(productModel.getProductImgUrl());
        System.out.println("get image url: "+productCartDto.getImage());
        return (productCartDto);
    }
}

//    public void computeWeightedFactor(int weightage1, int weightage2, int weightage3, int weightage4, int weightage5){
//        Long productQuantity = null;
//        Long productSold  = null;
//        int merchantRating = 0;
//        Double price = null;
//        int customerRating = 0;
//        int weightedFactor = 0;
//
//
//        weightedFactor = (int) (((weightage1 * productQuantity) + (weightage2 * productSold) +
// (weightage3 * merchantRating)
//        + (weightage4 * price) + (weightage5 * customerRating))/5 );
//
//        System.out.println("computation: "+weightedFactor);
//    }


//    Number of products the merchant offers to sell
//        - Number of products sold (number of orders created)
//                - Current stock of the product
//                - Merchant rating
//                - Price of the products by various merchants
//                - Customer reviews/rating given to the products of various merchants

