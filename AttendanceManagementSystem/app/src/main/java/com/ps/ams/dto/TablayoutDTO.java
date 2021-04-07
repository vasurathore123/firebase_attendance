package com.ps.ams.dto;

public class TablayoutDTO {

    String mOne;
    boolean isSelected;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public TablayoutDTO(String mOne) {
        this.mOne = mOne;
    }

    public TablayoutDTO(String mOne, boolean isSelected) {
        this.mOne = mOne;
        this.isSelected = isSelected;
    }

    public String getmOne() {
        return mOne;
    }

    public void setmOne(String mOne) {
        this.mOne = mOne;
    }
}
