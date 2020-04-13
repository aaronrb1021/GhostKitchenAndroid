package com.example.ghostkitchenandroid.ui.customer;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;

import com.example.ghostkitchenandroid.R;
import com.example.ghostkitchenandroid.model.Item;
import com.example.ghostkitchenandroid.utils.PriceFormat;
import com.google.android.material.textfield.TextInputEditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public abstract class CustomerItemDialog extends DialogFragment {

    protected Item item;
    private TextView itemNameTv, itemPriceTv, totalPriceTv;
    protected TextInputEditText quantityEt;
    protected SingleArgSubmittable<Integer> singleArgSubmittable;

    public CustomerItemDialog(Item item, SingleArgSubmittable<Integer> singleArgSubmittable) {
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
                .setTitle(createTitle());

        initViews(view);
        config();


        return finalizeBuilderAndViews(builder).create();
    }

    /**
     * Allows a subclass to implement any extra buttons on the builder. Also, the views are initialized by the time this method
     * is called so, within this method, you can edit the text visible in views.
     * @param builder The builder with already implemented basic functionality
     * @return the same builder with any additions
     */
    protected abstract AlertDialog.Builder finalizeBuilderAndViews(AlertDialog.Builder builder);

    /**
     * Check the quantity and return an error if one exists. If not, return null to indicate there is no error.
     * @param quantity Integer quantity the user entered
     * @return an error message to be set on the EditText, or null if no error.
     */
    protected abstract String getQuantityError(int quantity); //return String error, or null if no error

    protected abstract String createTitle();

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
                    String error = getQuantityError(quantityEntered);
                    if (error != null)
                        quantityEt.setError(error);
                    else {//no error, all others above are errors
                        totalPriceTv.setText(PriceFormat.getFormattedTotal(quantityEntered, item.getPrice()));
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        quantityEt.requestFocus();
    }
}
