package com.example.ghostkitchenandroid.network.user_address;

import com.example.ghostkitchenandroid.network.RetrofitClientInstance;

public abstract class UserAddressServiceInstance {
    private static UserAddressService userAddressService;

    public static UserAddressService getInstance() {
        if (userAddressService == null)
            userAddressService = RetrofitClientInstance.getInstance().create(UserAddressService.class);
        return userAddressService;
    }
}
