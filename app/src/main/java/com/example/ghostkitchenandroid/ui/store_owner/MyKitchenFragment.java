package com.example.ghostkitchenandroid.ui.store_owner;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ghostkitchenandroid.R;
import com.example.ghostkitchenandroid.model.Kitchen;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

public class MyKitchenFragment extends Fragment {

    private BottomNavigationView bottomNavigationView;
    private MyKitchenViewModel myKitchenViewModel;
    private View myKitchenFragmentContainer;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Bundle bundle = getArguments();

        myKitchenViewModel = new ViewModelProvider(this).get(MyKitchenViewModel.class);
        myKitchenViewModel.setKitchen((Kitchen) bundle.get("kitchen"));
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

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.bottom_nav_pending_orders:
                    getParentFragmentManager().beginTransaction().replace(myKitchenFragmentContainer.getId(), new OrderListFragment()).commit();
                    return true;
            }
            return false;
        });

        getParentFragmentManager().beginTransaction().replace(myKitchenFragmentContainer.getId(), new OrderListFragment()).commit();
        bottomNavigationView.setSelectedItemId(R.id.bottom_nav_pending_orders);

    }


}
