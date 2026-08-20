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
import com.example.smartchef.models.Recipe;
import com.example.smartchef.utils.FavoritesManager;

import java.util.List;

public class PopularRecipeAdapter extends RecyclerView.Adapter<PopularRecipeAdapter.ViewHolder> {

    public interface OnRecipeClickListener {
        void onRecipeClick(Recipe recipe);
        void onFavoriteClick(Recipe recipe);
    }

    private final Context context;
    private final List<Recipe> recipes;
    private final OnRecipeClickListener listener;
    private final FavoritesManager favoritesManager;

    public PopularRecipeAdapter(Context context, List<Recipe> recipes, OnRecipeClickListener listener) {
        this.context = context;
        this.recipes = recipes;
        this.listener = listener;
        this.favoritesManager = FavoritesManager.getInstance(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_recipe_popular, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Recipe recipe = recipes.get(position);
        holder.tvTitle.setText(recipe.getTitle());
        holder.tvRating.setText(String.valueOf(recipe.getRating()));
        holder.tvCookingTime.setText(recipe.getCookingTimeMinutes() + " mins");
        holder.tvDifficulty.setText(recipe.getDifficulty());

        boolean isFav = favoritesManager.isFavorite(recipe.getId());
        recipe.setFavorite(isFav);
        holder.btnFavorite.setImageResource(isFav ? R.drawable.ic_heart_filled : R.drawable.ic_heart_outline);

        Glide.with(context)
                .load(recipe.getImageUrl())
                .placeholder(R.drawable.bg_pill_indicator)
                .into(holder.ivImage);

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onRecipeClick(recipe);
        });

        holder.btnFavorite.setOnClickListener(v -> {
            boolean newFav = favoritesManager.toggleFavorite(recipe);
            holder.btnFavorite.setImageResource(newFav ? R.drawable.ic_heart_filled : R.drawable.ic_heart_outline);
            if (listener != null) listener.onFavoriteClick(recipe);
        });
    }

    @Override
    public int getItemCount() {
        return recipes != null ? recipes.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivImage, btnFavorite;
        TextView tvTitle, tvRating, tvCookingTime, tvDifficulty;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivImage = itemView.findViewById(R.id.iv_recipe_image);
            btnFavorite = itemView.findViewById(R.id.btn_favorite);
            tvTitle = itemView.findViewById(R.id.tv_recipe_title);
            tvRating = itemView.findViewById(R.id.tv_rating);
            tvCookingTime = itemView.findViewById(R.id.tv_cooking_time);
            tvDifficulty = itemView.findViewById(R.id.tv_difficulty);
        }
    }
}
