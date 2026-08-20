package com.example.smartchef.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.smartchef.R;
import com.example.smartchef.models.Recipe;
import com.example.smartchef.utils.Constants;

public class RecipeOverviewFragment extends Fragment {

    public static RecipeOverviewFragment newInstance(Recipe recipe) {
        RecipeOverviewFragment fragment = new RecipeOverviewFragment();
        Bundle args = new Bundle();
        args.putSerializable(Constants.EXTRA_RECIPE, recipe);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_overview, container, false);

        TextView tvDesc = view.findViewById(R.id.tv_overview_description);
        TextView tvCalories = view.findViewById(R.id.tv_macro_calories);
        TextView tvProtein = view.findViewById(R.id.tv_macro_protein);
        TextView tvCarbs = view.findViewById(R.id.tv_macro_carbs);
        TextView tvFats = view.findViewById(R.id.tv_macro_fats);

        if (getArguments() != null) {
            Recipe recipe = (Recipe) getArguments().getSerializable(Constants.EXTRA_RECIPE);
            if (recipe != null) {
                tvDesc.setText(recipe.getDescription());
                tvCalories.setText(recipe.getCalories() + " kcal");
                tvProtein.setText(recipe.getProteinGrams() + " g");
                tvCarbs.setText(recipe.getCarbsGrams() + " g");
                tvFats.setText(recipe.getFatsGrams() + " g");
            }
        }

        return view;
    }
}
