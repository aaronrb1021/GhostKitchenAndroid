package com.example.ghostkitchenandroid.network.user;

import com.example.ghostkitchenandroid.model.User;
import com.example.ghostkitchenandroid.network.advice.ResultWithData;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.PUT;

interface UserService {
    @POST("/users")
    Call<User> createUser(@Body User user);

    @POST("/users/auth")
    Call<ResultWithData<User>> getUser(@Body User user);

    @PUT("/users")
    Call<User> updateUser(@Body User user);
}
