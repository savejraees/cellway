package com.cellway.Cellway.retrofitModel.YourBookingModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BookingAvaiableModel {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("phone_id")
    @Expose
    private Integer phoneId;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("description")
    @Expose
    private String description;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPhoneId() {
        return phoneId;
    }

    public void setPhoneId(Integer phoneId) {
        this.phoneId = phoneId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
