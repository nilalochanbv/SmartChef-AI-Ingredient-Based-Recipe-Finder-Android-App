package com.example.smartchef.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartchef.R;
import com.example.smartchef.models.Ingredient;

import java.util.List;

public class BasketAdapter extends RecyclerView.Adapter<BasketAdapter.ViewHolder> {

    public interface OnBasketRemoveListener {
        void onItemRemoved(Ingredient ingredient);
    }

    private final Context context;
    private final List<Ingredient> selectedItems;
    private final OnBasketRemoveListener removeListener;

    public BasketAdapter(Context context, List<Ingredient> selectedItems, OnBasketRemoveListener removeListener) {
        this.context = context;
        this.selectedItems = selectedItems;
        this.removeListener = removeListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_basket_ingredient, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Ingredient item = selectedItems.get(position);
        holder.tvName.setText(item.getName());

        holder.btnRemove.setOnClickListener(v -> {
            if (removeListener != null) {
                removeListener.onItemRemoved(item);
            }
        });
    }

    @Override
    public int getItemCount() {
        return selectedItems != null ? selectedItems.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, btnRemove;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_basket_item_name);
            btnRemove = itemView.findViewById(R.id.btn_remove_item);
        }
    }
}
