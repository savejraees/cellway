package com.cellway.Cellway.retrofitModel.YourMobDestination;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MobDestData {
    @SerializedName("allproduct")
    @Expose
    private ArrayList<MobDestAllProd> allproduct = null;
    @SerializedName("shopcategory")
    @Expose
    private ArrayList<MobDestShopCategory> shopcategory = null;
    @SerializedName("genuines")
    @Expose
    private ArrayList<MobDestGenuine> genuines = null;
    @SerializedName("bestsellers")
    @Expose
    private ArrayList<MobDestBestSeller> bestsellers = null;
    @SerializedName("shopprices")
    @Expose
    private ArrayList<MobDestShopPrice> shopprices = null;
    @SerializedName("knowmores")
    @Expose
    private ArrayList<MobDestKnowMore> knowmores = null;
    @SerializedName("offers")
    @Expose
    private ArrayList<MobDestOffer> offers = null;
    @SerializedName("brands")
    @Expose
    private ArrayList<MobDestBrand> brands = null;

    public ArrayList<MobDestAllProd> getAllproduct() {
        return allproduct;
    }

    public void setAllproduct(ArrayList<MobDestAllProd> allproduct) {
        this.allproduct = allproduct;
    }

    public ArrayList<MobDestShopCategory> getShopcategory() {
        return shopcategory;
    }

    public void setShopcategory(ArrayList<MobDestShopCategory> shopcategory) {
        this.shopcategory = shopcategory;
    }

    public ArrayList<MobDestGenuine> getGenuines() {
        return genuines;
    }

    public void setGenuines(ArrayList<MobDestGenuine> genuines) {
        this.genuines = genuines;
    }

    public ArrayList<MobDestBestSeller> getBestsellers() {
        return bestsellers;
    }

    public void setBestsellers(ArrayList<MobDestBestSeller> bestsellers) {
        this.bestsellers = bestsellers;
    }

    public ArrayList<MobDestShopPrice> getShopprices() {
        return shopprices;
    }

    public void setShopprices(ArrayList<MobDestShopPrice> shopprices) {
        this.shopprices = shopprices;
    }

    public ArrayList<MobDestKnowMore> getKnowmores() {
        return knowmores;
    }

    public void setKnowmores(ArrayList<MobDestKnowMore> knowmores) {
        this.knowmores = knowmores;
    }

    public ArrayList<MobDestOffer> getOffers() {
        return offers;
    }

    public void setOffers(ArrayList<MobDestOffer> offers) {
        this.offers = offers;
    }

    public ArrayList<MobDestBrand> getBrands() {
        return brands;
    }

    public void setBrands(ArrayList<MobDestBrand> brands) {
        this.brands = brands;
    }
}
