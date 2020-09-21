package com.cellway.Cellway.retrofitModel.OrderHistoryModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class OrderHistoryStatusModel {
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
        @SerializedName("userid")
        @Expose
        private Integer userid;
        @SerializedName("ref_id")
        @Expose
        private String refId;
        @SerializedName("total_qty")
        @Expose
        private Integer totalQty;
        @SerializedName("total_amount")
        @Expose
        private Integer totalAmount;
        @SerializedName("address")
        @Expose
        private String address;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("delivery_date")
        @Expose
        private Object deliveryDate;
        @SerializedName("cdate")
        @Expose
        private String cdate;
        @SerializedName("payment_mode")
        @Expose
        private String paymentMode;
        @SerializedName("payment_bank")
        @Expose
        private Object paymentBank;
        @SerializedName("booking_amount")
        @Expose
        private Integer bookingAmount;
        @SerializedName("shipping_charge")
        @Expose
        private Integer shippingCharge;
        @SerializedName("delivery_charge")
        @Expose
        private Integer deliveryCharge;
        @SerializedName("online_payment_status")
        @Expose
        private Integer onlinePaymentStatus;
        @SerializedName("delivery_type")
        @Expose
        private String deliveryType;
        @SerializedName("cod_payment_recived")
        @Expose
        private Integer codPaymentRecived;
        @SerializedName("cancel_button")
        @Expose
        private Integer cancelButton;
        @SerializedName("return_button")
        @Expose
        private Integer returnButton;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getUserid() {
            return userid;
        }

        public void setUserid(Integer userid) {
            this.userid = userid;
        }

        public String getRefId() {
            return refId;
        }

        public void setRefId(String refId) {
            this.refId = refId;
        }

        public Integer getTotalQty() {
            return totalQty;
        }

        public void setTotalQty(Integer totalQty) {
            this.totalQty = totalQty;
        }

        public Integer getTotalAmount() {
            return totalAmount;
        }

        public void setTotalAmount(Integer totalAmount) {
            this.totalAmount = totalAmount;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public Object getDeliveryDate() {
            return deliveryDate;
        }

        public void setDeliveryDate(Object deliveryDate) {
            this.deliveryDate = deliveryDate;
        }

        public String getCdate() {
            return cdate;
        }

        public void setCdate(String cdate) {
            this.cdate = cdate;
        }

        public String getPaymentMode() {
            return paymentMode;
        }

        public void setPaymentMode(String paymentMode) {
            this.paymentMode = paymentMode;
        }

        public Object getPaymentBank() {
            return paymentBank;
        }

        public void setPaymentBank(Object paymentBank) {
            this.paymentBank = paymentBank;
        }

        public Integer getBookingAmount() {
            return bookingAmount;
        }

        public void setBookingAmount(Integer bookingAmount) {
            this.bookingAmount = bookingAmount;
        }

        public Integer getShippingCharge() {
            return shippingCharge;
        }

        public void setShippingCharge(Integer shippingCharge) {
            this.shippingCharge = shippingCharge;
        }

        public Integer getDeliveryCharge() {
            return deliveryCharge;
        }

        public void setDeliveryCharge(Integer deliveryCharge) {
            this.deliveryCharge = deliveryCharge;
        }

        public Integer getOnlinePaymentStatus() {
            return onlinePaymentStatus;
        }

        public void setOnlinePaymentStatus(Integer onlinePaymentStatus) {
            this.onlinePaymentStatus = onlinePaymentStatus;
        }

        public String getDeliveryType() {
            return deliveryType;
        }

        public void setDeliveryType(String deliveryType) {
            this.deliveryType = deliveryType;
        }

        public Integer getCodPaymentRecived() {
            return codPaymentRecived;
        }

        public void setCodPaymentRecived(Integer codPaymentRecived) {
            this.codPaymentRecived = codPaymentRecived;
        }

        public Integer getCancelButton() {
            return cancelButton;
        }

        public void setCancelButton(Integer cancelButton) {
            this.cancelButton = cancelButton;
        }

        public Integer getReturnButton() {
            return returnButton;
        }

        public void setReturnButton(Integer returnButton) {
            this.returnButton = returnButton;
        }

        @SerializedName("item")
        @Expose
        private Item item;

        public Item getItem() {
            return item;
        }

        public void setItem(Item item) {
            this.item = item;
        }


        public class Item {

            @SerializedName("id")
            @Expose
            private Integer id;
            @SerializedName("order_id")
            @Expose
            private Integer orderId;
            @SerializedName("category")
            @Expose
            private String category;
            @SerializedName("product_id")
            @Expose
            private Integer productId;
            @SerializedName("qty")
            @Expose
            private Integer qty;
            @SerializedName("is_protection")
            @Expose
            private Integer isProtection;
            @SerializedName("is_backcover")
            @Expose
            private Integer isBackcover;
            @SerializedName("is_tempered")
            @Expose
            private Integer isTempered;
            @SerializedName("payment_status")
            @Expose
            private Integer paymentStatus;
            @SerializedName("is_return")
            @Expose
            private Integer isReturn;
            @SerializedName("is_cancel")
            @Expose
            private Integer isCancel;
            @SerializedName("status")
            @Expose
            private Object status;
            @SerializedName("brand_name")
            @Expose
            private String brandName;
            @SerializedName("series_name")
            @Expose
            private String seriesName;
            @SerializedName("model_name")
            @Expose
            private String modelName;
            @SerializedName("image")
            @Expose
            private String image;

            public Integer getId() {
                return id;
            }

            public void setId(Integer id) {
                this.id = id;
            }

            public Integer getOrderId() {
                return orderId;
            }

            public void setOrderId(Integer orderId) {
                this.orderId = orderId;
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

            public Integer getQty() {
                return qty;
            }

            public void setQty(Integer qty) {
                this.qty = qty;
            }

            public Integer getIsProtection() {
                return isProtection;
            }

            public void setIsProtection(Integer isProtection) {
                this.isProtection = isProtection;
            }

            public Integer getIsBackcover() {
                return isBackcover;
            }

            public void setIsBackcover(Integer isBackcover) {
                this.isBackcover = isBackcover;
            }

            public Integer getIsTempered() {
                return isTempered;
            }

            public void setIsTempered(Integer isTempered) {
                this.isTempered = isTempered;
            }

            public Integer getPaymentStatus() {
                return paymentStatus;
            }

            public void setPaymentStatus(Integer paymentStatus) {
                this.paymentStatus = paymentStatus;
            }

            public Integer getIsReturn() {
                return isReturn;
            }

            public void setIsReturn(Integer isReturn) {
                this.isReturn = isReturn;
            }

            public Integer getIsCancel() {
                return isCancel;
            }

            public void setIsCancel(Integer isCancel) {
                this.isCancel = isCancel;
            }

            public Object getStatus() {
                return status;
            }

            public void setStatus(Object status) {
                this.status = status;
            }

            public String getBrandName() {
                return brandName;
            }

            public void setBrandName(String brandName) {
                this.brandName = brandName;
            }

            public String getSeriesName() {
                return seriesName;
            }

            public void setSeriesName(String seriesName) {
                this.seriesName = seriesName;
            }

            public String getModelName() {
                return modelName;
            }

            public void setModelName(String modelName) {
                this.modelName = modelName;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

        }
    }
}
