package com.example.ghostkitchenandroid.ui.store_owner;

import com.example.ghostkitchenandroid.model.Kitchen;
import com.example.ghostkitchenandroid.model.KitchenAddress;
import com.example.ghostkitchenandroid.model.State;
import com.example.ghostkitchenandroid.network.kitchen.KitchenRepo;
import com.example.ghostkitchenandroid.network.user.UserRepo;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class EditKitchenViewModel extends ViewModel {

    private Kitchen kitchen;
    private KitchenRepo kitchenRepo = KitchenRepo.getInstance();

    void submit(String name, String addressLine1, String addressLine2, String city, State state, String zip, String phone) {
        KitchenAddress address = new KitchenAddress(name, name, addressLine1, addressLine2, city, state.toString(), zip, phone);
        Kitchen kitchen = new Kitchen(name, address);
        kitchen.setId(this.kitchen.getId());
        kitchenRepo.updateKitchen(kitchen);
    }

    LiveData<Kitchen> getKitchenLiveData() {
        return kitchenRepo.getKitchenLiveData();
    }

    public Kitchen getKitchen() {
        return kitchen;
    }

    public void setKitchen(Kitchen kitchen) {
        this.kitchen = kitchen;
    }
}
