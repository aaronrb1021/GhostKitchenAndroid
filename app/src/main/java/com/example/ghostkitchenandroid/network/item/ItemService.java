package com.example.ghostkitchenandroid.network.item;

import com.example.ghostkitchenandroid.model.Item;
import com.example.ghostkitchenandroid.model.Kitchen;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

interface ItemService {

    @POST("items")
    Call<Item> createItem(@Body Item item);

    @POST("items/kitchen")
    Call<ArrayList<Item>> findItemsByKitchen(@Body Kitchen kitchen);
}
