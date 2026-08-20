package com.example.smartchef.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartchef.R;
import com.example.smartchef.adapters.InstructionStepAdapter;
import com.example.smartchef.models.Recipe;
import com.example.smartchef.utils.Constants;

public class RecipeInstructionsFragment extends Fragment {

    public static RecipeInstructionsFragment newInstance(Recipe recipe) {
        RecipeInstructionsFragment fragment = new RecipeInstructionsFragment();
        Bundle args = new Bundle();
        args.putSerializable(Constants.EXTRA_RECIPE, recipe);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_instructions, container, false);

        RecyclerView rvInstructions = view.findViewById(R.id.rv_instructions_timeline);

        if (getArguments() != null) {
            Recipe recipe = (Recipe) getArguments().getSerializable(Constants.EXTRA_RECIPE);
            if (recipe != null && recipe.getInstructionSteps() != null) {
                InstructionStepAdapter adapter = new InstructionStepAdapter(getContext(), recipe.getInstructionSteps());
                rvInstructions.setAdapter(adapter);
            }
        }

        return view;
    }
}
