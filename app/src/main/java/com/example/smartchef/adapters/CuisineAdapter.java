package com.example.smartchef.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.smartchef.R;
import com.example.smartchef.models.Category;

import java.util.List;

public class CuisineAdapter extends RecyclerView.Adapter<CuisineAdapter.ViewHolder> {

    public interface OnCuisineClickListener {
        void onCuisineClick(Category cuisine);
    }

    private final Context context;
    private final List<Category> cuisines;
    private final OnCuisineClickListener listener;

    public CuisineAdapter(Context context, List<Category> cuisines, OnCuisineClickListener listener) {
        this.context = context;
        this.cuisines = cuisines;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cuisine_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Category category = cuisines.get(position);
        holder.tvName.setText(category.getName());

        Glide.with(context)
                .load(category.getImageUrl())
                .placeholder(R.drawable.bg_pill_indicator)
                .into(holder.ivBg);

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onCuisineClick(category);
        });
    }

    @Override
    public int getItemCount() {
        return cuisines != null ? cuisines.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivBg;
        TextView tvName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivBg = itemView.findViewById(R.id.iv_cuisine_bg);
            tvName = itemView.findViewById(R.id.tv_cuisine_name);
        }
    }
}
