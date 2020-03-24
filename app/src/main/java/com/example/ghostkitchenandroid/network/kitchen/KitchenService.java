package com.example.ghostkitchenandroid.network.kitchen;

import com.example.ghostkitchenandroid.model.Kitchen;
import com.example.ghostkitchenandroid.model.User;
import com.example.ghostkitchenandroid.network.advice.DualObjectWrapper;
import com.example.ghostkitchenandroid.network.advice.ResultWithData;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

interface KitchenService {

    @POST("/kitchens")
    Call<ResultWithData<Kitchen>> addKitchen(@Body DualObjectWrapper<Kitchen, User> dualObjectWrapper);

    @POST("/kitchens/user")
    Call<ArrayList<Kitchen>> getKitchensByUser(@Body User user);
}
