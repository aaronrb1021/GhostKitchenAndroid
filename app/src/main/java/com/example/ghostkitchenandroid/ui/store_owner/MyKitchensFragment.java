package com.example.ghostkitchenandroid.ui.store_owner;

import androidx.appcompat.app.AppCompatActivity;
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
import android.widget.Toolbar;

import com.example.ghostkitchenandroid.R;
import com.example.ghostkitchenandroid.network.user.UserRepo;
import com.example.ghostkitchenandroid.ui.lists.KitchenListAdapter;

public class MyKitchensFragment extends Fragment {

    private MyKitchensViewModel myKitchensViewModel;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private Toolbar toolbar;
    private View kitchenFragmentContainer;

    public static MyKitchensFragment newInstance() {
        return new MyKitchensFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        myKitchensViewModel = new ViewModelProvider(this).get(MyKitchensViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.my_kitchens_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.my_kitchens_toolbar_title);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        progressBar = view.findViewById(R.id.my_kitchen_progress);
        recyclerView = view.findViewById(R.id.my_kitchen_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        updateKitchensList();

        setObservance();
    }

    private void updateKitchensList() {
        progressBar.setVisibility(View.VISIBLE);
        myKitchensViewModel.updateKitchensLiveData(UserRepo.getLoggedInUser());
    }

    private void setObservance() {
        myKitchensViewModel.getKitchensLiveData().observe(getViewLifecycleOwner(), kitchens -> {
            recyclerView.setAdapter(new KitchenListAdapter(getContext(), kitchens));
            progressBar.setVisibility(View.INVISIBLE);
        });
    }

}
