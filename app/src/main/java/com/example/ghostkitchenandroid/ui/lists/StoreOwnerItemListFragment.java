package com.example.ghostkitchenandroid.ui.lists;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ghostkitchenandroid.R;
import com.example.ghostkitchenandroid.model.Item;
import com.example.ghostkitchenandroid.model.Kitchen;
import com.example.ghostkitchenandroid.model.KitchenMenu;
import com.example.ghostkitchenandroid.ui.store_owner.ItemDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class StoreOwnerItemListFragment extends Fragment {

    private StoreOwnerItemListViewModel storeOwnerItemListViewModel;
    private RecyclerView itemListRecycler;
    private ProgressBar progressBar;
    private TextView tvNoItems;
    private FloatingActionButton floatingActionButton;
    private int mode;

    public static final int MODE_STORE_OWNER = 3;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        storeOwnerItemListViewModel = new ViewModelProvider(this).get(StoreOwnerItemListViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_item_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews(view);

        handleBundle();

        updateItemList();

        setObservance();

    }

    private void initViews(View view) {
        itemListRecycler = view.findViewById(R.id.item_list_recycler);
        itemListRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        progressBar = view.findViewById(R.id.item_list_progress);
        tvNoItems = view.findViewById(R.id.item_list_no_items_tv);
        floatingActionButton = view.findViewById(R.id.item_list_fab);

        floatingActionButton.setOnClickListener(v -> {
            DialogFragment dialogFragment = new ItemDialog(storeOwnerItemListViewModel, progressBar);
            dialogFragment.show(getActivity().getSupportFragmentManager(), "AddItemDialogFragment");
        });
    }

    private void handleBundle() {
        final Bundle bundle = getArguments();
        storeOwnerItemListViewModel.setKitchen((Kitchen) bundle.get("kitchen"));
        mode = (int) bundle.get("mode");
        if (mode == MODE_STORE_OWNER)
            floatingActionButton.setVisibility(View.VISIBLE);
    }

    private void updateItemList() {
        progressBar.setVisibility(View.VISIBLE);
        storeOwnerItemListViewModel.updateItemListLiveData();
    }

    private void setObservance() {
        storeOwnerItemListViewModel.getItemListLiveData().observe(getViewLifecycleOwner(), items -> {
            progressBar.setVisibility(View.INVISIBLE);
            if (items.size() == 0) {
                tvNoItems.setVisibility(View.VISIBLE);
            } else {
                tvNoItems.setVisibility(View.INVISIBLE);
            }
            storeOwnerItemListViewModel.setKitchenMenu(new KitchenMenu(items));
            itemListRecycler.setAdapter(new ItemListAdapter(getContext(), storeOwnerItemListViewModel.getKitchenMenu()) {
                @Override
                public void onItemCardClick(Item item) {
                    DialogFragment dialogFragment = new ItemDialog(storeOwnerItemListViewModel, item);
                    dialogFragment.show(getParentFragmentManager(), "EditItemDialogFragment");
                }
            });
        });

        //only set observance for the below items in store owner mode because they're not needed for customers
        if (mode == MODE_STORE_OWNER) {
            storeOwnerItemListViewModel.getItemLiveData().observe(getViewLifecycleOwner(), item -> {
                progressBar.setVisibility(View.INVISIBLE);
                updateItemList();
            }); //show the updated item list when we receive our created item back to our app

            storeOwnerItemListViewModel.getDeleteItemLiveData().observe(getViewLifecycleOwner(), success -> {
                if (success) {
                    Toast.makeText(getContext(), R.string.item_deleted, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), R.string.item_delete_failed, Toast.LENGTH_SHORT).show();
                }
                updateItemList();
            });
        }
    }
}
