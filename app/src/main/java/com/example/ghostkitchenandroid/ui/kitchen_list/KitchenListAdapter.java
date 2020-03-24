package com.example.ghostkitchenandroid.ui.kitchen_list;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.ghostkitchenandroid.R;
import com.example.ghostkitchenandroid.model.Kitchen;
import com.example.ghostkitchenandroid.ui.store_owner.MyKitchenFragment;
import com.example.ghostkitchenandroid.ui.store_owner.MyKitchensFragment;

import java.util.ArrayList;
import java.util.TreeSet;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

public class KitchenListAdapter extends RecyclerView.Adapter<KitchenListAdapter.KitchenViewHolder> {

    private Context context;
    private ArrayList<Kitchen> kitchens;
    private ArrayList<View.OnClickListener> cachedListeners = new ArrayList<>();

    public KitchenListAdapter(Context context, ArrayList<Kitchen> kitchens) {
        this.context = context;
        this.kitchens = kitchens;
    }

    @NonNull
    @Override
    public KitchenListAdapter.KitchenViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new KitchenViewHolder(LayoutInflater.from(context).inflate(R.layout.kitchen_row, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull KitchenListAdapter.KitchenViewHolder holder, int position) {
        holder.tvName.setText(kitchens.get(position).getName());
        holder.tvAddress.setText(kitchens.get(position).getKitchenAddress().toString());
        holder.tvPhone.setText(kitchens.get(position).getKitchenAddress().getPhone());

        if (position >= cachedListeners.size() || cachedListeners.get(position) == null) {
            cachedListeners.add(position, view -> {
                MyKitchenFragment fragment = new MyKitchenFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("kitchen", kitchens.get(position));
                fragment.setArguments(bundle);
                ((FragmentActivity) context).getSupportFragmentManager().beginTransaction().replace(R.id.store_owner_fragment_container, fragment, "MyKitchenFragment").addToBackStack(null).commit();
            });
        }

        holder.cardView.setOnClickListener(cachedListeners.get(position));
    }

    @Override
    public int getItemCount() {
        return kitchens.size();
    }

    static class KitchenViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;
        private TextView tvName, tvAddress, tvPhone;
        private CardView cardView;

        public KitchenViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.kitchen_image_view);
            tvName = itemView.findViewById(R.id.kitchen_name_tv);
            tvAddress = itemView.findViewById(R.id.kitchen_address_tv);
            tvPhone = itemView.findViewById(R.id.kitchen_phone_tv);
            cardView = itemView.findViewById(R.id.kitchen_row_cardview);
        }
    }
}
