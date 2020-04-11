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

    LiveData<ArrayList<Kitchen>> getKitchensLiveData() {
        return kitchenRepo.getKitchensLiveData();
    }

    void updateKitchensLiveDataByUser(User user) {
        kitchenRepo.fetchKitchensByUser(user);
    }

    void updateKitchensLiveDataAllKitchens() {
        kitchenRepo.fetchAllKitchens();
    }

    int getMode() {
        return mode;
    }

    void setMode(int mode) {
        this.mode = mode;
    }
}
