package com.cellway.Cellway.retrofitModel.YourMobDestination;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MobDestBestSeller {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("purchase_cat_name")
    @Expose
    private String purchaseCatName;
    @SerializedName("product_category")
    @Expose
    private String productCategory;
    @SerializedName("brand_name")
    @Expose
    private String brand;
    @SerializedName("series_name")
    @Expose
    private String seriesName;
    @SerializedName("model")
    @Expose
    private String model;
    @SerializedName("gb")
    @Expose
    private String gb;
    @SerializedName("warrenty")
    @Expose
    private String warrenty;
    @SerializedName("warrenty_month")
    @Expose
    private String warrentyMonth;
    @SerializedName("imei_no")
    @Expose
    private String imeiNo;
    @SerializedName("product_condotion")
    @Expose
    private String productCondotion;
    @SerializedName("remark")
    @Expose
    private String remark;
    @SerializedName("sale_amount")
    @Expose
    private Integer saleAmount;
    @SerializedName("model_name")
    @Expose
    private String modelName;
    @SerializedName("mobile_display")
    @Expose
    private String mobileDisplay;
    @SerializedName("battery")
    @Expose
    private String battery;
    @SerializedName("rear_camera")
    @Expose
    private String rearCamera;
    @SerializedName("front_camera")
    @Expose
    private String frontCamera;
    @SerializedName("image")
    @Expose
    private String image;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPurchaseCatName() {
        return purchaseCatName;
    }

    public void setPurchaseCatName(String purchaseCatName) {
        this.purchaseCatName = purchaseCatName;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getSeriesName() {
        return seriesName;
    }

    public void setSeriesName(String seriesName) {
        this.seriesName = seriesName;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getGb() {
        return gb;
    }

    public void setGb(String gb) {
        this.gb = gb;
    }

    public String getWarrenty() {
        return warrenty;
    }

    public void setWarrenty(String warrenty) {
        this.warrenty = warrenty;
    }

    public String getWarrentyMonth() {
        return warrentyMonth;
    }

    public void setWarrentyMonth(String warrentyMonth) {
        this.warrentyMonth = warrentyMonth;
    }

    public String getImeiNo() {
        return imeiNo;
    }

    public void setImeiNo(String imeiNo) {
        this.imeiNo = imeiNo;
    }

    public String getProductCondotion() {
        return productCondotion;
    }

    public void setProductCondotion(String productCondotion) {
        this.productCondotion = productCondotion;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getSaleAmount() {
        return saleAmount;
    }

    public void setSaleAmount(Integer saleAmount) {
        this.saleAmount = saleAmount;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getMobileDisplay() {
        return mobileDisplay;
    }

    public void setMobileDisplay(String mobileDisplay) {
        this.mobileDisplay = mobileDisplay;
    }

    public String getBattery() {
        return battery;
    }

    public void setBattery(String battery) {
        this.battery = battery;
    }

    public String getRearCamera() {
        return rearCamera;
    }

    public void setRearCamera(String rearCamera) {
        this.rearCamera = rearCamera;
    }

    public String getFrontCamera() {
        return frontCamera;
    }

    public void setFrontCamera(String frontCamera) {
        this.frontCamera = frontCamera;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
