package com.example.ghostkitchenandroid.ui.store_owner;

import com.example.ghostkitchenandroid.model.Kitchen;

import androidx.lifecycle.ViewModel;

public class MyKitchenViewModel extends ViewModel {

    private Kitchen kitchen;

    public void setKitchen(Kitchen kitchen) {
        this.kitchen = kitchen;
    }

    public Kitchen getKitchen() {
        return kitchen;
    }
}
