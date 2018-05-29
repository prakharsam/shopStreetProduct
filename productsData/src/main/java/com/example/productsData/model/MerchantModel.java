package com.example.productsData.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = MerchantModel.COLLECTION_NAME)
public class MerchantModel {
    public static final String COLLECTION_NAME="merchantdetails";

    @Id
    private Long merchantID;
    private String merchantName;
    private int merchantRating;

    public int getMerchantRating() {
        return merchantRating;
    }

    public void setMerchantRating(int merchantRating) {
        this.merchantRating = merchantRating;
    }

    public Long getMerchantID() {
        return merchantID;
    }

    public void setMerchantID(Long merchantID) {
        this.merchantID = merchantID;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public MerchantModel() {
    }
}
