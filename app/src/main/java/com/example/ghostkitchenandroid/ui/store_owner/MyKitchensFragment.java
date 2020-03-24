package com.example.ghostkitchenandroid.ui.store_owner;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ghostkitchenandroid.R;
import com.example.ghostkitchenandroid.model.User;
import com.example.ghostkitchenandroid.network.user.UserRepo;
import com.example.ghostkitchenandroid.ui.kitchen_list.KitchenListAdapter;

public class MyKitchensFragment extends Fragment {

    private MyKitchensViewModel mViewModel;
    private RecyclerView recyclerView;

    public static MyKitchensFragment newInstance() {
        return new MyKitchensFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.my_kitchens_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mViewModel = new ViewModelProvider(this).get(MyKitchensViewModel.class);
        recyclerView = getActivity().findViewById(R.id.my_kitchen_recycler);
//        recyclerView.setAdapter(new KitchenListAdapter(getContext(), ));
    }

}
