package com.example.ghostkitchenandroid.ui.store_owner;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ghostkitchenandroid.R;
import com.example.ghostkitchenandroid.model.Kitchen;
import com.example.ghostkitchenandroid.ui.lists.ItemListFragment;
import com.example.ghostkitchenandroid.ui.lists.OrderListFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

public class MyKitchenFragment extends Fragment {

    private BottomNavigationView bottomNavigationView;
    private MyKitchenViewModel myKitchenViewModel;
    private View myKitchenFragmentContainer;
    private FloatingActionButton floatingActionButton;

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
        floatingActionButton = view.findViewById(R.id.my_kitchen_fab);

        floatingActionButton.setOnClickListener(v -> {
            DialogFragment dialogFragment = new AddItemDialog();
            dialogFragment.show(getActivity().getSupportFragmentManager(), "AddItemDialogFragment");
        });

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.bottom_nav_pending_orders:
                    getParentFragmentManager().beginTransaction().replace(myKitchenFragmentContainer.getId(), new OrderListFragment()).addToBackStack(null).commit();
                    floatingActionButton.setVisibility(View.INVISIBLE);
                    return true;
                case R.id.bottom_nav_menu:
                    getParentFragmentManager().beginTransaction().replace(myKitchenFragmentContainer.getId(), new ItemListFragment()).addToBackStack(null).commit();
                    floatingActionButton.setVisibility(View.VISIBLE);
                    return true;
            }
            return false;
        });

        getParentFragmentManager().beginTransaction().replace(myKitchenFragmentContainer.getId(), new OrderListFragment()).commit();
        bottomNavigationView.setSelectedItemId(R.id.bottom_nav_pending_orders);

    }

    public static class AddItemDialog extends DialogFragment {
        @NonNull
        @Override
        public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            LayoutInflater inflater = requireActivity().getLayoutInflater();
            builder.setView(inflater.inflate(R.layout.create_item, null))
                    .setPositiveButton(R.string.submit, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //TODO
                        }
                    })
                    .setNegativeButton(R.string.negative_button, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            getDialog().cancel();
                        }
                    });

            return builder.create();
        }
    }

}
