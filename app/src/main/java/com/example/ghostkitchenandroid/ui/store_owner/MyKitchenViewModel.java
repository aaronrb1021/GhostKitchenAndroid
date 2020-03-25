package com.example.ghostkitchenandroid.ui.store_owner;

import com.example.ghostkitchenandroid.model.Item;
import com.example.ghostkitchenandroid.model.Kitchen;
import com.example.ghostkitchenandroid.network.item.ItemRepo;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class MyKitchenViewModel extends ViewModel {

    private Kitchen kitchen;
    private ItemRepo itemRepo = ItemRepo.getInstance();

    public void setKitchen(Kitchen kitchen) {
        this.kitchen = kitchen;
    }

    public Kitchen getKitchen() {
        return kitchen;
    }

    void createItem(Item item) {
        itemRepo.createItem(item);
    }

    LiveData<Item> getItemLiveData() {
        return itemRepo.getItemLiveData();
    }
}
