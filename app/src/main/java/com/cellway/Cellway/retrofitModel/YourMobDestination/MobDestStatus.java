package com.cellway.Cellway.retrofitModel.YourMobDestination;

import com.cellway.Cellway.retrofitModel.YourMobDestination.MobDestData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MobDestStatus {
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("data")
    @Expose
    private ArrayList<MobDestData> data = null;

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

    public ArrayList<MobDestData> getData() {
        return data;
    }

    public void setData(ArrayList<MobDestData> data) {
        this.data = data;
    }

}
