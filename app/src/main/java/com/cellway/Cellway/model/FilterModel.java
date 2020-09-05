package com.cellway.Cellway.model;

import java.io.Serializable;

public class FilterModel implements Serializable {
    private static final long serialVersionUID = 1L;

    private boolean isSelected;
    private String series;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }
}
