package com.example.smartchef.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartchef.R;
import com.example.smartchef.activities.MainActivity;
import com.example.smartchef.activities.RecipeDetailActivity;
import com.example.smartchef.adapters.RecipeAdapter;
import com.example.smartchef.database.FavoriteDatabase;
import com.example.smartchef.database.FavoriteRecipe;
import com.example.smartchef.models.Recipe;
import com.example.smartchef.utils.Constants;
import com.example.smartchef.utils.MockData;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class FavoritesFragment extends Fragment {

    private RecyclerView rvFavorites;
    private LinearLayout layoutEmptyState;
    private MaterialButton btnExploreRecipes;
    private RecipeAdapter adapter;
    private List<Recipe> favoriteRecipesList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);

        rvFavorites = view.findViewById(R.id.rv_favorites);
        layoutEmptyState = view.findViewById(R.id.layout_empty_favorites);
        btnExploreRecipes = view.findViewById(R.id.btn_explore_recipes);

        adapter = new RecipeAdapter(getContext(), favoriteRecipesList, new RecipeAdapter.OnRecipeClickListener() {
            @Override
            public void onRecipeClick(Recipe recipe) {
                Intent intent = new Intent(getContext(), RecipeDetailActivity.class);
                intent.putExtra(Constants.EXTRA_RECIPE, recipe);
                startActivity(intent);
            }

            @Override
            public void onFavoriteClick(Recipe recipe) {
                loadFavorites();
            }
        });
        rvFavorites.setAdapter(adapter);

        btnExploreRecipes.setOnClickListener(v -> {
            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).switchToTab(R.id.navigation_home);
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadFavorites();
    }

    private void loadFavorites() {
        favoriteRecipesList.clear();
        List<FavoriteRecipe> dbFavs = FavoriteDatabase.getInstance(getContext()).favoriteDao().getAllFavorites();
        List<Recipe> allMock = MockData.getPopularRecipes();

        if (dbFavs != null && !dbFavs.isEmpty()) {
            for (FavoriteRecipe favEntity : dbFavs) {
                Recipe match = null;
                for (Recipe r : allMock) {
                    if (r.getId().equalsIgnoreCase(favEntity.getId())) {
                        match = r;
                        break;
                    }
                }
                if (match == null) {
                    match = new Recipe(favEntity.getId(), favEntity.getTitle(), favEntity.getImageUrl(),
                            favEntity.getRating(), favEntity.getCookingTimeMinutes(), 4, favEntity.getDifficulty(), 100, "Main Course", favEntity.getCuisine(), "");
                }
                match.setFavorite(true);
                favoriteRecipesList.add(match);
            }
        }

        if (favoriteRecipesList.isEmpty()) {
            rvFavorites.setVisibility(View.GONE);
            layoutEmptyState.setVisibility(View.VISIBLE);
        } else {
            rvFavorites.setVisibility(View.VISIBLE);
            layoutEmptyState.setVisibility(View.GONE);
            adapter.updateList(favoriteRecipesList);
        }
    }
}
