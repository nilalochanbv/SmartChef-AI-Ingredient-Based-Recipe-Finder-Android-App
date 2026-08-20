package com.example.smartchef.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartchef.R;
import com.example.smartchef.models.Ingredient;

import java.util.List;

public class IngredientDetailAdapter extends RecyclerView.Adapter<IngredientDetailAdapter.ViewHolder> {

    private final Context context;
    private final List<Ingredient> ingredients;

    public IngredientDetailAdapter(Context context, List<Ingredient> ingredients) {
        this.context = context;
        this.ingredients = ingredients;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_ingredient_detail, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Ingredient item = ingredients.get(position);
        holder.tvName.setText(item.getName());
        holder.tvAmount.setText(item.getAmount());

        if (item.isAvailable()) {
            holder.tvStatusIcon.setText("✓");
            holder.tvStatusIcon.setBackgroundResource(R.drawable.bg_match_badge);
            holder.tvStatusIcon.setTextColor(ContextCompat.getColor(context, R.color.match_green));

            holder.tvBadge.setText("Available");
            holder.tvBadge.setBackgroundResource(R.drawable.bg_match_badge);
            holder.tvBadge.setTextColor(ContextCompat.getColor(context, R.color.match_green));
        } else {
            holder.tvStatusIcon.setText("○");
            holder.tvStatusIcon.setBackgroundResource(R.drawable.bg_missing_chip);
            holder.tvStatusIcon.setTextColor(ContextCompat.getColor(context, R.color.tomato_red));

            holder.tvBadge.setText("Missing");
            holder.tvBadge.setBackgroundResource(R.drawable.bg_missing_chip);
            holder.tvBadge.setTextColor(ContextCompat.getColor(context, R.color.tomato_red));
        }
    }

    @Override
    public int getItemCount() {
        return ingredients != null ? ingredients.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvStatusIcon, tvName, tvAmount, tvBadge;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvStatusIcon = itemView.findViewById(R.id.tv_status_icon);
            tvName = itemView.findViewById(R.id.tv_ingredient_name);
            tvAmount = itemView.findViewById(R.id.tv_ingredient_amount);
            tvBadge = itemView.findViewById(R.id.tv_availability_badge);
        }
    }
}
