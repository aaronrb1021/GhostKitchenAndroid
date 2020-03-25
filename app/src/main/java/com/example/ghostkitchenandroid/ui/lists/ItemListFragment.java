package com.example.ghostkitchenandroid.ui.lists;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.ghostkitchenandroid.R;
import com.example.ghostkitchenandroid.model.Kitchen;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ItemListFragment extends Fragment {

    private ItemListViewModel itemListViewModel;
    private RecyclerView itemListRecycler;
    private ProgressBar progressBar;
    private Kitchen kitchen;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Bundle bundle = getArguments();

        itemListViewModel = new ViewModelProvider(this).get(ItemListViewModel.class);
        itemListViewModel.setKitchen((Kitchen) bundle.get("kitchen"));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_item_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        itemListRecycler = view.findViewById(R.id.item_list_recycler);
        itemListRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        progressBar = view.findViewById(R.id.item_list_progress);


        setObservance();
        updateItemList();

    }

    private void updateItemList() {
        progressBar.setVisibility(View.VISIBLE);
        itemListViewModel.updateItemListLiveDataByKitchen(itemListViewModel.getKitchen());
    }

    private void setObservance() {
        itemListViewModel.getItemListLiveData().observe(getViewLifecycleOwner(), items -> {
            itemListRecycler.setAdapter(new ItemListAdapter(getContext(), items));
            progressBar.setVisibility(View.INVISIBLE);
        });
    }
}
