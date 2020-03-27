package com.example.ghostkitchenandroid.ui.store_owner;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.ghostkitchenandroid.R;
import com.example.ghostkitchenandroid.model.Item;
import com.example.ghostkitchenandroid.ui.lists.ItemListViewModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class AddItemDialog extends DialogFragment {

    private ItemListViewModel itemListViewModel;
    private ProgressBar progressBar;

    public AddItemDialog(ItemListViewModel itemListViewModel, ProgressBar progressBar) {
        this.itemListViewModel = itemListViewModel;
        this.progressBar = progressBar;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        final View view = inflater.inflate(R.layout.create_item, null);
        final EditText etName = view.findViewById(R.id.create_item_name_et);
        final EditText etPrice = view.findViewById(R.id.create_item_price_et);
        final EditText etDescription = view.findViewById(R.id.create_item_description_et);
        final TextView tvCategory = view.findViewById(R.id.create_item_category_tv);

        tvCategory.setOnClickListener(v -> {
            AddItemCategoryDialog categoryDialog = new AddItemCategoryDialog(itemListViewModel, tvCategory);
            categoryDialog.show(getActivity().getSupportFragmentManager(), "AddItemCategoryDialogFragment");
        });

        builder.setView(view)
                .setPositiveButton(R.string.submit, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //TODO
                        progressBar.setVisibility(View.VISIBLE);
                        itemListViewModel.createItem(new Item(
                                        etName.getText().toString().trim(),
                                        Double.parseDouble(etPrice.getText().toString().trim()),
                                        tvCategory.getText().toString(),
                                        itemListViewModel.getKitchen(),
                                        etDescription.getText().toString().trim()
                                )
                        );
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

    public static class AddItemCategoryDialog extends DialogFragment {

        private ItemListViewModel itemListViewModel;
        private TextView tvCategory;
        private Button button;
        private ListView listView;
        private EditText etCategory;

        AddItemCategoryDialog(ItemListViewModel itemListViewModel, TextView tvCategory) {
            this.itemListViewModel = itemListViewModel;
            this.tvCategory = tvCategory;
        }
        @NonNull
        @Override
        public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            LayoutInflater inflater = requireActivity().getLayoutInflater();

            final View view = inflater.inflate(R.layout.create_item_category, null);
            etCategory = view.findViewById(R.id.create_item_category_dialog_et);
            button = view.findViewById(R.id.create_item_category_dialog_submit);
            listView = view.findViewById(R.id.create_item_category_list);

            button.setOnClickListener(v -> {
                    tvCategory.setText(etCategory.getText().toString().trim());
                    getDialog().cancel();
            });

            listView.setAdapter(new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_2, itemListViewModel.getKitchenMenu().getCategories()));
//            listView.setClickable(true);
            listView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    tvCategory.setText(itemListViewModel.getKitchenMenu().getCategories()[position]);
                    dismiss();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            return builder.setView(R.layout.create_item_category)
                    .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            getDialog().cancel();
                        }
                    })
                    .create();
        }
    }
}
