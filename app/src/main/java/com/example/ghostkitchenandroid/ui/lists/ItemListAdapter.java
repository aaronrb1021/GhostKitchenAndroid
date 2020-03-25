package com.example.ghostkitchenandroid.ui.lists;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.ghostkitchenandroid.R;
import com.example.ghostkitchenandroid.model.Item;
import com.example.ghostkitchenandroid.model.Kitchen;
import com.example.ghostkitchenandroid.model.KitchenMenu;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.ItemListViewHolder> {

    private Context context;
    private KitchenMenu kitchenMenu;

    public ItemListAdapter(Context context, ArrayList<Item> items) {
        this.context = context;
        kitchenMenu = new KitchenMenu(items);
    }

    @NonNull
    @Override
    public ItemListAdapter.ItemListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemListViewHolder(LayoutInflater.from(context).inflate(R.layout.item_row, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemListAdapter.ItemListViewHolder holder, int position) {
//TODO        if (kitchenMenu.getItemWrapperList().get(position).isCategory())
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class ItemListViewHolder extends RecyclerView.ViewHolder {

        private TextView tvItemName, tvItemPrice, tvItemDescription;

        public ItemListViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItemName = itemView.findViewById(R.id.item_row_name_tv);
            tvItemPrice = itemView.findViewById(R.id.item_row_price_tv);
            tvItemDescription = itemView.findViewById(R.id.item_row_description_tv);
        }
    }
}