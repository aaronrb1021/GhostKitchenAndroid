package com.example.ghostkitchenandroid.ui.customer;

import android.app.AlertDialog;

import com.example.ghostkitchenandroid.R;
import com.example.ghostkitchenandroid.model.Item;

public class CartAddItemDialog extends CustomerItemDialog {

    public CartAddItemDialog(Item item, SingleArgSubmittable<Integer> singleArgSubmittable) {
        super(item, singleArgSubmittable);
    }

    @Override
    protected AlertDialog.Builder finalizeBuilderAndViews(AlertDialog.Builder builder) {
        return builder;
    }

    @Override
    protected String getQuantityError(int quantity) {
        if (quantity < 1) {
            return "Must be greater than 0";
        } else if (quantity > 50) {
            return "Must be less than 50";
        }
        return null;
    }

    @Override
    protected String createTitle() {
        return getString(R.string.add_to_cart_dialog_title);
    }
}
