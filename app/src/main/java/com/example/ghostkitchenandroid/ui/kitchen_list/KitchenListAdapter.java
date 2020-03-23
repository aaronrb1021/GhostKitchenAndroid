package com.example.ghostkitchenandroid.ui.kitchen_list;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class KitchenListAdapter extends RecyclerView.Adapter<KitchenListAdapter.KitchenViewHolder> {

    private Context context;


    public KitchenListAdapter(Context context) {

    }

    @NonNull
    @Override
    public KitchenListAdapter.KitchenViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull KitchenListAdapter.KitchenViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    static class KitchenViewHolder extends RecyclerView.ViewHolder {

        public KitchenViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
