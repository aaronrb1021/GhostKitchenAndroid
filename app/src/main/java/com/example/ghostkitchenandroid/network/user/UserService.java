package com.example.ghostkitchenandroid.network.user;

import com.example.ghostkitchenandroid.data.model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

interface UserService {
    @POST("/users")
    Call<User> createUser(@Body User user);
}
