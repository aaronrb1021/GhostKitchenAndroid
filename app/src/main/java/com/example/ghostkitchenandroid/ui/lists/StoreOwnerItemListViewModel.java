package com.example.ghostkitchenandroid.ui.lists;

import com.example.ghostkitchenandroid.model.Item;
import com.example.ghostkitchenandroid.model.Kitchen;
import com.example.ghostkitchenandroid.model.KitchenMenu;
import com.example.ghostkitchenandroid.network.item.ItemRepo;

import java.util.ArrayList;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class StoreOwnerItemListViewModel extends ViewModel {

    private Kitchen kitchen;
    private KitchenMenu kitchenMenu;
    private ItemRepo itemRepo = new ItemRepo();

    public LiveData<ArrayList<Item>> getItemListLiveData() {
        return itemRepo.getItemListLiveData();
    }

    public LiveData<Item> getItemLiveData() {
        return itemRepo.getItemLiveData();
    }

    public LiveData<Boolean> getDeleteItemLiveData() {
        return itemRepo.getDeleteItemLiveData();
    }

    public void updateItemListLiveDataByKitchen(Kitchen kitchen) {
        itemRepo.getItemsByKitchen(kitchen);
    }

    public void createItem(Item item) {
        itemRepo.createItem(item);
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

    public void deleteItem(Item item) {
        itemRepo.deleteItem(item);
    }
}
