package com.example.ghostkitchenandroid.ui.store_owner;

import com.example.ghostkitchenandroid.model.Order;
import com.example.ghostkitchenandroid.model.StoreOwnerOrderOverview;
import com.example.ghostkitchenandroid.model.User;
import com.example.ghostkitchenandroid.network.order.OrderRepo;
import com.example.ghostkitchenandroid.network.user.UserRepo;

import java.util.ArrayList;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class StoreOwnerOrdersViewModel extends ViewModel implements OrderStatusUpdateCompatible {

    private OrderRepo orderRepo = OrderRepo.getInstance();
    private StoreOwnerOrderOverview storeOwnerOrderOverview;

    public StoreOwnerOrderOverview getStoreOwnerOrderOverview() {
        return storeOwnerOrderOverview;
    }

    public void setStoreOwnerOrderOverview(StoreOwnerOrderOverview storeOwnerOrderOverview) {
        this.storeOwnerOrderOverview = storeOwnerOrderOverview;
    }

    LiveData<StoreOwnerOrderOverview> getStoreOwnerOrderOverviewLiveData() {
        return orderRepo.getStoreOwnerOrderOverviewLiveData();
    }

    LiveData<Order> getOrderLiveData() {
        return orderRepo.getOrderLiveData();
    }

    void fetchStoreOwnerOrderOverview(User user) {
        orderRepo.fetchOrdersByStoreOwner(user);
    }

    @Override
    public void updateOrder(Order order) {
        orderRepo.createOrder(order);
    }

    @Override
    public void refreshOrders() {
        fetchStoreOwnerOrderOverview(UserRepo.getLoggedInUser());
    }
}
