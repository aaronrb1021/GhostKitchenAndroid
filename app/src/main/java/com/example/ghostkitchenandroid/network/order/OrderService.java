package com.example.ghostkitchenandroid.network.order;

import com.example.ghostkitchenandroid.model.Order;
import com.example.ghostkitchenandroid.model.StoreOwnerOrderOverview;
import com.example.ghostkitchenandroid.model.User;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface OrderService {

    @POST("/orders")
    Call<Order> createOrder(@Body Order order);

    @GET("/orders/user/{userId}")
    Call<ArrayList<Order>> fetchOrdersByUser(@Path(value = "userId") String userId);

    @GET("/orders/kitchen/{kitchenId}")
    Call<ArrayList<Order>> fetchOrdersByKitchen(@Path(value = "kitchenId") String kitchenId);

    @POST("/orders/storeOwner")
    Call<StoreOwnerOrderOverview> fetchOrdersByStoreOwner(@Body User user);
}
