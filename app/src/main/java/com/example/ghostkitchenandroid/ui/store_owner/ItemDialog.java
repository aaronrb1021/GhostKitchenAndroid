package com.example.ghostkitchenandroid.ui.store_owner;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.ghostkitchenandroid.R;
import com.example.ghostkitchenandroid.model.Item;
import com.example.ghostkitchenandroid.ui.lists.StoreOwnerItemListViewModel;

import java.util.Arrays;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class ItemDialog extends DialogFragment {

    private StoreOwnerItemListViewModel storeOwnerItemListViewModel;
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
     *
     * @param storeOwnerItemListViewModel
     * @param item              The item to be modified.
     */
    public ItemDialog(StoreOwnerItemListViewModel storeOwnerItemListViewModel, Item item) {
        this.storeOwnerItemListViewModel = storeOwnerItemListViewModel;
        this.item = item;
    }

    /**
     * Use this constructor to create a dialog for adding a new item.
     *
     * @param storeOwnerItemListViewModel
     * @param progressBar       A progress bar that will be enabled/disabled as needed for network status.
     */
    public ItemDialog(StoreOwnerItemListViewModel storeOwnerItemListViewModel, ProgressBar progressBar) {
        this.storeOwnerItemListViewModel = storeOwnerItemListViewModel;
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

    @Override
    public void onResume() {
        super.onResume();

        final AlertDialog alertDialog = (AlertDialog) getDialog();
        if (alertDialog != null) {
            if (item == null) { //CREATE ITEM LISTENERS
                alertDialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(v -> {
                    if (checkData()) {
                        progressBar.setVisibility(View.VISIBLE);
                        storeOwnerItemListViewModel.createItem(new Item(
                                        etName.getText().toString().trim(),
                                        Double.parseDouble(etPrice.getText().toString().trim()),
                                        tvCategory.getText().toString(),
                                        storeOwnerItemListViewModel.getKitchen(),
                                        etDescription.getText().toString().trim()
                                )
                        );
                        dismiss();
                    }
                });
            } else { //EDIT ITEM LISTENERS
                alertDialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(v -> {
                    if (checkData())
                        submitEditItem();
                    dismiss();
                });
                alertDialog.getButton(DialogInterface.BUTTON_NEUTRAL).setOnClickListener(v -> {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    AlertDialog checkDeleteDialog = builder.setTitle("Are you sure you want to delete item: " + item.getName() + "?")
                            .setPositiveButton(R.string.delete_strong, (dialog1, which1) -> {
                                storeOwnerItemListViewModel.deleteItem(item);
                                dismiss();
                            })
                            .setNegativeButton(R.string.negative_button, null)
                            .create();
                    checkDeleteDialog.show();
                    checkDeleteDialog.getButton(Dialog.BUTTON_POSITIVE).setTextColor(getContext().getColor(R.color.danger));
                });

            }
        }
    }


    private void initViews() {
        builder = new AlertDialog.Builder(getActivity());
        inflater = requireActivity().getLayoutInflater();

        itemDialogView = inflater.inflate(R.layout.dialog_create_item, null);
        etName = itemDialogView.findViewById(R.id.create_item_name_et);
        etPrice = itemDialogView.findViewById(R.id.create_item_price_et);
        etDescription = itemDialogView.findViewById(R.id.create_item_description_et);
        tvCategory = itemDialogView.findViewById(R.id.create_item_category_tv);
        customerSpinner = itemDialogView.findViewById(R.id.create_item_category_spinner);

        customerSpinner.setOnClickListener(v -> {
            AddItemCategoryDialog categoryDialog = new AddItemCategoryDialog(storeOwnerItemListViewModel, tvCategory);
            categoryDialog.show(getActivity().getSupportFragmentManager(), "AddItemCategoryDialogFragment");
            tvCategory.setError(null);
        });
    }

    private boolean checkData() {
        if (etName.getText().toString().trim().length() == 0) {
            etName.setError(getString(R.string.required));
            return false;
        } else if (etPrice.getText().toString().trim().length() == 0) {
            etPrice.setError(getString(R.string.required));
            return false;
        } else if (tvCategory.getText().toString().equalsIgnoreCase("Category")) {
            tvCategory.setError(getString(R.string.required));
            return false;
        }
        return true;
    }

    private Dialog createItemDialog() {
        builder.setView(itemDialogView)
                .setPositiveButton(R.string.submit, null) //onClick set in onResume()
                .setNegativeButton(R.string.negative_button, (dialog, which) -> dismiss())
                .setTitle(R.string.dialog_create_item_title);

        return builder.create();
    }

    private Dialog editItemDialog() {
        initEditViews();

        builder.setCancelable(false);
        builder.setView(itemDialogView)
                .setPositiveButton(R.string.submit, null) //listener is set in onResume()
                .setNeutralButton("DELETE", null)//listener is set in onResume()
                .setNegativeButton(R.string.negative_button, (dialog, which) -> dismiss());

        return builder.create();
    }

    private void initEditViews() {
        etName.setText(item.getName());
        etPrice.setText(String.valueOf(item.getPrice()));
        etDescription.setText(item.getDescription());
        tvCategory.setText(item.getCategory());
    }

    private void submitEditItem() {
        item.setName(etName.getText().toString().trim());
        item.setPrice(Double.parseDouble(etPrice.getText().toString().trim()));
        item.setDescription(etDescription.getText().toString().trim());
        item.setCategory(tvCategory.getText().toString().trim());
        item.setKitchen(storeOwnerItemListViewModel.getKitchen());
        storeOwnerItemListViewModel.createItem(item);
    }

    /**
     * NESTED INNER CLASS
     * Used for creating a new dialog to display existing categories previously created as well as give the option to create a new category.
     */
    public static class AddItemCategoryDialog extends DialogFragment {

        private StoreOwnerItemListViewModel storeOwnerItemListViewModel;
        private TextView tvCategory;
        private Button button;
        private ListView listView;
        private EditText etCategory;

        AddItemCategoryDialog(StoreOwnerItemListViewModel storeOwnerItemListViewModel, TextView tvCategory) {
            this.storeOwnerItemListViewModel = storeOwnerItemListViewModel;
            this.tvCategory = tvCategory;
        }

        @NonNull
        @Override
        public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            LayoutInflater inflater = requireActivity().getLayoutInflater();

            final View view = inflater.inflate(R.layout.dialog_create_item_category, null);
            etCategory = view.findViewById(R.id.create_item_category_dialog_et);
            button = view.findViewById(R.id.create_item_category_dialog_submit);
            listView = view.findViewById(R.id.create_item_category_list);

            button.setOnClickListener(v -> {
                tvCategory.setText(etCategory.getText().toString().trim());
                getDialog().cancel();
            });

            String[] categories = storeOwnerItemListViewModel.getKitchenMenu().getCategories();
            Log.i("categoriestostring", Arrays.toString(categories));

            listView.setAdapter(new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, categories));
            listView.setClickable(true);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    tvCategory.setText(storeOwnerItemListViewModel.getKitchenMenu().getCategories()[position]);
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
