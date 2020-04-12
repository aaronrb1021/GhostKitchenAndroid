package com.example.ghostkitchenandroid.ui.customer;

import android.app.AlertDialog;

import com.example.ghostkitchenandroid.R;
import com.example.ghostkitchenandroid.model.Item;

public class CartEditItemDialog extends CustomerItemDialog {

    private int quantity;

    public CartEditItemDialog(Item item, int quantity, SingleArgSubmittable<Integer> singleArgSubmittable) {
        super(item, singleArgSubmittable);
        this.quantity = quantity;
    }

    @Override
    protected AlertDialog.Builder finalizeBuilderAndViews(AlertDialog.Builder builder) {
        quantityEt.setText(String.valueOf(quantity));

        return builder.setNeutralButton(R.string.delete_strong, ((dialog, which) -> {
            singleArgSubmittable.submit(0);
        }));
    }

    @Override
    protected String getQuantityError(int quantity) {

        if (quantity > 50) {
            return "Must be less than 50";
        }

        return null;
    }

    @Override
    protected String createTitle() {
        return getString(R.string.edit_cart_dialog_title);
    }
}
