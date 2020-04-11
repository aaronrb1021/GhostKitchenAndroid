package com.example.ghostkitchenandroid.ui.lists;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.ghostkitchenandroid.R;
import com.example.ghostkitchenandroid.network.user.UserRepo;

public class KitchenListFragment extends Fragment {

    private KitchenListViewModel kitchenListViewModel;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private Toolbar toolbar;
    private View kitchenFragmentContainer;
    private TextView tvNoKitchens;
    private int mode;

    public static KitchenListFragment newInstance(int mode) {
        if (mode == KitchenListAdapter.MODE_STORE_OWNER || mode == KitchenListAdapter.MODE_CUSTOMER) {
            KitchenListFragment kitchenListFragment = new KitchenListFragment();
            kitchenListFragment.mode = mode;
            return kitchenListFragment;
        }
        return null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        kitchenListViewModel = new ViewModelProvider(this).get(KitchenListViewModel.class);

        if (mode != 0) //This makes sure we only set the mode after newInstance(int mode); on state changes we do not need to set the mode again.
            kitchenListViewModel.setMode(mode);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_kitchen_list, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvNoKitchens = view.findViewById(R.id.my_kitchens_no_kitchens_tv);
        progressBar = view.findViewById(R.id.my_kitchen_progress);
        recyclerView = view.findViewById(R.id.my_kitchen_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        if (kitchenListViewModel.getMode() == KitchenListAdapter.MODE_CUSTOMER)
            showAllKitchens();
        else if (kitchenListViewModel.getMode() == KitchenListAdapter.MODE_STORE_OWNER)
            showKitchensByUser();

        setObservance();
    }

    private void showKitchensByUser() {
        progressBar.setVisibility(View.VISIBLE);
        kitchenListViewModel.updateKitchensLiveDataByUser(UserRepo.getLoggedInUser());
    }

    public void showAllKitchens() {
        progressBar.setVisibility(View.VISIBLE);
        kitchenListViewModel.updateKitchensLiveDataAllKitchens();
    }

    private void setObservance() {
        kitchenListViewModel.getKitchensLiveData().observe(getViewLifecycleOwner(), kitchens -> {
            if (kitchens.size() == 0)
                tvNoKitchens.setVisibility(View.VISIBLE);
            else
                tvNoKitchens.setVisibility(View.INVISIBLE);

            recyclerView.setAdapter(new KitchenListAdapter(getContext(), kitchens, kitchenListViewModel.getMode()));
            progressBar.setVisibility(View.INVISIBLE);
        });
    }

}
