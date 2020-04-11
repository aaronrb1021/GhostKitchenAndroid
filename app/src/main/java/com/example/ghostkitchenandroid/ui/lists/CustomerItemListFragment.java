package com.example.ghostkitchenandroid.ui.lists;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ghostkitchenandroid.R;
import com.example.ghostkitchenandroid.model.Item;
import com.example.ghostkitchenandroid.model.Kitchen;
import com.example.ghostkitchenandroid.model.KitchenMenu;

public class CustomerItemListFragment extends Fragment {

    private CustomerItemListViewModel customerItemListViewModel;
    private RecyclerView recyclerView;
    private View cartPreviewContainer;

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

        Bundle bundle = getArguments();
        if (bundle != null)
            customerItemListViewModel.setKitchen((Kitchen) bundle.get("kitchen"));
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

        recyclerView = view.findViewById(R.id.customer_item_list_recycler);
        cartPreviewContainer = view.findViewById(R.id.cart_preview_container);

        customerItemListViewModel.updateItemListLiveData();
        customerItemListViewModel.getItemListLiveData().observe(getViewLifecycleOwner(), items -> {
            recyclerView.setAdapter(new ItemListAdapter(getContext(), new KitchenMenu(items)) {
                @Override
                public void onItemCardClick(Item item) {
                    //TODO need a dialog to allow addition of items to the cart in viewmodel
                }
            });
        });
    }
}
