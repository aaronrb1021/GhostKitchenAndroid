package com.example.ghostkitchenandroid.ui.store_owner;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.ghostkitchenandroid.R;
import com.example.ghostkitchenandroid.model.Order;

public class StoreOwnerOrderDialog extends OrderDialogFragment {

    private OrderStatusUpdateCompatible orderStatusUpdateCompatible;
    private Order order;

    public StoreOwnerOrderDialog(Order order, OrderStatusUpdateCompatible orderStatusUpdateCompatible) {
        super(order);
        this.order = order;
        this.orderStatusUpdateCompatible = orderStatusUpdateCompatible;
    }

    @Override
    protected void onOkClick(Order order, Spinner statusSpinner, DialogInterface dialog) {
        if (statusSpinner.getSelectedItem().equals("COMPLETE") && order.getStatus() != Order.STATUS_COMPLETE)
            updateOrderWithStatus(order, Order.STATUS_COMPLETE);
        else if (statusSpinner.getSelectedItem().equals("CANCELLED") && order.getStatus() != Order.STATUS_CANCELLED)
            updateOrderWithStatus(order, Order.STATUS_CANCELLED);
        else if (statusSpinner.getSelectedItem().equals("PENDING") && order.getStatus() != Order.STATUS_PENDING)
            updateOrderWithStatus(order, Order.STATUS_PENDING);

        dialog.dismiss();
    }

    @Override
    protected void handleStatus(Order order, Spinner statusSpinner, TextView statusTextView) {
        statusSpinner.setVisibility(View.VISIBLE);

        if (order.getStatus() == Order.STATUS_COMPLETE) {
            statusSpinner.setSelection(1);
        } else if (order.getStatus() == Order.STATUS_CANCELLED) {
            statusSpinner.setSelection(2);
        }
    }

    @Override
    protected AlertDialog.Builder handleBuilderButtons(Order order, AlertDialog.Builder builder) {
        if (order.getStatus() == Order.STATUS_PENDING) {
            builder.setNeutralButton(R.string.mark_order_complete, ((dialog, which) -> {
                updateOrderWithStatus(order, Order.STATUS_COMPLETE);
            }));
        }
        return builder;
    }

    private void updateOrderWithStatus(Order order, byte status) {
        order.setStatus(status);
        orderStatusUpdateCompatible.updateOrder(order);
        orderStatusUpdateCompatible.refreshOrders(); //this will update our visible list, as per observance of the orderListLiveData
    }
}
