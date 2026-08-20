package com.example.smartchef.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartchef.R;
import com.example.smartchef.activities.MainActivity;
import com.example.smartchef.activities.RecipeDetailActivity;
import com.example.smartchef.activities.RecipeResultsActivity;
import com.example.smartchef.adapters.CuisineAdapter;
import com.example.smartchef.adapters.IngredientAdapter;
import com.example.smartchef.adapters.PopularRecipeAdapter;
import com.example.smartchef.models.Category;
import com.example.smartchef.models.Ingredient;
import com.example.smartchef.models.Recipe;
import com.example.smartchef.utils.Constants;
import com.example.smartchef.utils.MockData;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class HomeFragment extends Fragment {

    private TextView tvGreeting;
    private LinearLayout layoutSearchBar;
    private RecyclerView rvQuickIngredients, rvPopularRecipes, rvExploreCuisines;

    private List<Ingredient> selectedQuickIngredients = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        tvGreeting = view.findViewById(R.id.tv_greeting);
        layoutSearchBar = view.findViewById(R.id.layout_search_bar);
        rvQuickIngredients = view.findViewById(R.id.rv_quick_ingredients);
        rvPopularRecipes = view.findViewById(R.id.rv_popular_recipes);
        rvExploreCuisines = view.findViewById(R.id.rv_explore_cuisines);

        setupGreeting();
        setupSearch();
        setupQuickIngredients();
        setupPopularRecipes();
        setupExploreCuisines();

        return view;
    }

    private void setupGreeting() {
        int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        if (hour >= 4 && hour < 12) {
            tvGreeting.setText(R.string.greeting_morning);
        } else if (hour >= 12 && hour < 17) {
            tvGreeting.setText(R.string.greeting_afternoon);
        } else {
            tvGreeting.setText(R.string.greeting_evening);
        }
    }

    private void setupSearch() {
        layoutSearchBar.setOnClickListener(v -> {
            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).switchToTab(R.id.navigation_search);
            }
        });
    }

    private void setupQuickIngredients() {
        List<Ingredient> quickList = MockData.getQuickIngredients();
        IngredientAdapter adapter = new IngredientAdapter(getContext(), quickList, (ingredient, isSelected) -> {
            if (isSelected) {
                if (!selectedQuickIngredients.contains(ingredient)) {
                    selectedQuickIngredients.add(ingredient);
                }
            } else {
                selectedQuickIngredients.remove(ingredient);
            }

            if (!selectedQuickIngredients.isEmpty()) {
                Intent intent = new Intent(getContext(), RecipeResultsActivity.class);
                ArrayList<String> ingNames = new ArrayList<>();
                for (Ingredient ing : selectedQuickIngredients) {
                    ingNames.add(ing.getName());
                }
                intent.putStringArrayListExtra(Constants.EXTRA_INGREDIENTS_LIST, ingNames);
                startActivity(intent);
            }
        });
        rvQuickIngredients.setAdapter(adapter);
    }

    private void setupPopularRecipes() {
        List<Recipe> popularRecipes = MockData.getPopularRecipes();
        PopularRecipeAdapter adapter = new PopularRecipeAdapter(getContext(), popularRecipes, new PopularRecipeAdapter.OnRecipeClickListener() {
            @Override
            public void onRecipeClick(Recipe recipe) {
                Intent intent = new Intent(getContext(), RecipeDetailActivity.class);
                intent.putExtra(Constants.EXTRA_RECIPE, recipe);
                startActivity(intent);
            }

            @Override
            public void onFavoriteClick(Recipe recipe) {
                Toast.makeText(getContext(), recipe.isFavorite() ? "Added to Favorites" : "Removed from Favorites", Toast.LENGTH_SHORT).show();
            }
        });
        rvPopularRecipes.setAdapter(adapter);
    }

    private void setupExploreCuisines() {
        List<Category> cuisines = MockData.getCuisines();
        CuisineAdapter adapter = new CuisineAdapter(getContext(), cuisines, cuisine -> {
            Intent intent = new Intent(getContext(), RecipeResultsActivity.class);
            intent.putExtra(Constants.EXTRA_CUISINE_FILTER, cuisine.getName());
            startActivity(intent);
        });
        rvExploreCuisines.setAdapter(adapter);
    }
}
