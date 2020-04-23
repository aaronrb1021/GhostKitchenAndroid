package com.example.ghostkitchenandroid.ui.lists;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ghostkitchenandroid.R;
import com.example.ghostkitchenandroid.model.Order;
import com.example.ghostkitchenandroid.utils.Format;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public abstract class OrderListAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<OrderListAdapter.OrderListViewHolder> {

    private Context context;
    private ArrayList<Order> orders;

    public OrderListAdapter(Context context, ArrayList<Order> orders) {
        this.context = context;
        this.orders = orders;
    }

    @NonNull
    @Override
    public OrderListAdapter.OrderListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OrderListViewHolder(LayoutInflater.from(context).inflate(R.layout.row_order, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull OrderListAdapter.OrderListViewHolder holder, int position) {
        Order order = orders.get(position);

        holder.time.setText(Format.makeDisplayTimeFromMillis(order.getOrderDateInMillis()));

        if (order.isPickup()) {
            holder.timeLimit.setText("Pickup Time: " + Format.makeDisplayTimeFromMillis(order.getOrderDateInMillis() + 900000 * 2)); //todo need to collect pickup time in order
            holder.buyerName.setText(order.getPickupName());
        }
        else {
            holder.timeLimit.setText("Deliver By: " + Format.makeDisplayTimeFromMillis(order.getOrderDateInMillis() + 900000 * 4)); //todo " "
            holder.buyerName.setText(order.getBuyerDetails().getName());
        }

        holder.buyerPhone.setText(Format.phone(order.getBuyerDetails().getPhone()));

        if (order.isPickup())
            holder.buyerAddress.setText("PICKUP ORDER");
        else
            holder.buyerAddress.setText("N/A"); //todo delivery address

        holder.orderDetails.setText(order.getTotalString() + "\n" + order.getCart().getNumOfItems() + " Items");

        switch (order.getStatus()) {
            case Order.STATUS_PENDING:
                holder.image.setImageDrawable(context.getDrawable(R.drawable.ic_pending_24dp));
                break;
            case Order.STATUS_COMPLETE:
                holder.image.setImageDrawable(context.getDrawable(R.drawable.ic_complete_24dp));
                break;
            case Order.STATUS_CANCELLED:
                holder.image.setImageDrawable(context.getDrawable(R.drawable.ic_cancelled_24dp));
                break;
        }

        holder.cardView.setOnClickListener(view -> {
            onCardClick(order);
        });
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public abstract void onCardClick(Order order);

    public abstract String getEmptyDisplayText();

    static class OrderListViewHolder extends RecyclerView.ViewHolder {

        private TextView time, timeLimit, buyerName, buyerPhone, buyerAddress, orderDetails;
        private ImageView image;
        private CardView cardView;

        OrderListViewHolder(@NonNull View view) {
            super(view);

            time = view.findViewById(R.id.order_row_time);
            timeLimit = view.findViewById(R.id.order_row_time_limit);
            buyerName = view.findViewById(R.id.order_row_buyer_name);
            buyerPhone = view.findViewById(R.id.order_row_phone);
            buyerAddress = view.findViewById(R.id.order_row_address);
            orderDetails = view.findViewById(R.id.order_row_details);
            image = view.findViewById(R.id.order_row_status_image);
            cardView = view.findViewById(R.id.order_row_cardview);
        }

    }
}
