package com.example.ghostkitchenandroid.ui.store_owner;

import android.util.Log;

import com.example.ghostkitchenandroid.model.Item;
import com.example.ghostkitchenandroid.model.Kitchen;
import com.example.ghostkitchenandroid.model.Order;
import com.example.ghostkitchenandroid.network.item.ItemRepo;
import com.example.ghostkitchenandroid.network.order.OrderRepo;

import java.util.ArrayList;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class MyKitchenViewModel extends ViewModel {

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

    void fetchOrders() {
        orderRepo.fetchOrdersByKitchen(kitchen);
    }

    void updateOrder(Order order) {
        orderRepo.createOrder(order);
    }

}
