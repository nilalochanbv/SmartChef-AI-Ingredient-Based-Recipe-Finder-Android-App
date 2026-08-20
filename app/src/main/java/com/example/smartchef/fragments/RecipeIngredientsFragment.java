package com.example.smartchef.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartchef.R;
import com.example.smartchef.adapters.IngredientDetailAdapter;
import com.example.smartchef.models.Recipe;
import com.example.smartchef.utils.Constants;
import com.example.smartchef.utils.ShoppingListManager;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class RecipeIngredientsFragment extends Fragment {

    public static RecipeIngredientsFragment newInstance(Recipe recipe) {
        RecipeIngredientsFragment fragment = new RecipeIngredientsFragment();
        Bundle args = new Bundle();
        args.putSerializable(Constants.EXTRA_RECIPE, recipe);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_ingredients, container, false);

        RecyclerView rvIngredients = view.findViewById(R.id.rv_ingredients_detail);
        MaterialButton btnAddMissing = view.findViewById(R.id.btn_add_missing_shopping);

        if (getArguments() != null) {
            Recipe recipe = (Recipe) getArguments().getSerializable(Constants.EXTRA_RECIPE);
            if (recipe != null) {
                IngredientDetailAdapter adapter = new IngredientDetailAdapter(getContext(), recipe.getIngredients());
                rvIngredients.setAdapter(adapter);

                btnAddMissing.setOnClickListener(v -> {
                    List<String> missing = recipe.getMissingIngredients();
                    if (missing != null && !missing.isEmpty()) {
                        ShoppingListManager.getInstance(requireContext()).addMissingIngredients(missing);
                        Toast.makeText(getContext(), "Added " + missing.size() + " missing item(s) to your Shopping List! 🛒", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "You already have all ingredients for this recipe! 🎉", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }

        return view;
    }
}
