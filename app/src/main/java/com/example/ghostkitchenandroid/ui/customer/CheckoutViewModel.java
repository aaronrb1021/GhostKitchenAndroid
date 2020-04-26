package com.example.ghostkitchenandroid.ui.customer;

import com.example.ghostkitchenandroid.model.Cart;
import com.example.ghostkitchenandroid.model.Kitchen;
import com.example.ghostkitchenandroid.model.Order;
import com.example.ghostkitchenandroid.network.order.OrderRepo;
import com.example.ghostkitchenandroid.network.user.UserRepo;
import com.example.ghostkitchenandroid.utils.ListGenerator;

import java.util.ArrayList;
import java.util.Date;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class CheckoutViewModel extends ViewModel {

    private OrderRepo orderRepo = OrderRepo.getInstance();
    private Cart cart;
    private Kitchen kitchen;
    private ArrayList<String> pickupTimes;
    private ArrayList<String> deliveryTimes;

    Cart getCart() {
        return cart;
    }

    void setCart(Cart cart) {
        this.cart = cart;
    }

    public Kitchen getKitchen() {
        return kitchen;
    }

    public void setKitchen(Kitchen kitchen) {
        this.kitchen = kitchen;
    }

    LiveData<Order> getOrderLiveData() {
        return orderRepo.getOrderLiveData();
    }

    ArrayList<String> getPickupTimes() {
        return pickupTimes;
    }

    ArrayList<String> getDeliveryTimes() {
        return deliveryTimes;
    }

    public void initTimeLists(long fromTimeInMillis) {
        pickupTimes = ListGenerator.makePickupTimes(fromTimeInMillis);
    }

    void createOrder(Order order) {
        orderRepo.createOrder(order);
        UserRepo.getLoggedInUser().getCartManager().deleteCartForKitchen(kitchen);
    }

}
