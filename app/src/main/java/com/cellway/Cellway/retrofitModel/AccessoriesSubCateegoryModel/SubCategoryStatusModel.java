package com.cellway.Cellway.retrofitModel.AccessoriesSubCateegoryModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SubCategoryStatusModel {
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("data")
    @Expose
    private ArrayList<SubCategoryDataModel> data = null;
    @SerializedName("banner")
    @Expose
    private ArrayList<SubCategoryBannerModel> banner = null;

    public ArrayList<SubCategoryBannerModel> getBanner() {
        return banner;
    }

    public void setBanner(ArrayList<SubCategoryBannerModel> banner) {
        this.banner = banner;
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

    public ArrayList<SubCategoryDataModel> getData() {
        return data;
    }

    public void setData(ArrayList<SubCategoryDataModel> data) {
        this.data = data;
    }
}
