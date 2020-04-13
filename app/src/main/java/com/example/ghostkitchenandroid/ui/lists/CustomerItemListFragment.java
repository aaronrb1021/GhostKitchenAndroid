package com.example.ghostkitchenandroid.ui.lists;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ghostkitchenandroid.R;
import com.example.ghostkitchenandroid.model.Item;
import com.example.ghostkitchenandroid.model.Kitchen;
import com.example.ghostkitchenandroid.model.KitchenMenu;
import com.example.ghostkitchenandroid.ui.customer.CartAddItemDialog;
import com.example.ghostkitchenandroid.ui.customer.CartEditItemDialog;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

public class CustomerItemListFragment extends Fragment {

    private CustomerItemListViewModel customerItemListViewModel;
    private RecyclerView recyclerView, cartRecycler;
    private View cartPreviewContainer, cartCheckoutArrowLayout;
    private TextView cartPreviewPrice, cartPreviewItemCount;
    private BottomSheetBehavior bottomSheetBehavior;
    private ImageView cartImage;

    public static CustomerItemListFragment newInstance(Kitchen kitchen) {
        CustomerItemListFragment customerItemListFragment = new CustomerItemListFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("kitchen", kitchen);
        customerItemListFragment.setArguments(bundle);
        return customerItemListFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        customerItemListViewModel = new ViewModelProvider(this).get(CustomerItemListViewModel.class);

        if (customerItemListViewModel.getKitchen() == null)
            customerItemListViewModel.setKitchen((Kitchen) getArguments().get("kitchen"));

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_customer_item_list, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews(view);

        checkShouldDisplayPreview();

        customerItemListViewModel.updateItemListLiveData();
        customerItemListViewModel.getItemListLiveData().observe(getViewLifecycleOwner(), items -> {
            recyclerView.setAdapter(new ItemListAdapter(getContext(), new KitchenMenu(items)) {
                @Override
                public void onItemCardClick(Item item) {
                    new CartAddItemDialog(item, quantity -> { //lambda called when user clicks dialog submit button
                        onAddToCartDialogSubmit(item, quantity);
                    }).show(getParentFragmentManager(), "CartAddItemDialog");
                }
            });
        });

        cartPreviewContainer.setOnClickListener(v -> {
            if (bottomSheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED)
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        });

        setBackBehavior();
    }

    private void initViews(View view) {
        recyclerView = view.findViewById(R.id.customer_item_list_recycler);
        cartPreviewContainer = view.findViewById(R.id.cart_preview_container);
        cartPreviewItemCount = view.findViewById(R.id.cart_preview_item_count);
        cartPreviewPrice = view.findViewById(R.id.cart_preview_price);
        bottomSheetBehavior = BottomSheetBehavior.from(cartPreviewContainer);
        cartImage = view.findViewById(R.id.cart_preview_shopping_cart_image);
        cartCheckoutArrowLayout = view.findViewById(R.id.cart_checkout_layout);
        cartRecycler = view.findViewById(R.id.cart_item_list_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        cartRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

        bottomSheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                    cartCheckoutArrowLayout.setVisibility(View.GONE);
                    return;
                }
                cartCheckoutArrowLayout.setVisibility(View.VISIBLE);
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                cartPreviewItemCount.setAlpha(1 - slideOffset);
                cartImage.setAlpha(1 - slideOffset);
                setPriceViewTranslation(slideOffset);
                cartPreviewPrice.setTextColor(calculateColor(slideOffset));
                cartCheckoutArrowLayout.setAlpha(slideOffset);
            }
        });
    }

    private void setPriceViewTranslation(float slideOffset) {
        float potentialTranslation = (float) ((slideOffset * 1125) - cartPreviewPrice.getWidth() / 1.8);
        float translation = (potentialTranslation >= 0) ? potentialTranslation : 0;
        cartPreviewPrice.setTranslationX(translation);
    }

    private static int calculateColor(float slideOffset) {
        int nonGreen = (int)(0xFF * (1 - slideOffset));
        int blue = Math.max(nonGreen, 0x52);
        int red = Math.max(nonGreen, 0x87);
        return (0xFF00FF00 | blue) | (red << 16);
    }

    private void checkShouldDisplayPreview() {

        if (customerItemListViewModel.getCart().isEmpty()) {
            recyclerView.setPadding(0, 0, 0, 0);
            cartPreviewContainer.animate()
                    .alpha(0f)
                    .setDuration(getResources().getInteger(android.R.integer.config_longAnimTime));
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            cartPreviewContainer.setVisibility(View.GONE);
        } else if (cartPreviewContainer.getVisibility() == View.GONE) {
            updateCartPreview();
            recyclerView.setPadding(0, 120, 0, 0);
            cartPreviewContainer.setAlpha(0f);
            cartPreviewContainer.setVisibility(View.VISIBLE);
            cartPreviewContainer.animate()
                    .alpha(0.8f)
                    .setDuration(getResources().getInteger(android.R.integer.config_longAnimTime));
        } else {
            updateCartPreview();
        }
    }

    private void onAddToCartDialogSubmit(Item item, int quantity) {
        customerItemListViewModel.getCart().add(item, quantity);
        checkShouldDisplayPreview();
    }

    private void updateCartPreview() {
        cartPreviewItemCount.setText(String.valueOf(customerItemListViewModel.getCart().getNumOfItems()));
        cartPreviewPrice.setText(customerItemListViewModel.getCart().getSubtotalString());
        if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED)
            setPriceViewTranslation(1);
        cartRecycler.setAdapter(new CartListAdapter(getContext(), customerItemListViewModel.getCart()) {
            @Override
            public void onCardClick(Item item) {
                new CartEditItemDialog(item, customerItemListViewModel.getCart().getQuantityOfItem(item), quantity -> {
                    customerItemListViewModel.getCart().updateQuantity(item, quantity);
                    checkShouldDisplayPreview();
                }).show(getParentFragmentManager(), "CartEditItemDialog");
            }
        });
    }

    private void setBackBehavior() {
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener((v, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED || bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_SETTLING) {//this is hacked, might break in the future, trying to fix issue of this method being called twice on a single back press, the sheet is in STATE_SETTLING when the second method call executes
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                } else {
                    getActivity().finish();
                }
            }
            return true;
        });
    }


}
