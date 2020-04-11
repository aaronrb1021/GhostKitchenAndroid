package com.example.ghostkitchenandroid.ui.lists;

import com.example.ghostkitchenandroid.model.Cart;
import com.example.ghostkitchenandroid.model.Item;
import com.example.ghostkitchenandroid.model.Kitchen;
import com.example.ghostkitchenandroid.network.user.UserRepo;

import androidx.lifecycle.ViewModel;

public class CustomerItemListViewModel extends AbstractItemListViewModel {

    private Cart cart;

    @Override //override to intialize our cart object after the kitchen is known and set
    public void setKitchen(Kitchen kitchen) {
        super.setKitchen(kitchen);
        cart = UserRepo.getLoggedInUser().getCartManager().getCart(kitchen);
    }

    public Cart getCart() {
        return cart;
    }
}
