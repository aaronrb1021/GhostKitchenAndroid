package com.example.ghostkitchenandroid.ui.customer;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ghostkitchenandroid.R;
import com.example.ghostkitchenandroid.model.Kitchen;
import com.example.ghostkitchenandroid.model.KitchenAddress;
import com.google.android.material.textfield.TextInputEditText;

public class CustomerKitchenInfoFragment extends Fragment {

    public static final String ARG_PARAM1 = "kitchen";

    private TextInputEditText name, address, phone;
    private Kitchen kitchen;

    public static CustomerKitchenInfoFragment newInstance(Kitchen kitchen) {
        CustomerKitchenInfoFragment fragment = new CustomerKitchenInfoFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, kitchen);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            kitchen = (Kitchen) getArguments().get(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_customer_kitchen_info, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        configViews();
    }

    private void initViews(View view) {
        name = view.findViewById(R.id.customer_kitchen_info_name_et);
        address = view.findViewById(R.id.customer_kitchen_info_address_et);
        phone = view.findViewById(R.id.customer_kitchen_info_phone_et);
    }

    private void configViews() {
        name.setText(kitchen.getName());
        address.setText(kitchen.getKitchenAddress().toString());
        phone.setText(kitchen.getKitchenAddress().getPhone());
    }
}
