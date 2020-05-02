package com.example.ghostkitchenandroid.ui.lists;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.ghostkitchenandroid.R;
import com.example.ghostkitchenandroid.network.user.UserRepo;

public class KitchenListFragment extends Fragment {

    public enum Radius {
        ALL, FIVE, TEN, TWENTY, FIFTY
    }

    private KitchenListViewModel kitchenListViewModel;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private Toolbar toolbar;
    private View kitchenFragmentContainer;
    private TextView tvNoKitchens;

    public static KitchenListFragment newInstance(int mode) {
        if (mode == KitchenListAdapter.MODE_STORE_OWNER || mode == KitchenListAdapter.MODE_CUSTOMER) {
            KitchenListFragment kitchenListFragment = new KitchenListFragment();

            Bundle bundle = new Bundle();
            bundle.putSerializable("mode", mode);

            kitchenListFragment.setArguments(bundle);

            return kitchenListFragment;
        }
        return null;
    }

    public static KitchenListFragment newInstance(int mode, String zip, Radius radius) {
        if (mode == KitchenListAdapter.MODE_STORE_OWNER || mode == KitchenListAdapter.MODE_CUSTOMER) {
            KitchenListFragment kitchenListFragment = new KitchenListFragment();

            Bundle bundle = new Bundle();
            bundle.putSerializable("mode", mode);
            bundle.putSerializable("zip", zip);
            bundle.putSerializable("radius", radius);

            kitchenListFragment.setArguments(bundle);

            return kitchenListFragment;
        }
        return null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        kitchenListViewModel = new ViewModelProvider(this).get(KitchenListViewModel.class);

        Bundle bundle = getArguments();

        if (bundle != null) {
            kitchenListViewModel.setMode((int) bundle.get("mode"));
            kitchenListViewModel.setZip((String) bundle.get("zip"));
            kitchenListViewModel.setRadius((Radius) bundle.get("radius"));
        }

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

        initViews(view);

        if (kitchenListViewModel.getRadius() == null && kitchenListViewModel.getMode() == KitchenListAdapter.MODE_CUSTOMER)
            showAllKitchens();
        else if (kitchenListViewModel.getMode() == KitchenListAdapter.MODE_STORE_OWNER)
            showKitchensByUser();
        else if (kitchenListViewModel.getRadius() != null)
            showKitchensByRadiusFromZip();

        setObservance();
    }

    private void initViews(View view) {
        tvNoKitchens = view.findViewById(R.id.my_kitchens_no_kitchens_tv);
        progressBar = view.findViewById(R.id.my_kitchen_progress);
        recyclerView = view.findViewById(R.id.my_kitchen_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void showKitchensByUser() {
        progressBar.setVisibility(View.VISIBLE);
        kitchenListViewModel.updateKitchensLiveDataByUser(UserRepo.getLoggedInUser());
    }

    public void showAllKitchens() {
        progressBar.setVisibility(View.VISIBLE);
        kitchenListViewModel.updateKitchensLiveDataAllKitchens();
    }

    private void showKitchensByRadiusFromZip() {
        Log.i("fetchbyzip", "here");
        String radiusString;

        switch (kitchenListViewModel.getRadius()) {
            case FIVE:
                radiusString = "5";
                break;
            case TEN:
                radiusString = "10";
                break;
            case TWENTY:
                radiusString = "20";
                break;
            case FIFTY:
                radiusString = "50";
                break;
            default:
                showAllKitchens();
                return;
        }

        progressBar.setVisibility(View.VISIBLE);
        kitchenListViewModel.updateKitchensByDistanceFromZip(radiusString);
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

    @Override
    public void onResume() {
        super.onResume();

        if (kitchenListViewModel.getMode() == KitchenListAdapter.MODE_STORE_OWNER)
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.my_kitchens_toolbar_title);
    }
}
