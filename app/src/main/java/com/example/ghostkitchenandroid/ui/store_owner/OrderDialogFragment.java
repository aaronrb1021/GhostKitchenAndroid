package com.example.ghostkitchenandroid.ui.store_owner;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.ghostkitchenandroid.R;
import com.example.ghostkitchenandroid.model.Item;
import com.example.ghostkitchenandroid.model.Order;
import com.example.ghostkitchenandroid.utils.Format;

import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public abstract class OrderDialogFragment extends DialogFragment {

    private Order order;
    private TextView id, kitchen, date, address, statusTextView, tax, deliveryFee, total;
    private Spinner statusSpinner;
    private TableLayout tableLayout;

    public OrderDialogFragment(Order order) {
        this.order = order;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = requireActivity().getLayoutInflater().inflate(R.layout.dialog_order, null);

        builder.setView(view)
                .setTitle(R.string.order_details)
                .setPositiveButton(android.R.string.ok, ((dialog, which) -> {
                    onOkClick(order, statusSpinner, dialog);
                }));

        initViews(view);
        config();


        return handleBuilderButtons(order, builder).create();
    }

    protected abstract void onOkClick(Order order, Spinner statusSpinner, DialogInterface dialog);

    protected abstract void handleStatus(Order order, Spinner statusSpinner, TextView statusTextView);

    protected abstract AlertDialog.Builder handleBuilderButtons(Order order, AlertDialog.Builder builder);

    private void initViews(View view) {
        id = view.findViewById(R.id.order_dialog_order_id);
        kitchen = view.findViewById(R.id.order_dialog_kitchen);
        date = view.findViewById(R.id.order_dialog_date);
        address = view.findViewById(R.id.order_dialog_delivery_address);
        statusTextView = view.findViewById(R.id.order_dialog_status_tv);
        tax = view.findViewById(R.id.order_dialog_tax);
        deliveryFee = view.findViewById(R.id.order_dialog_deliver_fee);
        total = view.findViewById(R.id.order_dialog_total);
        statusSpinner = view.findViewById(R.id.order_dialog_status_spinner);
        tableLayout = view.findViewById(R.id.order_dialog_table);
    }

    private void config() {
        id.setText(String.valueOf(order.getId()));
        kitchen.setText(order.getKitchen().getName());
        date.setText(order.getDateString());

        if (order.isPickup()) {
            address.setText("N/A - PICKUP\nPickup Name: " + order.getPickupName());
        } else {
            //TODO delivery address
        }

        handleStatus(order, statusSpinner, statusTextView);

        showItemsInTable();

        tax.setText(Format.getFormattedPrice(order.getTaxFee()));

        deliveryFee.setText(Format.getFormattedPrice(order.getDeliveryFee()));

        total.setText(order.getTotalString());
    }

    private void showItemsInTable() {
        for (int i = 0; i < order.getCart().getNumOfItems(); i++) {
            Item item = order.getCart().getItem(i);

            TextView tvName = new TextView(getContext());
            TextView tvQuantity = new TextView(getContext());
            TextView tvPrice = new TextView(getContext());

            tvPrice.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_END);

            tvName.setText(item.getName());
            tvQuantity.setText(String.valueOf(order.getCart().getQuantityOfItem(item)));
            tvPrice.setText(Format.getFormattedPrice(order.getCart().getQuantityOfItem(item) * item.getPrice()));

            TableRow tableRow = new TableRow(getContext());
            tableLayout.addView(tableRow);

            tableRow.addView(tvName, new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 4));
            tableRow.addView(tvQuantity, new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 2));
            tableRow.addView(tvPrice, new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1));
        }
    }
}
