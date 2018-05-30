package com.example.productsData.service;

import com.example.productsData.client.SearchClientInterface;
import com.example.productsData.dto.*;
import com.example.productsData.model.CategoryModel;
import com.example.productsData.model.MerchantModel;
import com.example.productsData.model.ProductMerchantMapModel;
import com.example.productsData.model.ProductModel;
import com.example.productsData.repository.*;
import com.sun.tools.javac.comp.Check;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductServiceInterface {

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
    public ProductDto addProduct(ProductDto productDto) {
//        System.out.println("Adding new product");

        ProductModel productModel = new ProductModel();
        BeanUtils.copyProperties(productDto, productModel);
        productModel = productRepositoryInterface.save(productModel);
        BeanUtils.copyProperties(productModel, productDto);

        return (productDto);
    }

    @Override
    public MerchantDto addMerchant(MerchantDto merchantDto) {
//        System.out.println("Adding new merchant");

        MerchantModel merchantModel = new MerchantModel();
        BeanUtils.copyProperties(merchantDto, merchantModel);
        merchantModel = merchantRepositoryInterface.save(merchantModel);
        BeanUtils.copyProperties(merchantModel, merchantDto);

        return (merchantDto);
    }

    @Override
    public ProductMerchantMapDto addProductMerchant(ProductMerchantMapDto productMerchantMapDto) {
//        System.out.println("Adding new product merchant detail");

        ProductMerchantMapModel productMerchantMapModel = new ProductMerchantMapModel();
        BeanUtils.copyProperties(productMerchantMapDto, productMerchantMapModel);
        productMerchantMapModel = productMerchantMapRepositoryInterface.save(productMerchantMapModel);
        BeanUtils.copyProperties(productMerchantMapModel, productMerchantMapDto);

        //ProductSearchDto addProductForSearch(Long productID)

        return (productMerchantMapDto);
    }

    @Override
    public CategoryDto addCategory(CategoryDto categoryDto) {
//        System.out.println("Adding category");

        CategoryModel categoryModel = new CategoryModel();
        BeanUtils.copyProperties(categoryDto, categoryModel);
        categoryModel = categoryRepositoryInterface.save(categoryModel);
        BeanUtils.copyProperties(categoryModel, categoryDto);

        return categoryDto;
    }

    @Override
    public boolean deleteProduct(Long productID) {
        ProductDto productDto = new ProductDto();
        productDto.setProductID(productID);

        ProductModel productModel = new ProductModel();
        BeanUtils.copyProperties(productDto, productModel);
        if (!productRepositoryInterface.existsById(productModel.getProductID())) {
            throw new RuntimeException("Product with queried ID doesn't exist to delete");
        }
        productRepositoryInterface.deleteById(productModel.getProductID());
        return (true);
    }

    @Override
    public ProductDto getProductById(Long productID) {
//        System.out.println("Getting product by id " + productID);
        ProductDto productDto = new ProductDto();

        if (!productRepositoryInterface.existsById(productID)) {
            return productDto;
        }
        productDto.setProductID(productID);
        ProductModel productModel = new ProductModel();
        BeanUtils.copyProperties(productDto, productModel);
        productModel = productRepositoryInterface.findById(productModel.getProductID()).get();
        BeanUtils.copyProperties(productModel, productDto);

        ProductDto productDto_new = updatePrice(productID); //Dto with only price,merchant name and merchant id
        productDto.setMerchantID(productDto_new.getMerchantID());
        productDto.setMerchantName(productDto_new.getMerchantName());
        productDto.setProductPrice(productDto_new.getProductPrice());
        return (productDto);

    }

    @Override
    public ArrayList<ProductDto> getProductsByCategoryID(Long categoryID) {
//        System.out.println("Getting products by category");
        ArrayList<ProductDto> productDtosList = new ArrayList<>();

        if (!categoryRepositoryInterface.existsById(categoryID)) {
            return productDtosList;
        }
        ArrayList<ProductDto> productDtos = new ArrayList<>();
        ArrayList<ProductModel> productModels = productRepositoryInterface.findByCategoryID(categoryID);
        productModelToDto(productModels, productDtos);

        List<Long> productIds = new ArrayList<>();
        for(ProductDto dto : productDtos){
            productIds.add(dto.getProductID());
        }
        Iterable<ProductModel> productModelsToUpdate = productRepositoryInterface.findAllById(productIds);
        ProductDto productDto = new ProductDto();
        ProductDto productDtoAfterUpdate = new ProductDto();
        for (ProductModel productModel: productModelsToUpdate){
            BeanUtils.copyProperties(productModel, productDtoAfterUpdate);
            productDto  = updatePrice(productModel.getProductID());
            productDtoAfterUpdate.setMerchantName(productDto.getMerchantName());
            productDtoAfterUpdate.setMerchantID(productDto.getMerchantID());
            productDtoAfterUpdate.setProductPrice(productDto.getProductPrice());
            //System.out.println(productDtoAfterUpdate);
            productDtosList.add(productDtoAfterUpdate);
        }
        return (productDtosList);
    }

    @Override
    public ArrayList<ProductMerchantMapDto> getMerchantsByProductID(Long productID) {
//        System.out.println("Getting merchants by product id " + productID);
        ArrayList<ProductMerchantMapDto> productMerchantMapDtos = new ArrayList<>();

        if (!productRepositoryInterface.existsById(productID)) {
            return productMerchantMapDtos;
        }
        ProductMerchantMapDto productMerchantMapDto = new ProductMerchantMapDto();
        productMerchantMapDto.setProductID(productID);
        ProductMerchantMapModel productMerchantMapModel = new ProductMerchantMapModel();
        BeanUtils.copyProperties(productMerchantMapDto, productMerchantMapModel);

        ArrayList<ProductMerchantMapModel> productMerchantMapModels =
                productMerchantMapRepositoryInterface.findByProductID(productMerchantMapModel.getProductID());
        productMerchantModelToDto(productMerchantMapModels, productMerchantMapDtos);
        MerchantModel merchantModel = new MerchantModel();

        for (int i = 0; i < productMerchantMapDtos.size(); i++) {

            merchantModel = merchantRepositoryInterface.findById(productMerchantMapDtos.get(i).getMerchantID()).get();
            productMerchantMapDtos.get(i).setMerchantName(merchantModel.getMerchantName());
            productMerchantMapDtos.get(i).setMerchantRating(merchantModel.getMerchantRating());
        }
        return (productMerchantMapDtos);
    }

    @Override
    public String getMerchantNameById(Long merchantID) {
        String merchantName = null;

        if (!merchantRepositoryInterface.existsById(merchantID)) {
            return merchantName;
        }
        MerchantDto merchantDto = new MerchantDto();
        merchantDto.setMerchantID(merchantID);
        MerchantModel merchantModel = new MerchantModel();
        BeanUtils.copyProperties(merchantDto, merchantModel);
        merchantModel = merchantRepositoryInterface.findById(merchantModel.getMerchantID()).get();
        BeanUtils.copyProperties(merchantModel, merchantDto);

        return (merchantDto.getMerchantName());
    }

    @Override
    public StockResponseDto updateStock(UpdateStockDto updateStockDto) {
//        System.out.println(" In update stock ");
        StockResponseDto stockResponseDto = new StockResponseDto();

        if (!productMerchantMapRepositoryInterface.existsByProductIDAndMerchantID(
                updateStockDto.getProductID(), updateStockDto.getMerchantID())) {
            return stockResponseDto;
        }
        ProductMerchantMapModel productMerchantMapModel = productMerchantMapRepositoryInterface.findByProductIDAndMerchantID(
                updateStockDto.getProductID(), updateStockDto.getMerchantID());

        if (!((productMerchantMapModel.getProductStock() - updateStockDto.getQty()) < 0)) {
//            System.out.println("Product stock : " + productMerchantMapModel.getProductStock() +
//                    "Required quantity : " + updateStockDto.getQty());
            //Decrement in stock
            //productMerchantMapModel.setProductStock(productMerchantMapModel.getProductStock() - updateStockDto.getQty());
            if (productMerchantMapModel.getProductSold() == null)
                productMerchantMapModel.setProductSold(0L);

            productMerchantMapModel.setProductSold(productMerchantMapModel.getProductSold() + updateStockDto.getQty());
            productMerchantMapModel = productMerchantMapRepositoryInterface.save(productMerchantMapModel);

//            System.out.println("No reduction in stock " + productMerchantMapModel.getProductStock());
//            System.out.println("after adding in sold: " + productMerchantMapModel.getProductSold());

            computeWeightedFactor(productMerchantMapModel.getProductID(), productMerchantMapModel.getMerchantID());
//            System.out.println("Going to compute wf...");

            stockResponseDto.setAvailable(true);
            stockResponseDto.setProductID(productMerchantMapModel.getProductID());
            return stockResponseDto;
        }
        stockResponseDto.setAvailable(false);
        stockResponseDto.setProductID(updateStockDto.getProductID());

        return stockResponseDto;
    }

    @Override
    public CheckAvailabilityDto checkAvailability(UpdateStockDto updateStockDto) {
//        System.out.println(" In check availability ");
        CheckAvailabilityDto checkAvailabilityDto = new CheckAvailabilityDto();

        if (!productMerchantMapRepositoryInterface.existsByProductIDAndMerchantID(
                updateStockDto.getProductID(), updateStockDto.getMerchantID())) {

            checkAvailabilityDto.setProductID(updateStockDto.getProductID());
            checkAvailabilityDto.setAvailable(false);
            checkAvailabilityDto.setSuccess(false);
            checkAvailabilityDto.setMessage("No such product exists");
            return checkAvailabilityDto;
        }
        ProductMerchantMapModel productMerchantMapModel = new ProductMerchantMapModel();
        productMerchantMapModel.setProductID(updateStockDto.getProductID());
        productMerchantMapModel.setMerchantID(updateStockDto.getMerchantID());

        productMerchantMapModel = productMerchantMapRepositoryInterface.findByProductIDAndMerchantID(
                productMerchantMapModel.getProductID(), productMerchantMapModel.getMerchantID());

        if (!((productMerchantMapModel.getProductStock() - updateStockDto.getQty()) < 0)) {
//            System.out.println("Product stock : " + productMerchantMapModel.getProductStock() +
//                    "Required quantity : " + updateStockDto.getQty());

            checkAvailabilityDto.setProductID(productMerchantMapModel.getProductID());
            checkAvailabilityDto.setAvailable(true);
            checkAvailabilityDto.setSuccess(true);
            checkAvailabilityDto.setMessage("Successful");
            return checkAvailabilityDto;
        }
        checkAvailabilityDto.setProductID(updateStockDto.getProductID());
        checkAvailabilityDto.setAvailable(false);
        checkAvailabilityDto.setSuccess(true);
        checkAvailabilityDto.setMessage("Successful");
        return checkAvailabilityDto;
    }

    @Override
    public ProductDto updatePrice(Long productID) {
        ArrayList<ProductMerchantMapDto> productMerchantMapDtos = getMerchantsByProductID(productID);
        int maxWeightFactor = 0;
        Long bestMerchantIDIndex = 0L;
        Double productPrice = null;
        Double merchantName;

        for (int loopIndex = 0; loopIndex < productMerchantMapDtos.size(); loopIndex++) {

            if (productMerchantMapDtos.get(loopIndex).getWeightedFactor() > maxWeightFactor) {
                maxWeightFactor = productMerchantMapDtos.get(loopIndex).getWeightedFactor();
                bestMerchantIDIndex = productMerchantMapDtos.get(loopIndex).getMerchantID();
                productPrice = productMerchantMapDtos.get(loopIndex).getProductPrice();
            }
        }
        MerchantModel merchantModel = merchantRepositoryInterface.findById(bestMerchantIDIndex).get();
        MerchantDto merchantDto = new MerchantDto();
        BeanUtils.copyProperties(merchantModel, merchantDto);

        ProductDto productDto = new ProductDto();
        productDto.setProductPrice(productPrice);
        productDto.setMerchantName(merchantDto.getMerchantName());
        productDto.setMerchantID(merchantDto.getMerchantID());
        return (productDto);
    }


    public void productModelToDto(ArrayList<ProductModel> productModels, ArrayList<ProductDto> productDtos) {
        for (int i = 0; i < productModels.size(); i++) {
            ProductDto productDto = new ProductDto();

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
//            productDto.setProductMerchantName(productModels.get(i).getProductMerchantName());
//            productDto.setProductPrice(productModels.get(i).getProductPrice());
            productDtos.add(productDto);
        }
    }

    public void productMerchantModelToDto(ArrayList<ProductMerchantMapModel> productMerchantModels,
                                          ArrayList<ProductMerchantMapDto> productMerchantMapDtos) {
        for (int i = 0; i < productMerchantModels.size(); i++) {
            ProductMerchantMapDto productMerchantDto = new ProductMerchantMapDto();
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
    public ProductCartDto getProductForCart(Long productID) {
//        System.out.println("In get product for cart");
        ProductCartDto productCartDto = new ProductCartDto();
        ProductDto productDto = new ProductDto();

        if(!productRepositoryInterface.existsById(productID)){
            productCartDto.setSuccess(false);
            productCartDto.setMessage("No such product exists");
            return productCartDto;
        }

        ProductModel productModel = productRepositoryInterface.findById(productID).get();

        productCartDto.setProductID(productModel.getProductID());
        productCartDto.setProductName(productModel.getProductName());
        productCartDto.setImage(productModel.getProductImgUrl());
        productCartDto.setSuccess(true);
        productCartDto.setMessage("Successful");
        return (productCartDto);
    }

    @Override
    public ProductSearchDto addProductForSearch(Long productID) {
//        System.out.println("In add product for search");

        /* To Get merchant name,id, price from update price */
        ProductDto productDto = getProductById(productID);
        ProductSearchDto productSearchDto = new ProductSearchDto();

        /*Get category name */
        CategoryDto categoryDto = new CategoryDto();
        CategoryModel categoryModel = new CategoryModel();
        categoryDto.setCategoryID(productDto.getCategoryID());
        BeanUtils.copyProperties(categoryDto, categoryModel);
        categoryModel = categoryRepositoryInterface.findById(categoryModel.getCategoryID()).get();
        BeanUtils.copyProperties(categoryModel, categoryDto);

        /* get merchant list*/
        ArrayList<ProductMerchantMapModel> productMerchantMapModels = new ArrayList<>();
        productMerchantMapModels =
                productMerchantMapRepositoryInterface.findByProductID(productDto.getProductID());
        int merchantCount = productMerchantMapModels.size();

        productSearchDto.setProductID(productDto.getProductID());
        productSearchDto.setProductCategoryName(categoryModel.getCategoryName());
        productSearchDto.setProductName(productDto.getProductName());
        productSearchDto.setProductImgUrl(productDto.getProductImgUrl());
        productSearchDto.setProductDescription(productDto.getProductDescription());
        productSearchDto.setProductBrandName(productDto.getProductBrandName());
        productSearchDto.setProductPrice(productDto.getProductPrice());
        productSearchDto.setMerchantID(productDto.getMerchantID());
        productSearchDto.setProductMerchantName(productDto.getMerchantName());
        productSearchDto.setMerchantCount(merchantCount);

        boolean responseSearch = searchClientInterface.sendToSearch(productSearchDto);

        if (!responseSearch) {
            throw new RuntimeException("Something wrong with search micro service...");
        }
        return productSearchDto;

    }

    @Override
    public void computeWeightedFactor(Long productID, Long merchantID) {
        int weightage1 = 10;
        int weightage2 = 10;
        int weightage3 = 10;
        int weightage4 = 10;
        int weightage5 = 10;
        int weightage6 = 10;

        Long productStock = null;
        Long productSold = null;
        int merchantRating = 0;
        Double productPrice = null;
        int productRating = 0;
        int currentStock = 0;

        int weightedFactor = 0;

//        System.out.println("In weighted factor with pid: " + productID + " mid: " + merchantID);

        ProductMerchantMapModel productMerchantMapModel =
                productMerchantMapRepositoryInterface.findByProductIDAndMerchantID(productID,merchantID);

        productStock = productMerchantMapModel.getProductStock();
        productSold = productMerchantMapModel.getProductSold();
        productPrice = productMerchantMapModel.getProductPrice();

        merchantRating =
                merchantRepositoryInterface.findById(productMerchantMapModel.getMerchantID()).get().getMerchantRating();

        productRating =
                productRepositoryInterface.findById(productMerchantMapModel.getProductID()).get().getProductRating();

        if (productSold == null ) {
            productSold = 0L;
        }
        currentStock = (int) (productStock - productSold);
//        System.out.println("currentStock: " + currentStock + " productPrice: " + productPrice);
//        System.out.println("productStock " + productStock + " productSold: " + productSold);
//        System.out.println("merchantRating: " + merchantRating + " productRating: " + productRating);

        weightedFactor = (int) (((weightage1 * productStock) + (weightage2 * productSold) + (weightage3 * merchantRating)
                + (weightage4 * productPrice) + (weightage5 * productRating) + (weightage6 * currentStock)) / 6000);

        productMerchantMapModel.setWeightedFactor(weightedFactor);
        productMerchantMapRepositoryInterface.save(productMerchantMapModel);
    }
}

//    @Value("service.weightedFactor.weightage1")
//    int weightage1;
//    @Value("service.weightedFactor.weightage2")
//    int weightage2;
//    @Value("service.weightedFactor.weightage3")
//    int weightage3;
//    @Value("service.weightedFactor.weightage4")
//    int weightage4;
//    @Value("service.weightedFactor.weightage5")
//    int weightage5;