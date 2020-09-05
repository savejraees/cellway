package com.cellway.Cellway.retrofitModel.ProductCategoryModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ProductCategoryStatusModel {
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
    private ArrayList<ProductCategoryDatumModel> data = null;
    @SerializedName("series")
    @Expose
    private ArrayList<ProductCategorySeriesModel> series = null;

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

    public ArrayList<ProductCategoryDatumModel> getData() {
        return data;
    }

    public void setData(ArrayList<ProductCategoryDatumModel> data) {
        this.data = data;
    }

    public ArrayList<ProductCategorySeriesModel> getSeries() {
        return series;
    }

    public void setSeries(ArrayList<ProductCategorySeriesModel> series) {
        this.series = series;
    }

}
