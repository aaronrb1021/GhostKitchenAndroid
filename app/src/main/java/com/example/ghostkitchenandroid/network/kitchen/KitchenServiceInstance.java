package com.example.ghostkitchenandroid.network.kitchen;

import com.example.ghostkitchenandroid.network.RetrofitClientInstance;

abstract class KitchenServiceInstance {

    private static KitchenService kitchenService;

    public static KitchenService getInstance() {
        if (kitchenService == null)
            kitchenService = RetrofitClientInstance.getInstance().create(KitchenService.class);
        return kitchenService;
    }
}
