package com.cellway.Cellway.retrofitModel.ProductBrandModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ProductBrandStatusModel {
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
    private ArrayList<ProductBrandDatumModel> data = null;
    @SerializedName("series")
    @Expose
    private ArrayList<ProductBrandSeriesModel> series = null;
    @SerializedName("category")
    @Expose
    private ArrayList<ProductBrandCategoryModel> category = null;

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

    public ArrayList<ProductBrandDatumModel> getData() {
        return data;
    }

    public void setData(ArrayList<ProductBrandDatumModel> data) {
        this.data = data;
    }

    public ArrayList<ProductBrandSeriesModel> getSeries() {
        return series;
    }

    public void setSeries(ArrayList<ProductBrandSeriesModel> series) {
        this.series = series;
    }

    public ArrayList<ProductBrandCategoryModel> getCategory() {
        return category;
    }

    public void setCategory(ArrayList<ProductBrandCategoryModel> category) {
        this.category = category;
    }

}
