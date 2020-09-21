package com.cellway.Cellway.retrofitModel.OrderHistoryModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class OrderHistoryDetailModel {
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("data")
    @Expose
    private ArrayList<Datum> data = null;

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

    public ArrayList<Datum> getData() {
        return data;
    }

    public void setData(ArrayList<Datum> data) {
        this.data = data;
    }

    public class Datum {

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
        private Object categoryName;
        @SerializedName("brand_name")
        @Expose
        private Object brandName;
        @SerializedName("price")
        @Expose
        private Object price;
        @SerializedName("gb")
        @Expose
        private Object gb;
        @SerializedName("model_name")
        @Expose
        private Object modelName;
        @SerializedName("product_image")
        @Expose
        private Object productImage;
        @SerializedName("protection_price")
        @Expose
        private Integer protectionPrice;
        @SerializedName("backcover_price")
        @Expose
        private Integer backcoverPrice;
        @SerializedName("tempered_price")
        @Expose
        private Integer temperedPrice;

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

        public Object getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(Object categoryName) {
            this.categoryName = categoryName;
        }

        public Object getBrandName() {
            return brandName;
        }

        public void setBrandName(Object brandName) {
            this.brandName = brandName;
        }

        public Object getPrice() {
            return price;
        }

        public void setPrice(Object price) {
            this.price = price;
        }

        public Object getGb() {
            return gb;
        }

        public void setGb(Object gb) {
            this.gb = gb;
        }

        public Object getModelName() {
            return modelName;
        }

        public void setModelName(Object modelName) {
            this.modelName = modelName;
        }

        public Object getProductImage() {
            return productImage;
        }

        public void setProductImage(Object productImage) {
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
}
