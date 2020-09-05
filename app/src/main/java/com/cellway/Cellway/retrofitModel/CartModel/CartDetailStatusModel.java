package com.cellway.Cellway.retrofitModel.CartModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CartDetailStatusModel {
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("delivery_time")
    @Expose
    private String deliveryTime;
    @SerializedName("map_url")
    @Expose
    private String mapUrl;
    @SerializedName("pickup_address")
    @Expose
    private String pickupAddress;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("office_floor")
    @Expose
    private String officeFloor;
    @SerializedName("landmark")
    @Expose
    private String landmark;
    @SerializedName("locality")
    @Expose
    private String locality;
    @SerializedName("pincode")
    @Expose
    private Integer pincode;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("delivery_charge")
    @Expose
    private Integer deliveryCharge;
    @SerializedName("cod_booking_amount")
    @Expose
    private Integer codBookingAmount;
    @SerializedName("cod_charge")
    @Expose
    private Integer codCharge;
    @SerializedName("data")
    @Expose
    private ArrayList<CartDetailDatumModel> data = null;

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

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getMapUrl() {
        return mapUrl;
    }

    public void setMapUrl(String mapUrl) {
        this.mapUrl = mapUrl;
    }

    public String getPickupAddress() {
        return pickupAddress;
    }

    public void setPickupAddress(String pickupAddress) {
        this.pickupAddress = pickupAddress;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getOfficeFloor() {
        return officeFloor;
    }

    public void setOfficeFloor(String officeFloor) {
        this.officeFloor = officeFloor;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public Integer getPincode() {
        return pincode;
    }

    public void setPincode(Integer pincode) {
        this.pincode = pincode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getDeliveryCharge() {
        return deliveryCharge;
    }

    public void setDeliveryCharge(Integer deliveryCharge) {
        this.deliveryCharge = deliveryCharge;
    }

    public Integer getCodBookingAmount() {
        return codBookingAmount;
    }

    public void setCodBookingAmount(Integer codBookingAmount) {
        this.codBookingAmount = codBookingAmount;
    }

    public Integer getCodCharge() {
        return codCharge;
    }

    public void setCodCharge(Integer codCharge) {
        this.codCharge = codCharge;
    }

    public ArrayList<CartDetailDatumModel> getData() {
        return data;
    }

    public void setData(ArrayList<CartDetailDatumModel> data) {
        this.data = data;
    }

}
