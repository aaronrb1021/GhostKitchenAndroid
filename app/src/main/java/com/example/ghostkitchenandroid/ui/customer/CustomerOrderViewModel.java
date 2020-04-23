package com.example.ghostkitchenandroid.ui.customer;

import com.example.ghostkitchenandroid.model.Order;
import com.example.ghostkitchenandroid.network.order.OrderRepo;
import com.example.ghostkitchenandroid.network.user.UserRepo;

import java.util.ArrayList;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class CustomerOrderViewModel extends ViewModel {

    private OrderRepo orderRepo = OrderRepo.getInstance();

    void fetchOrders() {
        orderRepo.fetchOrdersByUser(UserRepo.getLoggedInUser());//todo
    }

    LiveData<ArrayList<Order>> getOrderListLiveData() {
        return orderRepo.getOrderListLiveData();
    }

}
