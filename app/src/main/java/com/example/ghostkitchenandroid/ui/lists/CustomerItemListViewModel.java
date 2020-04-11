package com.example.ghostkitchenandroid.ui.lists;

import com.example.ghostkitchenandroid.model.Cart;
import com.example.ghostkitchenandroid.network.user.UserRepo;

import androidx.lifecycle.ViewModel;

public class CustomerItemListViewModel extends AbstractItemListViewModel {

    private Cart cart = UserRepo.getLoggedInUser().getCartManager().getCart(kitchen);


}
