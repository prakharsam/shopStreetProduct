package com.example.productsData.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = CategoryModel.COLLECTION_NAME)

public class CategoryModel {

    public static final String COLLECTION_NAME="categorydetails";

    @Id
    private Long categoryID;
    private String categoryName;

    public Long getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(Long categoryID) {
        this.categoryID = categoryID;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public CategoryModel() {
    }
}
