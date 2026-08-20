package com.example.smartchef.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartchef.R;
import com.example.smartchef.adapters.RecipeAdapter;
import com.example.smartchef.models.Recipe;
import com.example.smartchef.utils.Constants;
import com.example.smartchef.utils.MockData;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.List;

public class RecipeResultsActivity extends AppCompatActivity {

    private ImageView btnBack;
    private TextView tvSummary;
    private ChipGroup cgFilters;
    private RecyclerView rvResults;
    private RecipeAdapter adapter;

    private List<Recipe> originalRecipes;
    private List<Recipe> displayedRecipes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_results);

        btnBack = findViewById(R.id.btn_back);
        tvSummary = findViewById(R.id.tv_selected_ingredients_summary);
        cgFilters = findViewById(R.id.cg_recipe_filters);
        rvResults = findViewById(R.id.rv_recipe_results);

        btnBack.setOnClickListener(v -> finish());

        ArrayList<String> selectedIngs = getIntent().getStringArrayListExtra(Constants.EXTRA_INGREDIENTS_LIST);
        String cuisineFilter = getIntent().getStringExtra(Constants.EXTRA_CUISINE_FILTER);

        if (selectedIngs != null && !selectedIngs.isEmpty()) {
            StringBuilder sb = new StringBuilder("Based on: ");
            for (int i = 0; i < selectedIngs.size(); i++) {
                sb.append(selectedIngs.get(i));
                if (i < selectedIngs.size() - 1) sb.append(", ");
            }
            tvSummary.setText(sb.toString());
            // Dynamically calculate match percentage & sort by highest match!
            originalRecipes = MockData.matchRecipesByIngredients(selectedIngs);
        } else if (cuisineFilter != null) {
            tvSummary.setText("Explore " + cuisineFilter + " Cuisine");
            originalRecipes = MockData.getPopularRecipes();
            List<Recipe> filteredByCuisine = new ArrayList<>();
            for (Recipe r : originalRecipes) {
                if (r.getCuisine().equalsIgnoreCase(cuisineFilter)) {
                    filteredByCuisine.add(r);
                }
            }
            if (!filteredByCuisine.isEmpty()) {
                originalRecipes = filteredByCuisine;
            }
        } else {
            tvSummary.setText("Discovered Recipes");
            originalRecipes = MockData.getPopularRecipes();
        }

        displayedRecipes.addAll(originalRecipes);

        adapter = new RecipeAdapter(this, displayedRecipes, new RecipeAdapter.OnRecipeClickListener() {
            @Override
            public void onRecipeClick(Recipe recipe) {
                Intent intent = new Intent(RecipeResultsActivity.this, RecipeDetailActivity.class);
                intent.putExtra(Constants.EXTRA_RECIPE, recipe);
                startActivity(intent);
            }

            @Override
            public void onFavoriteClick(Recipe recipe) {}
        });
        rvResults.setAdapter(adapter);

        setupFilterChips();
    }

    private void setupFilterChips() {
        cgFilters.setOnCheckedStateChangeListener((group, checkedIds) -> {
            if (checkedIds.isEmpty()) return;
            int id = checkedIds.get(0);

            displayedRecipes.clear();
            if (id == R.id.filter_under_20) {
                for (Recipe r : originalRecipes) {
                    if (r.getCookingTimeMinutes() <= 20) displayedRecipes.add(r);
                }
            } else if (id == R.id.filter_easy) {
                for (Recipe r : originalRecipes) {
                    if ("Easy".equalsIgnoreCase(r.getDifficulty())) displayedRecipes.add(r);
                }
            } else if (id == R.id.filter_veg) {
                for (Recipe r : originalRecipes) {
                    if ("Healthy".equalsIgnoreCase(r.getCuisine()) || "Italian".equalsIgnoreCase(r.getCuisine()) || r.getTitle().toLowerCase().contains("paneer") || r.getTitle().toLowerCase().contains("pasta") || r.getTitle().toLowerCase().contains("potato")) {
                        displayedRecipes.add(r);
                    }
                }
            } else if (id == R.id.filter_high_protein) {
                for (Recipe r : originalRecipes) {
                    if (r.getProteinGrams() >= 20) displayedRecipes.add(r);
                }
            } else {
                displayedRecipes.addAll(originalRecipes);
            }
            adapter.updateList(displayedRecipes);
        });
    }
}
