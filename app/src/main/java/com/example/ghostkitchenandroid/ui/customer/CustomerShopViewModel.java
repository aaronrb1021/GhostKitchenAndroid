package com.example.ghostkitchenandroid.ui.customer;

import androidx.lifecycle.ViewModel;

public class CustomerShopViewModel extends ViewModel {
    private int positionSelection = 4;
    private String zipCode;

    public int getPositionSelection() {
        return positionSelection;
    }

    public void setPositionSelection(int positionSelection) {
        this.positionSelection = positionSelection;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
}
