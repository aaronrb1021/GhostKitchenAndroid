package com.example.ghostkitchenandroid.ui.customer;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.ghostkitchenandroid.R;
import com.example.ghostkitchenandroid.model.State;
import com.example.ghostkitchenandroid.model.UserAddress;
import com.example.ghostkitchenandroid.network.user.UserRepo;
import com.example.ghostkitchenandroid.utils.Format;
import com.example.ghostkitchenandroid.utils.UserCredValidity;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Arrays;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class CreateDeliveryAddressDialog extends DialogFragment {

    private CheckoutViewModel checkoutViewModel;
    private TextInputEditText name, address1, address2, city, zip, phone;
    private Spinner state;

    public CreateDeliveryAddressDialog(CheckoutViewModel checkoutViewModel) {
        this.checkoutViewModel = checkoutViewModel;
    }

    @Override
    public void onResume() {
        super.onResume();

        final AlertDialog alertDialog = (AlertDialog) getDialog();
        if (alertDialog != null) {
            alertDialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(v -> {
                if (verifyData()) {
                    checkoutViewModel.createUserAddress(new UserAddress(
                            UserRepo.getLoggedInUser(),
                            name.getText().toString().trim(),
                            address1.getText().toString().trim(),
                            address2.getText().toString().trim(),
                            city.getText().toString().trim(),
                            state.getSelectedItem().toString(),
                            zip.getText().toString().trim(),
                            phone.getText().toString().trim()
                    ));
                    alertDialog.dismiss();
                }
            });
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        final View view = inflater.inflate(R.layout.dialog_create_user_address, null);

        name = view.findViewById(R.id.create_user_address_name_et);
        address1 = view.findViewById(R.id.create_user_address_address1_et);
        address2 = view.findViewById(R.id.create_user_address_address2_et);
        city = view.findViewById(R.id.create_user_address_city_et);
        state = view.findViewById(R.id.create_user_address_state_spinner);
        zip = view.findViewById(R.id.create_user_address_zip_et);
        phone = view.findViewById(R.id.create_user_address_phone_et);

        state.setAdapter(new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, State.values()));

        builder.setPositiveButton(R.string.submit, null) //onclick set in onResume
                .setNegativeButton(android.R.string.cancel, (dialog, which) -> {
                    dialog.dismiss();
                })
                .setView(view);


        return builder.create();
    }

    private boolean verifyData() {
        if (name.getText().toString().trim().length() == 0) {
            return setRequiredError(name);
        } else if (address1.getText().toString().trim().length() == 0) {
            return setRequiredError(address1);
        } else if (city.getText().toString().trim().length() == 0) {
            return setRequiredError(city);
        } else if (state.getSelectedItem().toString().equalsIgnoreCase("Select State")) {
            ((TextView)state.getSelectedView()).setError(getString(R.string.required));
            return false;
        } else if (!UserCredValidity.isZipValid(zip.getText().toString().trim())) {
            return setRequiredError(zip);
        } else if (!UserCredValidity.isPhoneValid(phone.getText().toString().trim())) {
            return setRequiredError(phone);
        }
        return true;
    }

    private boolean setRequiredError(TextInputEditText editText) {
        editText.setError(getString(R.string.required));
        return false;
    }
}
