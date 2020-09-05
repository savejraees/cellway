package com.cellway.Cellway.retrofitModel.YourBookingModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PincodeModel {
        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("pin")
        @Expose
        private Integer pin;
        @SerializedName("status")
        @Expose
        private Integer status;
        @SerializedName("msg")
        @Expose
        private String msg;
        @SerializedName("code")
        @Expose
        private String code;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getPin() {
            return pin;
        }

        public void setPin(Integer pin) {
            this.pin = pin;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
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


}
