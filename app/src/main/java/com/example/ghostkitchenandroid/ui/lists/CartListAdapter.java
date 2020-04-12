package com.example.ghostkitchenandroid.ui.lists;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ghostkitchenandroid.R;
import com.example.ghostkitchenandroid.model.Cart;
import com.example.ghostkitchenandroid.model.Item;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public abstract class CartListAdapter extends RecyclerView.Adapter {

    private Cart cart;
    private Context context;

    public CartListAdapter(Context context, Cart cart) {
        this.context = context;
        this.cart = cart;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CartViewHolder(LayoutInflater.from(context).inflate(R.layout.row_cart_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        CartViewHolder cartViewHolder = (CartViewHolder) holder;
        Item item = cart.getItem(position);

        cartViewHolder.itemName.setText(item.getName());
        cartViewHolder.itemPrice.setText(item.getPriceString());
        cartViewHolder.quantity.setText(String.valueOf(cart.getQuantityOfItem(position)));
        cartViewHolder.card.setOnClickListener((View v) -> {
            onCardClick(item);
        });
    }

    @Override
    public int getItemCount() {
        return cart.getNumOfItems();
    }

    public abstract void onCardClick(Item item);

    class CartViewHolder extends RecyclerView.ViewHolder {

        private TextView itemName, itemPrice, quantity;
        private CardView card;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);

            itemName = itemView.findViewById(R.id.cart_row_item_name_tv);
            itemPrice =  itemView.findViewById(R.id.cart_row_item_price_tv);
            quantity = itemView.findViewById(R.id.cart_row_quantity);
            card = itemView.findViewById(R.id.cart_row_cardview);
        }
    }
}
