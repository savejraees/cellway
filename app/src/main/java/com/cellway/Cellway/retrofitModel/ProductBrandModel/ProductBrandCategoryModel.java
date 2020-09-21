package com.cellway.Cellway.retrofitModel.ProductBrandModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductBrandCategoryModel {
    @SerializedName("shop_cat_id")
    @Expose
    private Integer shopCatId;
    @SerializedName("title")
    @Expose
    private String title;

    public Integer getShopCatId() {
        return shopCatId;
    }

    public void setShopCatId(Integer shopCatId) {
        this.shopCatId = shopCatId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    private boolean isSelected =false;
    public void setSelected(boolean selected) {
        isSelected = selected;
    }
    public boolean isSelected() {
        return isSelected;
    }

}
