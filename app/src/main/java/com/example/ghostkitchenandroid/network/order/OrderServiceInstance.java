package com.example.ghostkitchenandroid.network.order;

import com.example.ghostkitchenandroid.network.RetrofitClientInstance;

public abstract class OrderServiceInstance {

    private static OrderService orderService;

    static OrderService getInstance() {
        if (orderService == null)
            orderService = RetrofitClientInstance.getInstance().create(OrderService.class);

        return orderService;
    }
}
