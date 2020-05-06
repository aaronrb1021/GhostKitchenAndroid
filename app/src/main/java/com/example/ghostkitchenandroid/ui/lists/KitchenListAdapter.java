package com.example.ghostkitchenandroid.ui.lists;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ghostkitchenandroid.R;
import com.example.ghostkitchenandroid.model.Kitchen;
import com.example.ghostkitchenandroid.ui.customer.CustomerActivity;
import com.example.ghostkitchenandroid.ui.customer.CustomerKitchenActivity;
import com.example.ghostkitchenandroid.ui.store_owner.MyKitchenFragment;

import java.util.ArrayList;
import java.util.Base64;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

public class KitchenListAdapter extends RecyclerView.Adapter<KitchenListAdapter.KitchenViewHolder> {

    private Context context;
    private ArrayList<Kitchen> kitchens;
    private ArrayList<View.OnClickListener> cachedListeners = new ArrayList<>();
    private int mode;

    public static final int MODE_STORE_OWNER = 2;
    public static final int MODE_CUSTOMER = 3;

    public KitchenListAdapter(Context context, ArrayList<Kitchen> kitchens, int mode) {
        this.context = context;
        this.kitchens = kitchens;
        this.mode = mode;
    }

    @NonNull
    @Override
    public KitchenListAdapter.KitchenViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new KitchenViewHolder(LayoutInflater.from(context).inflate(R.layout.row_kitchen, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull KitchenListAdapter.KitchenViewHolder holder, int position) {

        Bitmap image = decodeImage(position);
        if (image != null)
            holder.imageView.setImageBitmap(image);

        holder.tvName.setText(kitchens.get(position).getName());
        holder.tvAddress.setText(kitchens.get(position).getKitchenAddress().toString());
        holder.tvPhone.setText(kitchens.get(position).getKitchenAddress().getPhone());

        if (position >= cachedListeners.size() || cachedListeners.get(position) == null) {
            if (mode == MODE_STORE_OWNER) {
                cachedListeners.add(position, view -> {
                    MyKitchenFragment fragment = new MyKitchenFragment();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("kitchen", kitchens.get(position));
                    fragment.setArguments(bundle);
                    ((FragmentActivity) context).getSupportFragmentManager().beginTransaction().replace(R.id.store_owner_fragment_container, fragment, "MyKitchenFragment").addToBackStack(null).commit();
                });
            } else if (mode == MODE_CUSTOMER) {
                cachedListeners.add(position, view -> {
                    Intent intent = new Intent(context, CustomerKitchenActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("kitchen", kitchens.get(position));
                    intent.putExtras(bundle);
//                    intent.putExtra("kitchen", kitchens.get(position));
                    context.startActivity(intent);
                });
            }
        }

        holder.cardView.setOnClickListener(cachedListeners.get(position));
    }

    @Override
    public int getItemCount() {
        return kitchens.size();
    }

    private Bitmap decodeImage(int position) {
        String base64ImageString = kitchens.get(position).getImageBytes();

        if (base64ImageString != null) {
            byte[] bytes = Base64.getDecoder().decode(base64ImageString);
            return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        }
        return null;
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
