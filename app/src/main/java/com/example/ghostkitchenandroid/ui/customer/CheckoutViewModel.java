package com.example.ghostkitchenandroid.ui.customer;

import com.example.ghostkitchenandroid.model.Cart;
import com.example.ghostkitchenandroid.model.Kitchen;
import com.example.ghostkitchenandroid.model.Order;
import com.example.ghostkitchenandroid.model.User;
import com.example.ghostkitchenandroid.model.UserAddress;
import com.example.ghostkitchenandroid.network.order.OrderRepo;
import com.example.ghostkitchenandroid.network.user.UserRepo;
import com.example.ghostkitchenandroid.network.user_address.UserAddressRepo;
import com.example.ghostkitchenandroid.utils.ListGenerator;

import java.util.ArrayList;
import java.util.Date;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class CheckoutViewModel extends ViewModel {

    private OrderRepo orderRepo = OrderRepo.getInstance();
    private UserAddressRepo userAddressRepo = UserAddressRepo.newInstance();
    private UserAddress selectedUserAddress;
    private Cart cart;
    private Kitchen kitchen;
    private ArrayList<String> pickupTimes;
    private ArrayList<String> deliveryTimes;
    private ArrayList<UserAddress> userAddresses;

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

    public ArrayList<UserAddress> getUserAddresses() {
        return userAddresses;
    }

    public void setUserAddresses(ArrayList<UserAddress> userAddresses) {
        this.userAddresses = userAddresses;
    }

    public UserAddress getSelectedUserAddress() {
        return selectedUserAddress;
    }

    public void setSelectedUserAddress(UserAddress selectedUserAddress) {
        this.selectedUserAddress = selectedUserAddress;
    }

    LiveData<ArrayList<UserAddress>> getUserAddressListLiveData() {
        return userAddressRepo.getUserAddressListLiveData();
    }

    LiveData<UserAddress> getUserAddressLiveData() {
        return userAddressRepo.getUserAddressLiveData();
    }

    void createUserAddress(UserAddress userAddress) {
        userAddressRepo.saveUserAddress(userAddress);
    }

    void fetchUserAddresses(User user) {
        userAddressRepo.fetchUserAddressesByUser(user);
    }

    ArrayList<String> getPickupTimes() {
        return pickupTimes;
    }

    ArrayList<String> getDeliveryTimes() {
        return deliveryTimes;
    }

    public void initTimeLists(long fromTimeInMillis) {
        pickupTimes = ListGenerator.makePickupTimes(fromTimeInMillis);
        deliveryTimes = ListGenerator.makeDeliveryTimes(fromTimeInMillis);
    }

    void createOrder(Order order) {
        orderRepo.createOrder(order);
        UserRepo.getLoggedInUser().getCartManager().deleteCartForKitchen(kitchen);
    }

}
