package com.example.ghostkitchenandroid.ui.lists;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ghostkitchenandroid.R;
import com.example.ghostkitchenandroid.model.Item;
import com.example.ghostkitchenandroid.model.KitchenMenu;
import com.example.ghostkitchenandroid.model.MenuItemWrapper;

import org.w3c.dom.Text;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ItemListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private KitchenMenu kitchenMenu;
    private ArrayList<MenuItemWrapper> itemWrappers;

    private final static int VIEW_TYPE_CATEGORY = 1;
    private final static int VIEW_TYPE_ITEM = 2;

    public ItemListAdapter(Context context, KitchenMenu kitchenMenu) {
        this.context = context;
        this.kitchenMenu = kitchenMenu;
        itemWrappers = kitchenMenu.getItemWrapperList();
    }

    @Override
    public int getItemViewType(int position) {
        if (itemWrappers.get(position).isCategory())
            return VIEW_TYPE_CATEGORY;
        return VIEW_TYPE_ITEM;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_CATEGORY)
            return new CategoryViewHolder(LayoutInflater.from(context).inflate(R.layout.item_category_row, parent, false));
        return new ItemViewHolder(LayoutInflater.from(context).inflate(R.layout.item_row, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == VIEW_TYPE_CATEGORY) {
            ((CategoryViewHolder) holder).tvCategory.setText(itemWrappers.get(position).getCategory());
        } else {
            ItemViewHolder itemViewHolder = ((ItemViewHolder) holder);
            Item item = itemWrappers.get(position).getItem();
            itemViewHolder.tvItemName.setText(item.getName());
            itemViewHolder.tvItemPrice.setText(item.getPriceString());
            itemViewHolder.tvItemDescription.setText(item.getDescription());
        }
    }

    @Override
    public int getItemCount() {
        return itemWrappers.size();
    }


    class ItemViewHolder extends RecyclerView.ViewHolder {

        private TextView tvItemName, tvItemPrice, tvItemDescription;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItemName = itemView.findViewById(R.id.item_row_name_tv);
            tvItemPrice = itemView.findViewById(R.id.item_row_price_tv);
            tvItemDescription = itemView.findViewById(R.id.item_row_description_tv);
        }
    }

    class CategoryViewHolder extends RecyclerView.ViewHolder {

        private TextView tvCategory;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCategory = itemView.findViewById(R.id.item_row_category_tv);
        }
    }
    //TODO MAKE CATEGORY VIEWHOLDER
}
