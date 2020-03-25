package com.example.ghostkitchenandroid.network.item;

import com.example.ghostkitchenandroid.network.RetrofitClientInstance;

abstract class ItemServiceInstance {

    private static ItemService itemService;

    public static ItemService getInstance() {
        if (itemService == null)
            itemService = RetrofitClientInstance.getInstance().create(ItemService.class);
        return itemService;
    }
}
