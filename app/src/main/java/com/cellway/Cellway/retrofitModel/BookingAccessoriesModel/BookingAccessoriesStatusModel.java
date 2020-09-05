package com.cellway.Cellway.retrofitModel.BookingAccessoriesModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class BookingAccessoriesStatusModel {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("cat_id")
    @Expose
    private Integer catId;
    @SerializedName("subcat_id")
    @Expose
    private Integer subcatId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("brand_id")
    @Expose
    private Integer brandId;
    @SerializedName("model_id")
    @Expose
    private Integer modelId;
    @SerializedName("mrp")
    @Expose
    private Integer mrp;
    @SerializedName("price")
    @Expose
    private Integer price;
    @SerializedName("barcode")
    @Expose
    private String barcode;
    @SerializedName("color")
    @Expose
    private String color;
    @SerializedName("description")
    @Expose
    private Object description;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("latest_arr")
    @Expose
    private Integer latestArr;
    @SerializedName("newstock")
    @Expose
    private Integer newstock;
    @SerializedName("totalstock")
    @Expose
    private Integer totalstock;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("subcat_name")
    @Expose
    private String subcatName;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("brand_name")
    @Expose
    private String brandName;
    @SerializedName("model_name")
    @Expose
    private String modelName;
    @SerializedName("product_image")
    @Expose
    private String productImage;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("product_images")
    @Expose
    private ArrayList<BookingAccessoriesBanner> productImages = null;
    @SerializedName("availableoffer")
    @Expose
    private ArrayList<BookingAccessoriesAvailableOffer> availableoffer = null;
    @SerializedName("qty")
    @Expose
    private Integer qty;

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public ArrayList<BookingAccessoriesAvailableOffer> getAvailableoffer() {
        return availableoffer;
    }

    public void setAvailableoffer(ArrayList<BookingAccessoriesAvailableOffer> availableoffer) {
        this.availableoffer = availableoffer;
    }

    public ArrayList<BookingAccessoriesPaymentPolicy> getPaymentPolicy() {
        return paymentPolicy;
    }

    public void setPaymentPolicy(ArrayList<BookingAccessoriesPaymentPolicy> paymentPolicy) {
        this.paymentPolicy = paymentPolicy;
    }

    @SerializedName("payment_policy")
    @Expose
    private ArrayList<BookingAccessoriesPaymentPolicy> paymentPolicy = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCatId() {
        return catId;
    }

    public void setCatId(Integer catId) {
        this.catId = catId;
    }

    public Integer getSubcatId() {
        return subcatId;
    }

    public void setSubcatId(Integer subcatId) {
        this.subcatId = subcatId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public Integer getModelId() {
        return modelId;
    }

    public void setModelId(Integer modelId) {
        this.modelId = modelId;
    }

    public Integer getMrp() {
        return mrp;
    }

    public void setMrp(Integer mrp) {
        this.mrp = mrp;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Object getDescription() {
        return description;
    }

    public void setDescription(Object description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getLatestArr() {
        return latestArr;
    }

    public void setLatestArr(Integer latestArr) {
        this.latestArr = latestArr;
    }

    public Integer getNewstock() {
        return newstock;
    }

    public void setNewstock(Integer newstock) {
        this.newstock = newstock;
    }

    public Integer getTotalstock() {
        return totalstock;
    }

    public void setTotalstock(Integer totalstock) {
        this.totalstock = totalstock;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSubcatName() {
        return subcatName;
    }

    public void setSubcatName(String subcatName) {
        this.subcatName = subcatName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
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

    public ArrayList<BookingAccessoriesBanner> getProductImages() {
        return productImages;
    }

    public void setProductImages(ArrayList<BookingAccessoriesBanner> productImages) {
        this.productImages = productImages;
    }

}
