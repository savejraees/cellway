package com.cellway.Cellway.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class TabletModel implements Parcelable {
  /*  public int getTabimage() {
        return tabimage;
    }

    public void setTabimage(int tabimage) {
        this.tabimage = tabimage;
    }

    private int tabimage;
*/

    public TabletModel(){

    }

    private String tabimage;
    private String id;
    private String brandname;

    protected TabletModel(Parcel in) {
        tabimage = in.readString();
        id = in.readString();
        brandname = in.readString();
    }

    public static final Creator<TabletModel> CREATOR = new Creator<TabletModel>() {
        @Override
        public TabletModel createFromParcel(Parcel in) {
            return new TabletModel(in);
        }

        @Override
        public TabletModel[] newArray(int size) {
            return new TabletModel[size];
        }
    };

    public String getBrandname() {
        return brandname;
    }

    public void setBrandname(String brandname) {
        this.brandname = brandname;
    }

    public String getTabimage() {
        return tabimage;
    }

    public void setTabimage(String tabimage) {
        this.tabimage = tabimage;
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(tabimage);
        parcel.writeString(id);
        parcel.writeString(brandname);
    }
}
