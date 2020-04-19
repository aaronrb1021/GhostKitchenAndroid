package com.example.ghostkitchenandroid.network.order;

import com.example.ghostkitchenandroid.model.Order;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface OrderService {

    @POST("/orders")
    Call<Order> createOrder(@Body Order order);
}
