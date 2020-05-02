package com.example.ghostkitchenandroid.ui.lists;

import com.example.ghostkitchenandroid.model.Kitchen;
import com.example.ghostkitchenandroid.model.User;
import com.example.ghostkitchenandroid.network.kitchen.KitchenRepo;

import java.util.ArrayList;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class KitchenListViewModel extends ViewModel {

    private KitchenRepo kitchenRepo = KitchenRepo.getInstance();
    private int mode;
    private KitchenListFragment.Radius radius;
    private String zip;

    LiveData<ArrayList<Kitchen>> getKitchensLiveData() {
        return kitchenRepo.getKitchensLiveData();
    }

    void updateKitchensLiveDataByUser(User user) {
        kitchenRepo.fetchKitchensByUser(user);
    }

    void updateKitchensLiveDataAllKitchens() {
        kitchenRepo.fetchAllKitchens();
    }

    void updateKitchensByDistanceFromZip(String distance) {
        kitchenRepo.fetchKitchensByDistanceFromZip(zip, distance);
    }

    int getMode() {
        return mode;
    }

    void setMode(int mode) {
        this.mode = mode;
    }

    public KitchenListFragment.Radius getRadius() {
        return radius;
    }

    public void setRadius(KitchenListFragment.Radius radius) {
        this.radius = radius;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }
}
