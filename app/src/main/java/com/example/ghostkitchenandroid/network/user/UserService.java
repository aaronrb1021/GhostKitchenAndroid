package com.example.ghostkitchenandroid.network.user;

import com.example.ghostkitchenandroid.data.model.User;
import com.example.ghostkitchenandroid.network.Result;
import com.example.ghostkitchenandroid.network.ResultWithData;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

interface UserService {
    @POST("/users")
    Call<User> createUser(@Body User user);

    @POST("/users/auth")
    Call<ResultWithData<User>> getUser(@Body User user);
}
