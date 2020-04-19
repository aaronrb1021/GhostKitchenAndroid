package com.example.ghostkitchenandroid.ui.customer;

import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ghostkitchenandroid.R;
import com.example.ghostkitchenandroid.model.BuyerDetails;
import com.example.ghostkitchenandroid.model.Cart;
import com.example.ghostkitchenandroid.model.Kitchen;
import com.example.ghostkitchenandroid.model.Order;
import com.example.ghostkitchenandroid.model.OrderBuilder;
import com.example.ghostkitchenandroid.model.User;
import com.example.ghostkitchenandroid.network.user.UserRepo;
import com.example.ghostkitchenandroid.utils.PriceFormat;
import com.google.android.material.chip.Chip;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Date;

public class CheckoutFragment extends Fragment {

    private CheckoutViewModel checkoutViewModel;
    private Chip deliveryChip, pickupChip, cashChip, creditChip;
    private TextInputEditText pickupNameEt;
    private View pickupLayout;
    private Spinner pickupSpinner, deliverySpinner;
    private Button placeOrderBt;
    private TextView subtotalView, taxView, deliveryFeeView, totalView;

    public static CheckoutFragment newInstance(Cart cart, Kitchen kitchen) {
        CheckoutFragment checkoutFragment = new CheckoutFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("cart", cart);
        bundle.putSerializable("kitchen", kitchen);
        checkoutFragment.setArguments(bundle);
        return checkoutFragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.checkout_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        checkoutViewModel = new ViewModelProvider(this).get(CheckoutViewModel.class);

        Bundle bundle = getArguments();
        if (bundle != null) {
            checkoutViewModel.setCart((Cart) bundle.get("cart"));
            checkoutViewModel.setKitchen((Kitchen) bundle.get("kitchen"));
        }

        checkoutViewModel.initTimeLists(System.currentTimeMillis());

        initViews(view);
        configViews();
        configChips();
        configSpinners();
        configButton();
        configWatchers();
        setObservance();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void initViews(View view) {
        deliveryChip = view.findViewById(R.id.checkout_chip_delivery);
        pickupChip = view.findViewById(R.id.checkout_chip_pickup);
        pickupLayout = view.findViewById(R.id.checkout_pickup_layout);
        pickupNameEt = view.findViewById(R.id.checkout_pickup_name_et);
        cashChip = view.findViewById(R.id.checkout_chip_cash);
        creditChip = view.findViewById(R.id.checkout_chip_credit);
        pickupSpinner = view.findViewById(R.id.checkout_pickup_time_spinner);
        placeOrderBt = view.findViewById(R.id.checkout_order_button);
        taxView = view.findViewById(R.id.checkout_tax_price);
        subtotalView = view.findViewById(R.id.checkout_subtotal_price);
        deliveryFeeView = view.findViewById(R.id.checkout_delivery_price);
        totalView = view.findViewById(R.id.checkout_total_price);
//        deliverySpinner = view.findViewById(R.id.checkout_del)TODO delivery spinner
    }

    private void configViews() {
        subtotalView.setText(checkoutViewModel.getCart().getSubtotalString());
        taxView.setText(checkoutViewModel.getCart().getSalesTaxString());

        double subtotalPlus = checkoutViewModel.getCart().getSubtotal() + checkoutViewModel.getCart().getSalesTax();

        if (deliveryChip.isChecked()) {
            deliveryFeeView.setText("$2.95");
            subtotalPlus += 2.95;
        } else {
            deliveryFeeView.setText("N/A");
        }

        totalView.setText(PriceFormat.getFormattedPrice(subtotalPlus));
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

    private void configSpinners() {
        pickupSpinner.setAdapter(new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, checkoutViewModel.getPickupTimes()));
    }

    private void chipSelectSwap(Chip oldChip, Chip newChip, int oldChipDrawable, int newChipDrawable) {
        oldChip.setChecked(false);
        newChip.setChipIcon(getActivity().getDrawable(newChipDrawable));
        oldChip.setChipIcon(getActivity().getDrawable(oldChipDrawable));
        newChip.setChipStrokeColorResource(R.color.colorPrimary);
        oldChip.setChipStrokeColorResource(R.color.colorPrimaryDark);
        updateOrderButton();
        configViews();
    }

    private void configWatchers() {
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                updateOrderButton();
            }
        };
        pickupNameEt.addTextChangedListener(textWatcher);
    }

    private void configButton() {
        placeOrderBt.setOnClickListener(v -> {
            User currentUser = UserRepo.getLoggedInUser();
            OrderBuilder orderBuilder = OrderBuilder.forPickup();
            Order order = orderBuilder
                    .setCart(checkoutViewModel.getCart())
                    .setBuyerDetails(new BuyerDetails(currentUser.getId(), currentUser.getFullName(), currentUser.getPhoneNumber()))
                    .setTaxFee(checkoutViewModel.getCart().getSalesTax())
                    .setKitchen(checkoutViewModel.getKitchen())
                    .setPickupName(pickupNameEt.getText().toString().trim())
                    .create();

            checkoutViewModel.createOrder(order);
        });
    }

    private void updateOrderButton() {
        if (pickupChip.isChecked()) {
            if (pickupNameEt.getText().toString().trim().length() > 0)
                placeOrderBt.setEnabled(true);
            else
                placeOrderBt.setEnabled(false);
            return;
        } else {
            placeOrderBt.setEnabled(false);
            //TODO check delivery form data
        }
    }

    private void setObservance() {
        checkoutViewModel.getOrderLiveData().observe(getViewLifecycleOwner(), order -> {
            if (order == null) {
                Toast.makeText(getContext(), "Order failed", Toast.LENGTH_SHORT).show();
                return;
            }

            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder
                    .setTitle("Order success!")
                    .setMessage("Order ID: " + order.getId() + "\nTime submitted: " + new Date(System.currentTimeMillis()).toString())
                    .setPositiveButton(R.string.submit, (DialogInterface dialog, int which) -> {
                        finish();
                    })
                    .create()
                    .show();
        });
    }

    private void finish() {
        getActivity().finish();
    }

}
