package com.cellway.Cellway.retrofitModel.AccessoriesCategoryModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CategoryStatusModel {

    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("banner")
    @Expose
    private ArrayList<CatgoryBannerModel> banner = null;
    @SerializedName("data")
    @Expose
    private ArrayList<CategoryDatumModel> data = null;
    @SerializedName("latest")
    @Expose
    private ArrayList<CategoryLatestModel> latest = null;

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

    public ArrayList<CatgoryBannerModel> getBanner() {
        return banner;
    }

    public void setBanner(ArrayList<CatgoryBannerModel> banner) {
        this.banner = banner;
    }

    public ArrayList<CategoryDatumModel> getData() {
        return data;
    }

    public void setData(ArrayList<CategoryDatumModel> data) {
        this.data = data;
    }

    public ArrayList<CategoryLatestModel> getLatest() {
        return latest;
    }

    public void setLatest(ArrayList<CategoryLatestModel> latest) {
        this.latest = latest;
    }

}
