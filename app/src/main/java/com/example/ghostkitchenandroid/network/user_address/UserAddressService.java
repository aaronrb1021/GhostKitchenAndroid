package com.example.ghostkitchenandroid.network.user_address;

import com.example.ghostkitchenandroid.model.UserAddress;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserAddressService {

    @POST("/addresses/users")
    Call<UserAddress> saveUserAddress(@Body UserAddress userAddress);

    @GET("/addresses/users/{userId}")
    Call<ArrayList<UserAddress>> fetchUserAddressesByUserId(@Path(value = "userId") String userId);

}
