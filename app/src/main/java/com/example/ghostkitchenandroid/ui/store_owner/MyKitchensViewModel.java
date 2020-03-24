package com.example.ghostkitchenandroid.ui.store_owner;

import com.example.ghostkitchenandroid.model.Kitchen;
import com.example.ghostkitchenandroid.model.User;
import com.example.ghostkitchenandroid.network.kitchen.KitchenRepo;

import java.util.ArrayList;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class MyKitchensViewModel extends ViewModel {

    private KitchenRepo kitchenRepo = KitchenRepo.getInstance();

    public LiveData<ArrayList<Kitchen>> getKitchensLiveData() {
        return kitchenRepo.getKitchensLiveData();
    }

    public void updateKitchensLiveData(User user) {
        kitchenRepo.getKitchensByUser(user);
    }
}
