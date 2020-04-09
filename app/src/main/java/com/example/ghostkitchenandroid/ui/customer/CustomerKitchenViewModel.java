package com.example.ghostkitchenandroid.ui.customer;

import com.example.ghostkitchenandroid.model.Kitchen;

import androidx.lifecycle.ViewModel;

public class CustomerKitchenViewModel extends ViewModel {

    private Kitchen kitchen;

    public Kitchen getKitchen() {
        return kitchen;
    }

    public void setKitchen(Kitchen kitchen) {
        this.kitchen = kitchen;
    }
}
