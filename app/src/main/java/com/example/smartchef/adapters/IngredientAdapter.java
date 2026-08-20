package com.example.smartchef.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartchef.R;
import com.example.smartchef.models.Ingredient;

import java.util.List;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.ViewHolder> {

    public interface OnIngredientSelectionListener {
        void onIngredientSelected(Ingredient ingredient, boolean isSelected);
    }

    private final Context context;
    private final List<Ingredient> ingredients;
    private final OnIngredientSelectionListener listener;

    public IngredientAdapter(Context context, List<Ingredient> ingredients, OnIngredientSelectionListener listener) {
        this.context = context;
        this.ingredients = ingredients;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_ingredient_chip, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Ingredient ingredient = ingredients.get(position);
        holder.tvName.setText(ingredient.getName());

        updateChipState(holder, ingredient.isSelected());

        holder.chipContainer.setOnClickListener(v -> {
            boolean newState = !ingredient.isSelected();
            ingredient.setSelected(newState);

            // Scale Animation on Select
            holder.chipContainer.animate().scaleX(1.08f).scaleY(1.08f).setDuration(120).withEndAction(() -> {
                holder.chipContainer.animate().scaleX(1.0f).scaleY(1.0f).setDuration(120).start();
            }).start();

            updateChipState(holder, newState);

            if (listener != null) {
                listener.onIngredientSelected(ingredient, newState);
            }
        });
    }

    private void updateChipState(ViewHolder holder, boolean selected) {
        if (selected) {
            holder.chipContainer.setBackgroundResource(R.drawable.bg_chip_selected);
            holder.tvName.setTextColor(Color.WHITE);
            holder.ivCheck.setVisibility(View.VISIBLE);
        } else {
            holder.chipContainer.setBackgroundResource(R.drawable.bg_chip_unselected);
            holder.tvName.setTextColor(ContextCompat.getColor(context, R.color.text_primary));
            holder.ivCheck.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return ingredients != null ? ingredients.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        View chipContainer;
        ImageView ivCheck;
        TextView tvName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            chipContainer = itemView.findViewById(R.id.chip_container);
            ivCheck = itemView.findViewById(R.id.iv_check_icon);
            tvName = itemView.findViewById(R.id.tv_ingredient_name);
        }
    }
}
