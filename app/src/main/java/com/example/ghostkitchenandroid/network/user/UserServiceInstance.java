package com.example.ghostkitchenandroid.network.user;

import com.example.ghostkitchenandroid.network.RetrofitClientInstance;

abstract class UserServiceInstance {

    private static UserService userService;

    public static UserService getInstance() {
        if (userService == null)
            userService = RetrofitClientInstance.getInstance().create(UserService.class);
        return userService;
    }
}
