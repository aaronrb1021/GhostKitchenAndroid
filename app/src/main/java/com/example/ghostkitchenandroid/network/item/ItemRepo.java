package com.example.ghostkitchenandroid.network.item;

import android.os.AsyncTask;

import com.example.ghostkitchenandroid.model.Item;
import com.example.ghostkitchenandroid.model.Kitchen;

import java.io.IOException;
import java.util.ArrayList;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class ItemRepo {

    private static ItemService itemService = ItemServiceInstance.getInstance();

    private final MutableLiveData<Item> itemLiveData = new MutableLiveData<>();
    private final MutableLiveData<ArrayList<Item>> itemListLiveData = new MutableLiveData<>();

    public void createItem(Item item) {
        new CreateItemTask(itemLiveData).execute(item);
    }

    public void getItemsByKitchen(Kitchen kitchen) {
        new GetItemsByKitchenTask(itemListLiveData).execute(kitchen);
    }

    public LiveData<Item> getItemLiveData() {
        return itemLiveData;
    }

    public LiveData<ArrayList<Item>> getItemListLiveData() {
        return itemListLiveData;
    }

    private static class CreateItemTask extends AsyncTask<Item, Void, Item> {

        MutableLiveData<Item> itemLiveData;

        CreateItemTask(MutableLiveData itemLiveData) {
            this.itemLiveData = itemLiveData;
        }

        @Override
        protected Item doInBackground(Item... items) {
            try {
                return itemService.createItem(items[0]).execute().body();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Item item) {
            itemLiveData.setValue(item);
            itemLiveData = null;
        }
    }

    private static class GetItemsByKitchenTask extends AsyncTask<Kitchen, Void, ArrayList<Item>> {

        MutableLiveData<ArrayList<Item>> itemListLiveData;

        GetItemsByKitchenTask(MutableLiveData itemListLiveData) {
            this.itemListLiveData = itemListLiveData;
        }

        @Override
        protected ArrayList<Item> doInBackground(Kitchen... kitchens) {
            try {
                return itemService.findItemsByKitchen(kitchens[0]).execute().body();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<Item> items) {
            itemListLiveData.setValue(items);
            itemListLiveData = null;
        }
    }
}
