package com.cellway.Cellway.retrofitModel.LoginRegisterModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OtpGetModel {
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("code")
    @Expose
    private String code;

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

}