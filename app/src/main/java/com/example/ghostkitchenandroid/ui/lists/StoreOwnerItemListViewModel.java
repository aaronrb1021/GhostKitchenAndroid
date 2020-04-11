package com.example.ghostkitchenandroid.ui.lists;

import com.example.ghostkitchenandroid.model.Item;

import androidx.lifecycle.LiveData;

public final class StoreOwnerItemListViewModel extends AbstractItemListViewModel {

    public LiveData<Item> getItemLiveData() {
        return itemRepo.getItemLiveData();
    }

    public LiveData<Boolean> getDeleteItemLiveData() {
        return itemRepo.getDeleteItemLiveData();
    }

    public void createItem(Item item) {
        itemRepo.createItem(item);
    }

    public void deleteItem(Item item) {
        itemRepo.deleteItem(item);
    }
}
