package com.example.ghostkitchenandroid.ui.lists;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.ItemListViewHolder> {

    @NonNull
    @Override
    public ItemListAdapter.ItemListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemListAdapter.ItemListViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class ItemListViewHolder extends RecyclerView.ViewHolder {
        public ItemListViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
