package com.cellway.Cellway.retrofitModel.ProductModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ProductStatusModel {
    @SerializedName("total_pages")
    @Expose
    private Integer totalPages;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("brand_name")
    @Expose
    private String brandName;
    @SerializedName("data")
    @Expose
    private ArrayList<ProductDatumModel> data = null;
    @SerializedName("brands")
    @Expose
    private ArrayList<ProductBrandModel> brands = null;
    @SerializedName("category")
    @Expose
    private ArrayList<ProductCategoryModel> category = null;

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public ArrayList<ProductDatumModel> getData() {
        return data;
    }

    public void setData(ArrayList<ProductDatumModel> data) {
        this.data = data;
    }

    public ArrayList<ProductBrandModel> getBrands() {
        return brands;
    }

    public void setBrands(ArrayList<ProductBrandModel> brands) {
        this.brands = brands;
    }

    public ArrayList<ProductCategoryModel> getCategory() {
        return category;
    }

    public void setCategory(ArrayList<ProductCategoryModel> category) {
        this.category = category;
    }
}
