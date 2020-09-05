package com.cellway.Cellway.retrofitModel.AccessoriesProductModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class AccessoriesProductStatusModel {
    @SerializedName("total_pages")
    @Expose
    private Integer totalPages;
    @SerializedName("data")
    @Expose
    private ArrayList<AccessoriesProductDatumModel> data = null;
    @SerializedName("brands")
    @Expose
    private ArrayList<AccessoriesProductBrandModel> brands = null;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("code")
    @Expose
    private String code;

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public ArrayList<AccessoriesProductDatumModel> getData() {
        return data;
    }

    public void setData(ArrayList<AccessoriesProductDatumModel> data) {
        this.data = data;
    }

    public ArrayList<AccessoriesProductBrandModel> getBrands() {
        return brands;
    }

    public void setBrands(ArrayList<AccessoriesProductBrandModel> brands) {
        this.brands = brands;
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

}
