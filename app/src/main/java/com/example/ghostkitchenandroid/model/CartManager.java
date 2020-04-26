package com.example.ghostkitchenandroid.model;

import java.util.HashMap;

import androidx.annotation.NonNull;

public class CartManager {

    private HashMap<Long, Cart> carts; //the Long key will be the kitchenId of the kitchen associated with this cart

    public CartManager() {
        carts = new HashMap<>();
    }

    /**
     * This method checks our list of managed carts to see if a cart exists for the given kitchen passed to this method.
     * If the cart exists it is returned. If a cart does not exist for this kitchen then a new cart is created, added
     * to our list of managed carts, then returned.
     * @param kitchen The kitchen ID will be used for mapping to a cart, or for returning a cart if one already exists.
     * @return
     */
    @NonNull
    public Cart getCart(Kitchen kitchen) {
        Cart cart = carts.get(kitchen.getId());

        if (cart == null) {
            cart = new Cart();
            carts.put(kitchen.getId(), cart);
        }

        return cart;
    }

    public boolean containsCartFor(Kitchen kitchen) {
        return carts.containsKey(kitchen.getId());
    }

    public void deleteCartForKitchen(Kitchen kitchen) {
        carts.remove(kitchen.getId());
    }

}
