package com.example.ghostkitchenandroid.ui.store_owner;

import android.util.Log;

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
        Log.i("itemdataincreateitem", item.getPriceString() + item.getName() + item.getCategory() + item.getDescription());
        itemRepo.createItem(item);
    }

    LiveData<Item> getItemLiveData() {
        return itemRepo.getItemLiveData();
    }
}
