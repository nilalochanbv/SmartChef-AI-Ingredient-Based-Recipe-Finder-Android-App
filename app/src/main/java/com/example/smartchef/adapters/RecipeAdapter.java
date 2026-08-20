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
import com.google.android.material.button.MaterialButton;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder> {

    public interface OnRecipeClickListener {
        void onRecipeClick(Recipe recipe);
        void onFavoriteClick(Recipe recipe);
    }

    private final Context context;
    private List<Recipe> recipes;
    private final OnRecipeClickListener listener;
    private final FavoritesManager favoritesManager;

    public RecipeAdapter(Context context, List<Recipe> recipes, OnRecipeClickListener listener) {
        this.context = context;
        this.recipes = recipes;
        this.listener = listener;
        this.favoritesManager = FavoritesManager.getInstance(context);
    }

    public void updateList(List<Recipe> newList) {
        this.recipes = newList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_recipe_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Recipe recipe = recipes.get(position);
        holder.tvTitle.setText(recipe.getTitle());
        holder.tvRating.setText(String.valueOf(recipe.getRating()));
        holder.tvCookingTime.setText(recipe.getCookingTimeMinutes() + " min");
        holder.tvDifficulty.setText(recipe.getDifficulty());

        holder.tvMatchPercentage.setText(recipe.getMatchPercentage() + "% Match");
        
        int totalIng = recipe.getIngredients() != null ? recipe.getIngredients().size() : 6;
        int missingCount = recipe.getMissingIngredients() != null ? recipe.getMissingIngredients().size() : 1;
        int haveCount = Math.max(0, totalIng - missingCount);
        
        holder.tvIngredientsSummary.setText("You have " + haveCount + " of " + totalIng + " ingredients");

        if (recipe.getMissingIngredients() != null && !recipe.getMissingIngredients().isEmpty()) {
            holder.tvMissingIngredient.setVisibility(View.VISIBLE);
            holder.tvMissingIngredient.setText("Missing: " + recipe.getMissingIngredients().get(0));
        } else {
            holder.tvMissingIngredient.setVisibility(View.GONE);
        }

        // TODO: Move database check out of onBindViewHolder to avoid blocking the main thread.
        // Consider pre-fetching favorite IDs or using a Flow/LiveData.
        boolean isFav = favoritesManager.isFavorite(recipe.getId());
        recipe.setFavorite(isFav);
        holder.btnFavorite.setImageResource(isFav ? R.drawable.ic_heart_filled : R.drawable.ic_heart_outline);

        Glide.with(context)
                .load(recipe.getImageUrl())
                .placeholder(R.drawable.bg_pill_indicator)
                .into(holder.ivImage);

        View.OnClickListener clickListener = v -> {
            if (listener != null) listener.onRecipeClick(recipe);
        };
        holder.itemView.setOnClickListener(clickListener);
        holder.btnViewRecipe.setOnClickListener(clickListener);

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
        TextView tvTitle, tvRating, tvCookingTime, tvDifficulty, tvMatchPercentage;
        TextView tvIngredientsSummary, tvMissingIngredient;
        MaterialButton btnViewRecipe;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivImage = itemView.findViewById(R.id.iv_recipe_image);
            btnFavorite = itemView.findViewById(R.id.btn_favorite);
            tvTitle = itemView.findViewById(R.id.tv_recipe_title);
            tvRating = itemView.findViewById(R.id.tv_rating);
            tvCookingTime = itemView.findViewById(R.id.tv_cooking_time);
            tvDifficulty = itemView.findViewById(R.id.tv_difficulty);
            tvMatchPercentage = itemView.findViewById(R.id.tv_match_percentage);
            tvIngredientsSummary = itemView.findViewById(R.id.tv_ingredients_count_summary);
            tvMissingIngredient = itemView.findViewById(R.id.tv_missing_ingredient);
            btnViewRecipe = itemView.findViewById(R.id.btn_view_recipe);
        }
    }
}
