package com.example.ghostkitchenandroid.ui.customer;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ghostkitchenandroid.R;
import com.example.ghostkitchenandroid.ui.lists.KitchenListAdapter;
import com.example.ghostkitchenandroid.ui.lists.KitchenListFragment;
import com.example.ghostkitchenandroid.utils.UserCredValidity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.resources.TextAppearance;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.w3c.dom.Text;

public class CustomerShopFragment extends Fragment {

    private CustomerShopViewModel customerShopViewModel;
    private TextView tvFilter;
    private ImageView imageFilter;
    private View listContainer;
    private BottomNavigationView bottomNavigationView;
    private int selection;

    public static CustomerShopFragment newInstance() {
        return new CustomerShopFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_customer_shop, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        customerShopViewModel = new ViewModelProvider(this).get(CustomerShopViewModel.class);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvFilter = view.findViewById(R.id.customer_shop_filter_tv);
        imageFilter = view.findViewById(R.id.customer_shop_filter_image);
        listContainer = view.findViewById(R.id.customer_shop_list_container);
        bottomNavigationView = view.findViewById(R.id.customer_shop_bottom_nav);

        bottomNavigationView.setOnNavigationItemSelectedListener(makeNavListener());

        bottomNavigationView.setSelectedItemId(R.id.bottom_nav_shop_explore);

        manageFilterButton();
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    private BottomNavigationView.OnNavigationItemSelectedListener makeNavListener() {
        return item -> {
            switch (item.getItemId()) {
                case R.id.bottom_nav_shop_explore:
                    displayKitchenList(null, null);
                    return true;
                case R.id.bottom_nav_shop_featured:
                    displayKitchenList(null, null);
                    return true;
                case R.id.bottom_nav_shop_favorites:
                    displayFavorites();
                    return true;
            }
            return false;
        };
    }

    private void displayKitchenList(String zip, KitchenListFragment.Radius radius) {
        if (zip == null || radius == null)
            getParentFragmentManager().beginTransaction().replace(listContainer.getId(), KitchenListFragment.newInstance(KitchenListAdapter.MODE_CUSTOMER)).commit();
        else
            getParentFragmentManager().beginTransaction().replace(listContainer.getId(), KitchenListFragment.newInstance(KitchenListAdapter.MODE_CUSTOMER, zip, radius)).commit();
    }

    private void displayFavorites() {
        getParentFragmentManager().beginTransaction().replace(listContainer.getId(), KitchenListFragment.newInstance(KitchenListAdapter.MODE_CUSTOMER_FAVORITES)).commit();
    }

    private void manageFilterButton() {
        View.OnClickListener onClickListener = (v) -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

            AlertDialog alertDialog = builder.setPositiveButton(android.R.string.cancel, ((dialog, which) -> {
                dialog.dismiss();
            }))
                    .setTitle("Distance From Zip Code")
                    .create();

            alertDialog.setView(makeLinearLayout(alertDialog));
            alertDialog.show();
        };

        tvFilter.setOnClickListener(onClickListener);
        imageFilter.setOnClickListener(onClickListener);
    }

    private LinearLayout makeLinearLayout(AlertDialog alertDialog) {
        LinearLayout linearLayout = new LinearLayout(getContext());
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        TextInputLayout textInputLayout = makeTextInput();

        linearLayout.addView(textInputLayout);
        linearLayout.addView(makeListView(textInputLayout, alertDialog));

        return linearLayout;
    }

    private TextInputLayout makeTextInput() {
        TextInputLayout textInputLayout = new TextInputLayout(getContext());
        TextInputEditText textInputEditText = new TextInputEditText(getContext());

        textInputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                textInputLayout.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        textInputEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
        textInputEditText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        textInputLayout.setErrorEnabled(true);
        textInputLayout.setPadding(450, 50, 450, 0);
        textInputLayout.addView(textInputEditText);
        textInputLayout.setHint("Zip Code");

        return textInputLayout;
    }

    private ListView makeListView(TextInputLayout textInputLayout, AlertDialog alertDialog) {
        String[] options = {"5 miles", "10 miles", "20 miles", "50 miles", "All"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_single_choice, options);

        ListView listView = new ListView(getContext());

        listView.setAdapter(adapter);
        listView.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);

        if (customerShopViewModel.getZipCode() != null) {
            textInputLayout.getEditText().setText(customerShopViewModel.getZipCode());
            listView.setItemChecked(customerShopViewModel.getPositionSelection(), true);
        } else {
            listView.setItemChecked(4, true);
        }

        listView.setOnItemClickListener((parent, view, position, id) -> {

            String zipString = textInputLayout.getEditText().getText().toString();
            String selectedOption = ((TextView)parent.getChildAt(position)).getText().toString();

            if (!selectedOption.equals("All") && !UserCredValidity.isZipValid(zipString)) {
                textInputLayout.setError(getString(R.string.required));
                listView.setItemChecked(customerShopViewModel.getPositionSelection(), true);
            } else {
                switch (selectedOption) {
                    case "5 miles":
                        customerShopViewModel.setZipCode(zipString);
                        customerShopViewModel.setPositionSelection(0);
                        displayKitchenList(zipString, KitchenListFragment.Radius.FIVE);
                        break;
                    case "10 miles":
                        customerShopViewModel.setZipCode(zipString);
                        customerShopViewModel.setPositionSelection(1);
                        displayKitchenList(zipString, KitchenListFragment.Radius.TEN);
                        break;
                    case "20 miles":
                        customerShopViewModel.setZipCode(zipString);
                        customerShopViewModel.setPositionSelection(2);
                        displayKitchenList(zipString, KitchenListFragment.Radius.TWENTY);
                        break;
                    case "50 miles":
                        customerShopViewModel.setZipCode(zipString);
                        customerShopViewModel.setPositionSelection(3);
                        displayKitchenList(zipString, KitchenListFragment.Radius.FIFTY);
                        break;
                    default:
                        customerShopViewModel.setZipCode(null);
                        customerShopViewModel.setPositionSelection(4);
                        displayKitchenList(null, null);
                        break;
                }
                alertDialog.dismiss();
            }
        });


        return listView;
    }

}
