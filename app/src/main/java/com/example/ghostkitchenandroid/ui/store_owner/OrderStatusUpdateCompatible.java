package com.example.ghostkitchenandroid.ui.store_owner;

import com.example.ghostkitchenandroid.model.Order;

public interface OrderStatusUpdateCompatible {
    void updateOrder(Order order);
    void refreshOrders();
}
