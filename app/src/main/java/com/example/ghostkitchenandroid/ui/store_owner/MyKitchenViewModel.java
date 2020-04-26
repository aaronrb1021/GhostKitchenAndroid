package com.example.ghostkitchenandroid.ui.store_owner;

import com.example.ghostkitchenandroid.model.Kitchen;
import com.example.ghostkitchenandroid.model.Order;
import com.example.ghostkitchenandroid.network.order.OrderRepo;

import java.util.ArrayList;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class MyKitchenViewModel extends ViewModel implements OrderStatusUpdateCompatible {

    private Kitchen kitchen;
    private OrderRepo orderRepo = OrderRepo.getInstance();

    public void setKitchen(Kitchen kitchen) {
        this.kitchen = kitchen;
    }

    public Kitchen getKitchen() {
        return kitchen;
    }

    LiveData<ArrayList<Order>> getOrderListLiveData() {
        return orderRepo.getOrderListLiveData();
    }

    LiveData<Order> getOrderLiveData() {
        return orderRepo.getOrderLiveData();
    }

    @Override
    public void refreshOrders() {
        orderRepo.fetchOrdersByKitchen(kitchen);
    }

    @Override
    public void updateOrder(Order order) {
        orderRepo.createOrder(order);
    }

}
