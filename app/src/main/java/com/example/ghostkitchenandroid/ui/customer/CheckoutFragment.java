package com.example.ghostkitchenandroid.ui.customer;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ghostkitchenandroid.R;
import com.example.ghostkitchenandroid.model.Cart;
import com.google.android.material.chip.Chip;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class CheckoutFragment extends Fragment {

    private CheckoutViewModel checkoutViewModel;
    private Chip deliveryChip, pickupChip, cashChip, creditChip;
    private TextInputEditText pickupNameEt;
    private View pickupLayout;

    public static CheckoutFragment newInstance(Cart cart) {
        CheckoutFragment checkoutFragment = new CheckoutFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("cart", cart);
        checkoutFragment.setArguments(bundle);
        return new CheckoutFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.checkout_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews(view);
        configChips();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        checkoutViewModel = new ViewModelProvider(this).get(CheckoutViewModel.class);

        Bundle bundle = getArguments();
        if (bundle != null)
            checkoutViewModel.setCart((Cart) bundle.get("cart"));
    }

    private void initViews(View view) {
        deliveryChip = view.findViewById(R.id.checkout_chip_delivery);
        pickupChip = view.findViewById(R.id.checkout_chip_pickup);
        pickupLayout = view.findViewById(R.id.checkout_pickup_layout);
        pickupNameEt = view.findViewById(R.id.checkout_pickup_name_et);
        cashChip = view.findViewById(R.id.checkout_chip_cash);
        creditChip = view.findViewById(R.id.checkout_chip_credit);
    }

    private void configChips() {
        deliveryChip.setOnClickListener(v -> {
            chipSelectSwap(pickupChip, deliveryChip, R.drawable.ic_directions_walk_black_24dp, R.drawable.ic_directions_car_primary_green_24dp);
            pickupLayout.setVisibility(View.INVISIBLE);
        });
        pickupChip.setOnClickListener(v -> {
            chipSelectSwap(deliveryChip, pickupChip, R.drawable.ic_directions_car_black_24dp, R.drawable.ic_directions_walk_primary_green_24dp);
            pickupLayout.setVisibility(View.VISIBLE);
        });
        cashChip.setOnClickListener(v -> {
            chipSelectSwap(creditChip, cashChip, R.drawable.ic_credit_card_black_24dp, R.drawable.ic_attach_money_primary_green_24dp);
        });
        creditChip.setOnClickListener(v -> {
            chipSelectSwap(cashChip, creditChip, R.drawable.ic_attach_money_black_24dp, R.drawable.ic_credit_card_primary_green_24dp);
        });
    }

    private void chipSelectSwap(Chip oldChip, Chip newChip, int oldChipDrawable, int newChipDrawable) {
        oldChip.setChecked(false);
        newChip.setChipIcon(getActivity().getDrawable(newChipDrawable));
        oldChip.setChipIcon(getActivity().getDrawable(oldChipDrawable));
        newChip.setChipStrokeColorResource(R.color.colorPrimary);
        oldChip.setChipStrokeColorResource(R.color.colorPrimaryDark);
    }

}
