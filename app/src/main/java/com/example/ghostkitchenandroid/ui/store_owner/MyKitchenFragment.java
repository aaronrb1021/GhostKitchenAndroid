package com.example.ghostkitchenandroid.ui.store_owner;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.ghostkitchenandroid.R;
import com.example.ghostkitchenandroid.model.Kitchen;
import com.example.ghostkitchenandroid.ui.lists.StoreOwnerItemListFragment;
import com.example.ghostkitchenandroid.ui.lists.OrderListFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

public class MyKitchenFragment extends Fragment {

    private BottomNavigationView bottomNavigationView;
    private MyKitchenViewModel myKitchenViewModel;
    private View myKitchenFragmentContainer;
    private ProgressBar progressBar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Bundle bundle = getArguments();

        myKitchenViewModel = new ViewModelProvider(this).get(MyKitchenViewModel.class);

        final Kitchen kitchen = (Kitchen) bundle.get("kitchen");
        if (kitchen != null)
            myKitchenViewModel.setKitchen(kitchen);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_store_owner_kitchen, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(myKitchenViewModel.getKitchen().getName());

        bottomNavigationView = view.findViewById(R.id.my_kitchen_bottom_nav);
        myKitchenFragmentContainer = view.findViewById(R.id.my_kitchen_fragment_container);
        progressBar = view.findViewById(R.id.my_kitchen_progress);

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.bottom_nav_pending_orders:
                    getParentFragmentManager().beginTransaction().replace(myKitchenFragmentContainer.getId(), new OrderListFragment()).commit();
                    return true;
                case R.id.bottom_nav_menu:
                    showItemList();
                    return true;
            }
            return false;
        });

        getParentFragmentManager().beginTransaction().replace(myKitchenFragmentContainer.getId(), new OrderListFragment()).commit();
        bottomNavigationView.setSelectedItemId(R.id.bottom_nav_pending_orders);

    }

    private void showItemList() {
        StoreOwnerItemListFragment storeOwnerItemListFragment = new StoreOwnerItemListFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("kitchen", myKitchenViewModel.getKitchen());
        bundle.putInt("mode", StoreOwnerItemListFragment.MODE_STORE_OWNER);
        storeOwnerItemListFragment.setArguments(bundle);
        getParentFragmentManager().beginTransaction().replace(myKitchenFragmentContainer.getId(), storeOwnerItemListFragment).commit();
    }

}
