package com.example.ghostkitchenandroid.ui.lists;

import com.example.ghostkitchenandroid.model.Item;
import com.example.ghostkitchenandroid.model.Kitchen;
import com.example.ghostkitchenandroid.network.item.ItemRepo;

import java.util.ArrayList;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class ItemListViewModel extends ViewModel {

    private Kitchen kitchen;
    private ItemRepo itemRepo = ItemRepo.getInstance();

    public LiveData<ArrayList<Item>> getItemListLiveData() {
        return itemRepo.getItemListLiveData();
    }

    public void updateItemListLiveDataByKitchen(Kitchen kitchen) {
        itemRepo.getItemsByKitchen(kitchen);
    }

    public void setKitchen(Kitchen kitchen) {
        this.kitchen = kitchen;
    }

    public Kitchen getKitchen() {
        return kitchen;
    }
}
