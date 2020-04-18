package com.example.ghostkitchenandroid.ui.lists;

import com.example.ghostkitchenandroid.model.Item;
import com.example.ghostkitchenandroid.model.Kitchen;
import com.example.ghostkitchenandroid.model.KitchenMenu;
import com.example.ghostkitchenandroid.network.item.ItemRepo;

import java.util.ArrayList;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public abstract class AbstractItemListViewModel extends ViewModel {

    private Kitchen kitchen;
    protected KitchenMenu kitchenMenu;
    protected ItemRepo itemRepo = new ItemRepo();

    public LiveData<ArrayList<Item>> getItemListLiveData() {
        return itemRepo.getItemListLiveData();
    }

    public void updateItemListLiveData() {
        itemRepo.getItemsByKitchen(kitchen);
    }

    public void setKitchen(Kitchen kitchen) {
        this.kitchen = kitchen;
    }

    public Kitchen getKitchen() {
        return kitchen;
    }

    public KitchenMenu getKitchenMenu() {
        return kitchenMenu;
    }

    public void setKitchenMenu(KitchenMenu kitchenMenu) {
        this.kitchenMenu = kitchenMenu;
    }

}
