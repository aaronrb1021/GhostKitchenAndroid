package com.example.ghostkitchenandroid.ui.lists;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.transition.Fade;
import android.transition.Scene;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ghostkitchenandroid.R;
import com.example.ghostkitchenandroid.model.Item;
import com.example.ghostkitchenandroid.model.Kitchen;
import com.example.ghostkitchenandroid.model.KitchenMenu;
import com.example.ghostkitchenandroid.ui.customer.CartAddItemDialog;

public class CustomerItemListFragment extends Fragment {

    private CustomerItemListViewModel customerItemListViewModel;
    private RecyclerView recyclerView;
    private View cartPreviewContainer;
    private TextView cartPreviewPrice, cartPreviewItemCount;

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

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

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
    }

    private void initViews(View view) {
        recyclerView = view.findViewById(R.id.customer_item_list_recycler);
        cartPreviewContainer = view.findViewById(R.id.cart_preview_container);
        cartPreviewItemCount = view.findViewById(R.id.cart_preview_item_count);
        cartPreviewPrice = view.findViewById(R.id.cart_preview_price);
    }

    private void checkShouldDisplayPreview() {

        if (customerItemListViewModel.getCart().isEmpty()) {
            cartPreviewContainer.animate()
                    .alpha(0f)
                    .setDuration(getResources().getInteger(android.R.integer.config_longAnimTime));
            cartPreviewContainer.setVisibility(View.GONE);
        } else if (cartPreviewContainer.getVisibility() == View.GONE){
            updateCartPreview();
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
    }

}
