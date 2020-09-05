package com.cellway.Cellway.retrofitModel.BookingAccessoriesModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BookingAccessoriesBanner {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("product_img")
    @Expose
    private String productImg;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProductImg() {
        return productImg;
    }

    public void setProductImg(String productImg) {
        this.productImg = productImg;
    }
}
