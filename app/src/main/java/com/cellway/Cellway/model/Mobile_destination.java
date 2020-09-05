package com.cellway.Cellway.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Mobile_destination implements Parcelable {




    private String MobDestimg;
    private String id;
    private String brand_name;

    private String latestId;
    private String latestImage;
    private String latest_mobile_name;
    private String latest_mobile_GB;
    private String latest_price;
    private String latest_model;
    private String latest_warrantyMonth;

    public Mobile_destination(){

    }

    public Mobile_destination(Parcel in) {
        MobDestimg = in.readString();
        id = in.readString();
        brand_name = in.readString();
        bannerImage = in.readString();
        latestId = in.readString();
        latestImage = in.readString();
        latest_mobile_name = in.readString();
        latest_mobile_GB = in.readString();
        latest_price = in.readString();
        latest_model = in.readString();
        latest_warrantyMonth = in.readString();
    }

    public static final Creator<Mobile_destination> CREATOR = new Creator<Mobile_destination>() {
        @Override
        public Mobile_destination createFromParcel(Parcel in) {
            return new Mobile_destination(in);
        }

        @Override
        public Mobile_destination[] newArray(int size) {
            return new Mobile_destination[size];
        }
    };

    public String getMobDestimg() {
        return MobDestimg;
    }

    public void setMobDestimg(String mobDestimg) {
        MobDestimg = mobDestimg;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getBrand_name() {
        return brand_name;
    }
    public void setBrand_name(String brand_name) {
        this.brand_name = brand_name;
    }



    private String bannerImage;
    public String getBannerImage() {
        return bannerImage;
    }

    public void setBannerImage(String bannerImage) {
        this.bannerImage = bannerImage;
    }




    public String getLatest_warrantyMonth() {
        return latest_warrantyMonth;
    }

    public void setLatest_warrantyMonth(String latest_warrantyMonth) {
        this.latest_warrantyMonth = latest_warrantyMonth;
    }



    public String getLatest_model() {
        return latest_model;
    }

    public void setLatest_model(String latest_model) {
        this.latest_model = latest_model;
    }

    public String getLatest_mobile_GB() {
        return latest_mobile_GB;
    }

    public void setLatest_mobile_GB(String latest_mobile_GB) {
        this.latest_mobile_GB = latest_mobile_GB;
    }


    public String getLatestId() {
        return latestId;
    }

    public void setLatestId(String latestId) {
        this.latestId = latestId;
    }

    public String getLatestImage() {
        return latestImage;
    }

    public void setLatestImage(String latestImage) {
        this.latestImage = latestImage;
    }

    public String getLatest_mobile_name() {
        return latest_mobile_name;
    }

    public void setLatest_mobile_name(String latest_mobile_name) {
        this.latest_mobile_name = latest_mobile_name;
    }


    public String getLatest_price() {
        return latest_price;
    }

    public void setLatest_price(String latest_price) {
        this.latest_price = latest_price;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(MobDestimg);
        parcel.writeString(id);
        parcel.writeString(brand_name);
        parcel.writeString(bannerImage);
        parcel.writeString(latestId);
        parcel.writeString(latestImage);
        parcel.writeString(latest_mobile_name);
        parcel.writeString(latest_mobile_GB);
        parcel.writeString(latest_price);
        parcel.writeString(latest_model);
        parcel.writeString(latest_warrantyMonth);
    }
}
