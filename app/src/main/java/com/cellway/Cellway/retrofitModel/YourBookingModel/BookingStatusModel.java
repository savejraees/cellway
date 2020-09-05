package com.cellway.Cellway.retrofitModel.YourBookingModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class BookingStatusModel {
    @SerializedName("product_id")
    @Expose
    private Integer productId;
    @SerializedName("protection_price")
    @Expose
    private Integer protectionPrice;
    @SerializedName("backcover_price")
    @Expose
    private Integer backcoverPrice;
    @SerializedName("tempered_price")
    @Expose
    private Integer temperedPrice;
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
    @SerializedName("processor")
    @Expose
    private String processor;
    @SerializedName("front_camera")
    @Expose
    private String frontCamera;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("banner_image")
    @Expose
    private String bannerImage;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("availableoffer")
    @Expose
    private ArrayList<BookingAvaiableModel> availableoffer = null;
    @SerializedName("images")
    @Expose
    private ArrayList<BookingBannerModel> images = null;
    @SerializedName("paymentpolicy")
    @Expose
    private ArrayList<BookingPaymentPolicy> paymentpolicy = null;
    @SerializedName("warrenty_month")
    @Expose
    private String warrentyMonth;
    @SerializedName("qty")
    @Expose
    private Integer qty;

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public String getWarrentyMonth() {
        return warrentyMonth;
    }

    public void setWarrentyMonth(String warrentyMonth) {
        this.warrentyMonth = warrentyMonth;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getProtectionPrice() {
        return protectionPrice;
    }

    public void setProtectionPrice(Integer protectionPrice) {
        this.protectionPrice = protectionPrice;
    }

    public Integer getBackcoverPrice() {
        return backcoverPrice;
    }

    public void setBackcoverPrice(Integer backcoverPrice) {
        this.backcoverPrice = backcoverPrice;
    }

    public Integer getTemperedPrice() {
        return temperedPrice;
    }

    public void setTemperedPrice(Integer temperedPrice) {
        this.temperedPrice = temperedPrice;
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

    public String getProcessor() {
        return processor;
    }

    public void setProcessor(String processor) {
        this.processor = processor;
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

    public String getBannerImage() {
        return bannerImage;
    }

    public void setBannerImage(String bannerImage) {
        this.bannerImage = bannerImage;
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

    public ArrayList<BookingAvaiableModel> getAvailableoffer() {
        return availableoffer;
    }

    public void setAvailableoffer(ArrayList<BookingAvaiableModel> availableoffer) {
        this.availableoffer = availableoffer;
    }

    public ArrayList<BookingBannerModel> getImages() {
        return images;
    }

    public void setImages(ArrayList<BookingBannerModel> images) {
        this.images = images;
    }

    public ArrayList<BookingPaymentPolicy> getPaymentpolicy() {
        return paymentpolicy;
    }

    public void setPaymentpolicy(ArrayList<BookingPaymentPolicy> paymentpolicy) {
        this.paymentpolicy = paymentpolicy;
    }

}
