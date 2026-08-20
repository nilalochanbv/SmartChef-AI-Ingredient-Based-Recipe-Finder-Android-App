package com.example.smartchef.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartchef.R;
import com.example.smartchef.activities.RecipeDetailActivity;
import com.example.smartchef.adapters.RecipeAdapter;
import com.example.smartchef.models.Recipe;
import com.example.smartchef.utils.Constants;
import com.example.smartchef.utils.MockData;
import com.google.android.material.chip.Chip;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {

    private EditText etSearchInput;
    private RecyclerView rvSearchResults;
    private RecipeAdapter adapter;
    private List<Recipe> allRecipes;
    private List<Recipe> filteredRecipes = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        etSearchInput = view.findViewById(R.id.et_search_input);
        rvSearchResults = view.findViewById(R.id.rv_search_results);

        Chip chip1 = view.findViewById(R.id.chip_recent_1);
        Chip chip2 = view.findViewById(R.id.chip_recent_2);
        Chip chip3 = view.findViewById(R.id.chip_recent_3);
        Chip chip4 = view.findViewById(R.id.chip_recent_4);

        allRecipes = MockData.getPopularRecipes();
        filteredRecipes.addAll(allRecipes);

        adapter = new RecipeAdapter(getContext(), filteredRecipes, new RecipeAdapter.OnRecipeClickListener() {
            @Override
            public void onRecipeClick(Recipe recipe) {
                Intent intent = new Intent(getContext(), RecipeDetailActivity.class);
                intent.putExtra(Constants.EXTRA_RECIPE, recipe);
                startActivity(intent);
            }

            @Override
            public void onFavoriteClick(Recipe recipe) {}
        });
        rvSearchResults.setAdapter(adapter);

        etSearchInput.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterSearch(s.toString());
            }
            @Override public void afterTextChanged(Editable s) {}
        });

        View.OnClickListener chipListener = v -> {
            if (v instanceof Chip) {
                String query = ((Chip) v).getText().toString();
                etSearchInput.setText(query);
                etSearchInput.setSelection(query.length());
            }
        };

        chip1.setOnClickListener(chipListener);
        chip2.setOnClickListener(chipListener);
        chip3.setOnClickListener(chipListener);
        chip4.setOnClickListener(chipListener);

        return view;
    }

    private void filterSearch(String query) {
        filteredRecipes.clear();
        if (query == null || query.trim().isEmpty()) {
            filteredRecipes.addAll(allRecipes);
        } else {
            String lower = query.toLowerCase().trim();
            for (Recipe r : allRecipes) {
                if (r.getTitle().toLowerCase().contains(lower) ||
                    r.getCuisine().toLowerCase().contains(lower) ||
                    r.getCategory().toLowerCase().contains(lower)) {
                    filteredRecipes.add(r);
                }
            }
        }
        adapter.updateList(filteredRecipes);
    }
}
