package com.cellway.Cellway.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Product_Description implements Parcelable {



    String id;
    String img_mobile;
    String deduct_price;
    String tv_size;
    String tv_battery;
    String tv_rear_amera;
    String tv_front_amera;
    String tv_wrnty;
    String tv_wrntyMonth;
    String brand;
    String model;
    String gb;
    String other_id;
    String other_category;
    String other_img_mobile;
    String other_deduct_price;
    String other_tv_size;
    String other_tv_battery;
    String other_tv_rear_amera;
    String other_tv_front_amera;
    String other_tv_wrnty;
    String other_brand;
    String other_model;
    String other_gb;
    String remark;
    String condition;
    private String series_name;
    private boolean isSelected;

    public Product_Description(){

    }

    protected Product_Description(Parcel in) {
        id = in.readString();
        img_mobile = in.readString();
        deduct_price = in.readString();
        tv_size = in.readString();
        tv_battery = in.readString();
        tv_rear_amera = in.readString();
        tv_front_amera = in.readString();
        tv_wrnty = in.readString();
        tv_wrntyMonth = in.readString();
        brand = in.readString();
        model = in.readString();
        gb = in.readString();
        remark = in.readString();
        condition = in.readString();
        series_name = in.readString();
        isSelected = in.readByte() != 0;
        other_id = in.readString();
        other_category = in.readString();
        other_img_mobile = in.readString();
        other_deduct_price = in.readString();
        other_tv_size = in.readString();
        other_tv_battery = in.readString();
        other_tv_rear_amera = in.readString();
        other_tv_front_amera = in.readString();
        other_tv_wrnty = in.readString();
        other_brand = in.readString();
        other_model = in.readString();
        other_gb = in.readString();
    }

    public static final Creator<Product_Description> CREATOR = new Creator<Product_Description>() {
        @Override
        public Product_Description createFromParcel(Parcel in) {
            return new Product_Description(in);
        }

        @Override
        public Product_Description[] newArray(int size) {
            return new Product_Description[size];
        }
    };

    public String getSeries_name() {
        return series_name;
    }

    public void setSeries_name(String series_name) {
        this.series_name = series_name;
    }

    private static final long serialVersionUID = 1L;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }




    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getTv_wrntyMonth() {
        return tv_wrntyMonth;
    }

    public void setTv_wrntyMonth(String tv_wrntyMonth) {
        this.tv_wrntyMonth = tv_wrntyMonth;
    }

    public String getGb() {
        return gb;
    }

    public void setGb(String gb) {
        this.gb = gb;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getImg_mobile() {
        return img_mobile;
    }

    public void setImg_mobile(String img_mobile) {
        this.img_mobile = img_mobile;
    }
    public String getTv_wrnty() {
        return tv_wrnty;
    }

    public void setTv_wrnty(String tv_wrnty) {
        this.tv_wrnty = tv_wrnty;
    }

    public String getDeduct_price() {
        return deduct_price;
    }

    public void setDeduct_price(String deduct_price) {
        this.deduct_price = deduct_price;
    }

    public String getTv_size() {
        return tv_size;
    }

    public void setTv_size(String tv_size) {
        this.tv_size = tv_size;
    }

    public String getTv_battery() {
        return tv_battery;
    }

    public void setTv_battery(String tv_battery) {
        this.tv_battery = tv_battery;
    }

    public String getTv_rear_amera() {
        return tv_rear_amera;
    }

    public void setTv_rear_amera(String tv_rear_amera) {
        this.tv_rear_amera = tv_rear_amera;
    }

    public String getTv_front_amera() {
        return tv_front_amera;
    }

    public void setTv_front_amera(String tv_front_amera) {
        this.tv_front_amera = tv_front_amera;
    }



    public String getOther_id() {
        return other_id;
    }

    public void setOther_id(String other_id) {
        this.other_id = other_id;
    }

    public String getOther_category() {
        return other_category;
    }

    public void setOther_category(String other_category) {
        this.other_category = other_category;
    }

    public String getOther_img_mobile() {
        return other_img_mobile;
    }

    public void setOther_img_mobile(String other_img_mobile) {
        this.other_img_mobile = other_img_mobile;
    }

    public String getOther_deduct_price() {
        return other_deduct_price;
    }

    public void setOther_deduct_price(String other_deduct_price) {
        this.other_deduct_price = other_deduct_price;
    }

    public String getOther_tv_size() {
        return other_tv_size;
    }

    public void setOther_tv_size(String other_tv_size) {
        this.other_tv_size = other_tv_size;
    }

    public String getOther_tv_battery() {
        return other_tv_battery;
    }

    public void setOther_tv_battery(String other_tv_battery) {
        this.other_tv_battery = other_tv_battery;
    }

    public String getOther_tv_rear_amera() {
        return other_tv_rear_amera;
    }

    public void setOther_tv_rear_amera(String other_tv_rear_amera) {
        this.other_tv_rear_amera = other_tv_rear_amera;
    }

    public String getOther_tv_front_amera() {
        return other_tv_front_amera;
    }

    public void setOther_tv_front_amera(String other_tv_front_amera) {
        this.other_tv_front_amera = other_tv_front_amera;
    }

    public String getOther_tv_wrnty() {
        return other_tv_wrnty;
    }

    public void setOther_tv_wrnty(String other_tv_wrnty) {
        this.other_tv_wrnty = other_tv_wrnty;
    }

    public String getOther_brand() {
        return other_brand;
    }

    public void setOther_brand(String other_brand) {
        this.other_brand = other_brand;
    }

    public String getOther_model() {
        return other_model;
    }

    public void setOther_model(String other_model) {
        this.other_model = other_model;
    }

    public String getOther_gb() {
        return other_gb;
    }

    public void setOther_gb(String other_gb) {
        this.other_gb = other_gb;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(img_mobile);
        parcel.writeString(deduct_price);
        parcel.writeString(tv_size);
        parcel.writeString(tv_battery);
        parcel.writeString(tv_rear_amera);
        parcel.writeString(tv_front_amera);
        parcel.writeString(tv_wrnty);
        parcel.writeString(tv_wrntyMonth);
        parcel.writeString(brand);
        parcel.writeString(model);
        parcel.writeString(gb);
        parcel.writeString(remark);
        parcel.writeString(condition);
        parcel.writeString(series_name);
        parcel.writeByte((byte) (isSelected ? 1 : 0));
        parcel.writeString(other_id);
        parcel.writeString(other_category);
        parcel.writeString(other_img_mobile);
        parcel.writeString(other_deduct_price);
        parcel.writeString(other_tv_size);
        parcel.writeString(other_tv_battery);
        parcel.writeString(other_tv_rear_amera);
        parcel.writeString(other_tv_front_amera);
        parcel.writeString(other_tv_wrnty);
        parcel.writeString(other_brand);
        parcel.writeString(other_model);
        parcel.writeString(other_gb);
    }
}
