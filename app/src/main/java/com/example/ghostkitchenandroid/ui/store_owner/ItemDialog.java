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
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.ghostkitchenandroid.R;
import com.example.ghostkitchenandroid.model.Item;
import com.example.ghostkitchenandroid.ui.lists.ItemListViewModel;

import java.util.Arrays;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class ItemDialog extends DialogFragment {

    private ItemListViewModel itemListViewModel;
    private ProgressBar progressBar;
    private Item item;
    private View itemDialogView;
    private EditText etName, etPrice, etDescription;
    private TextView tvCategory;
    private LinearLayout customerSpinner;
    private AlertDialog.Builder builder;
    private LayoutInflater inflater;

    /**
     * Use this constructor to create a dialog to edit the selected item.
     * @param itemListViewModel
     * @param item The item to be modified.
     */
    public ItemDialog(ItemListViewModel itemListViewModel, Item item) {
        this.itemListViewModel = itemListViewModel;
        this.item = item;
    }

    /**
     * Use this constructor to create a dialog for adding a new item.
     * @param itemListViewModel
     * @param progressBar A progress bar that will be enabled/disabled as needed for network status.
     */
    public ItemDialog(ItemListViewModel itemListViewModel, ProgressBar progressBar) {
        this.itemListViewModel = itemListViewModel;
        this.progressBar = progressBar;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        initViews();
        if (item == null)
            return createItemDialog();
        return editItemDialog();
    }

    private void initViews() {
        builder = new AlertDialog.Builder(getActivity());
        inflater = requireActivity().getLayoutInflater();

        itemDialogView = inflater.inflate(R.layout.create_item, null);
        etName = itemDialogView.findViewById(R.id.create_item_name_et);
        etPrice = itemDialogView.findViewById(R.id.create_item_price_et);
        etDescription = itemDialogView.findViewById(R.id.create_item_description_et);
        tvCategory = itemDialogView.findViewById(R.id.create_item_category_tv);
        customerSpinner = itemDialogView.findViewById(R.id.create_item_category_spinner);

        customerSpinner.setOnClickListener(v -> {
            AddItemCategoryDialog categoryDialog = new AddItemCategoryDialog(itemListViewModel, tvCategory);
            categoryDialog.show(getActivity().getSupportFragmentManager(), "AddItemCategoryDialogFragment");
        });
    }

    private Dialog createItemDialog() {
        builder.setView(itemDialogView)
                .setPositiveButton(R.string.submit, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
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

    private Dialog editItemDialog() {
        etName.setText(item.getName());
        etPrice.setText(String.valueOf(item.getPrice()));
        etDescription.setText(item.getDescription());
        tvCategory.setText(item.getCategory());

        builder.setView(itemDialogView)
                .setPositiveButton(R.string.submit, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        progressBar.setVisibility(View.VISIBLE);
                        item.setName(etName.getText().toString().trim());
                        item.setPrice(Double.parseDouble(etPrice.getText().toString().trim()));
                        item.setDescription(etDescription.getText().toString().trim());
                        item.setCategory(tvCategory.getText().toString().trim());
                        item.setKitchen(itemListViewModel.getKitchen());
                        itemListViewModel.createItem(item);
                    }
                })
                .setNeutralButton("DELETE", ((dialog, which) -> {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    AlertDialog alertDialog = builder.setTitle("Are you sure you want to delete item:\n" + item.getName() + "?")
                            .setPositiveButton(R.string.delete_strong, (dialog1, which1) -> {
                                itemListViewModel.deleteItem(item);
                                getDialog().dismiss();
                            })
                            .setNegativeButton(R.string.negative_button, (dialog1, which1) -> {
                                getDialog().dismiss();
                            })
                            .create();
                    alertDialog.getButton(Dialog.BUTTON_POSITIVE).setTextColor(getContext().getColor(R.color.danger));
                    alertDialog.show();
                }))
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

            String[] categories = itemListViewModel.getKitchenMenu().getCategories();
            Log.i("categoriestostring", Arrays.toString(categories));

            listView.setAdapter(new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, categories));
            listView.setClickable(true);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    tvCategory.setText(itemListViewModel.getKitchenMenu().getCategories()[position]);
                    getDialog().cancel();
                }
            });

            return builder.setView(view)
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
