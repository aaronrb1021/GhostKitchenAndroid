package com.example.ghostkitchenandroid.network.kitchen;

import com.example.ghostkitchenandroid.model.Kitchen;
import com.example.ghostkitchenandroid.model.User;
import com.example.ghostkitchenandroid.network.advice.DualObjectWrapper;
import com.example.ghostkitchenandroid.network.advice.ResultWithData;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

interface KitchenService {

    @POST("/kitchens")
    Call<ResultWithData<Kitchen>> addKitchen(@Body DualObjectWrapper<Kitchen, User> dualObjectWrapper);

    @POST("/kitchens/user")
    Call<ArrayList<Kitchen>> getKitchensByUser(@Body User user);

    @GET("/kitchens")
    Call<ArrayList<Kitchen>> getAllKitchens();

    @PUT("/kitchens")
    Call<Kitchen> updateKitchen(@Body Kitchen kitchen);

    @GET("/kitchens/{zip}/{distance}")
    Call<ArrayList<Kitchen>> getAllKitchensByDistanceFromZip(@Path(value = "zip") String zip, @Path(value = "distance") String distance);
}
