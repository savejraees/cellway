package com.cellway.Cellway.retrofitModel.ProductBrandModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductBrandSeriesModel {

    @SerializedName("series_id")
    @Expose
    private Integer seriesId;
    @SerializedName("series_name")
    @Expose
    private String seriesName;

    public Integer getSeriesId() {
        return seriesId;
    }

    public void setSeriesId(Integer seriesId) {
        this.seriesId = seriesId;
    }

    public String getSeriesName() {
        return seriesName;
    }

    public void setSeriesName(String seriesName) {
        this.seriesName = seriesName;
    }

    private boolean isSelected =false;
    public void setSelected(boolean selected) {
        isSelected = selected;
    }
    public boolean isSelected() {
        return isSelected;
    }

}
