package com.example.ghostkitchenandroid.ui.customer;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ghostkitchenandroid.R;
import com.example.ghostkitchenandroid.model.Item;
import com.example.ghostkitchenandroid.ui.lists.CustomerItemListViewModel;
import com.example.ghostkitchenandroid.utils.PriceFormat;
import com.google.android.material.textfield.TextInputEditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class CartAddItemDialog extends DialogFragment {

    private Item item;
    private TextView itemNameTv, itemPriceTv, totalPriceTv;
    private TextInputEditText quantityEt;
    private SingleArgSubmittable<Integer> singleArgSubmittable;

    public CartAddItemDialog(Item item, SingleArgSubmittable<Integer> singleArgSubmittable) {
        this.item = item;
        this.singleArgSubmittable = singleArgSubmittable;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return createDialog();
    }

    private Dialog createDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = requireActivity().getLayoutInflater().inflate(R.layout.dialog_cart_add, null);

        builder.setView(view)
                .setPositiveButton(R.string.submit, (DialogInterface dialog, int which) -> {
                    if (quantityEt.getError() == null)
                        singleArgSubmittable.submit(Integer.parseInt(quantityEt.getText().toString()));
                })
                .setNegativeButton(android.R.string.cancel, (dialog, which) -> dismiss())
                .setTitle(R.string.add_to_cart_dialog_title);

        initViews(view);
        config();


        return builder.create();
    }

    private void initViews(View view) {
        itemNameTv = view.findViewById(R.id.cart_dialog_add_item_name);
        itemPriceTv = view.findViewById(R.id.cart_dialog_add_item_price);
        totalPriceTv = view.findViewById(R.id.cart_dialog_add_item_total_price);
        quantityEt = view.findViewById(R.id.cart_dialog_add_quantity);
    }

    private void config() {
        itemNameTv.setText(item.getName());
        itemPriceTv.setText(item.getPriceString());
        totalPriceTv.setText(item.getPriceString());

        quantityEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String enteredString = s.toString();
                if (enteredString.length() < 1) {
                    quantityEt.setError(getString(R.string.required));
                } else {
                    int quantityEntered = Integer.parseInt(enteredString);
                    if (quantityEntered < 1) {
                        quantityEt.setError("Must be greater than 0");
                    } else if (quantityEntered > 50) {
                        quantityEt.setError("Must be less than 50");
                    } else {//no error, all others above are errors
                        totalPriceTv.setText(PriceFormat.getFormattedTotal(quantityEntered, item.getPrice()));
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
