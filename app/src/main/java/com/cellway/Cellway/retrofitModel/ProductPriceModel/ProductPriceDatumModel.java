package com.cellway.Cellway.retrofitModel.ProductPriceModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductPriceDatumModel {
    @SerializedName("product_id")
    @Expose
    private Integer productId;
    @SerializedName("brand_name")
    @Expose
    private String brandName;
    @SerializedName("series_name")
    @Expose
    private String seriesName;
    @SerializedName("model_name")
    @Expose
    private String modelName;
    @SerializedName("gb")
    @Expose
    private String gb;
    @SerializedName("warrenty")
    @Expose
    private String warrenty;
    @SerializedName("sale_amount")
    @Expose
    private Integer saleAmount;
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

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getSeriesName() {
        return seriesName;
    }

    public void setSeriesName(String seriesName) {
        this.seriesName = seriesName;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
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

    public Integer getSaleAmount() {
        return saleAmount;
    }

    public void setSaleAmount(Integer saleAmount) {
        this.saleAmount = saleAmount;
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
