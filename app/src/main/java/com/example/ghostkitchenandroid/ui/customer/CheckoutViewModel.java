package com.example.ghostkitchenandroid.ui.customer;

import com.example.ghostkitchenandroid.model.Cart;

import androidx.lifecycle.ViewModel;

public class CheckoutViewModel extends ViewModel {

    private Cart cart;

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }
}
