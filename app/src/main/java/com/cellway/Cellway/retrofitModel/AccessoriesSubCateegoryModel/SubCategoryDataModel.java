package com.cellway.Cellway.retrofitModel.AccessoriesSubCateegoryModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubCategoryDataModel {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("subcat_name")
    @Expose
    private String subcatName;
    @SerializedName("cat_id")
    @Expose
    private Integer catId;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("subcat_img")
    @Expose
    private String subcatImg;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSubcatName() {
        return subcatName;
    }

    public void setSubcatName(String subcatName) {
        this.subcatName = subcatName;
    }

    public Integer getCatId() {
        return catId;
    }

    public void setCatId(Integer catId) {
        this.catId = catId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSubcatImg() {
        return subcatImg;
    }

    public void setSubcatImg(String subcatImg) {
        this.subcatImg = subcatImg;
    }
}
