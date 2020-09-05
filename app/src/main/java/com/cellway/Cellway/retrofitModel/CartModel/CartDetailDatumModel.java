package com.cellway.Cellway.retrofitModel.CartModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CartDetailDatumModel {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("qty")
    @Expose
    private Integer qty;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("product_id")
    @Expose
    private Integer productId;
    @SerializedName("name")
    @Expose
    private Object name;
    @SerializedName("mrp")
    @Expose
    private Integer mrp;
    @SerializedName("category_name")
    @Expose
    private String categoryName;
    @SerializedName("brand_name")
    @Expose
    private String brandName;
    @SerializedName("price")
    @Expose
    private Integer price;
    @SerializedName("model_name")
    @Expose
    private String modelName;
    @SerializedName("product_image")
    @Expose
    private String productImage;
    @SerializedName("protection_price")
    @Expose
    private Integer protectionPrice;
    @SerializedName("backcover_price")
    @Expose
    private Integer backcoverPrice;
    @SerializedName("tempered_price")
    @Expose
    private Integer temperedPrice;
    @SerializedName("gb")
    @Expose
    private String gb;

    public String getGb() {
        return gb;
    }

    public void setGb(String gb) {
        this.gb = gb;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Object getName() {
        return name;
    }

    public void setName(Object name) {
        this.name = name;
    }

    public Integer getMrp() {
        return mrp;
    }

    public void setMrp(Integer mrp) {
        this.mrp = mrp;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
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
}
