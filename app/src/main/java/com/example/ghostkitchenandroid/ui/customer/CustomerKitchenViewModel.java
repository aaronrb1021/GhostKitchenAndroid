package com.example.ghostkitchenandroid.ui.customer;

import com.example.ghostkitchenandroid.model.Kitchen;
import com.example.ghostkitchenandroid.model.User;
import com.example.ghostkitchenandroid.network.user.UserRepo;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class CustomerKitchenViewModel extends ViewModel {

    private Kitchen kitchen;
    private UserRepo userRepo = new UserRepo();

    public Kitchen getKitchen() {
        return kitchen;
    }

    public void setKitchen(Kitchen kitchen) {
        this.kitchen = kitchen;
    }

    LiveData<User> getUserLiveData() {
        return userRepo.getUserLiveData();
    }

    void updateUser(User user) {
        userRepo.updateUser(user);
    }
}
