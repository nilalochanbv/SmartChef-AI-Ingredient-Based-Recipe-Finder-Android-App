package com.example.smartchef.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartchef.R;
import com.example.smartchef.activities.RecipeResultsActivity;
import com.example.smartchef.adapters.BasketAdapter;
import com.example.smartchef.models.Ingredient;
import com.example.smartchef.utils.Constants;
import com.example.smartchef.utils.MockData;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.List;

public class IngredientsFragment extends Fragment {

    private ChipGroup cgVegetables, cgProtein, cgGrains;
    private TextView tvBasketHeader;
    private RecyclerView rvBasketItems;
    private MaterialButton btnFindRecipes;

    private final List<Ingredient> selectedBasketIngredients = new ArrayList<>();
    private BasketAdapter basketAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ingredients, container, false);

        cgVegetables = view.findViewById(R.id.cg_vegetables);
        cgProtein = view.findViewById(R.id.cg_protein);
        cgGrains = view.findViewById(R.id.cg_grains);
        tvBasketHeader = view.findViewById(R.id.tv_basket_header);
        rvBasketItems = view.findViewById(R.id.rv_basket_items);
        btnFindRecipes = view.findViewById(R.id.btn_find_recipes);

        setupBasketRecyclerView();
        populateCategorizedChips();
        setupCtaButton();

        return view;
    }

    private void setupBasketRecyclerView() {
        basketAdapter = new BasketAdapter(getContext(), selectedBasketIngredients, ingredient -> {
            ingredient.setSelected(false);
            selectedBasketIngredients.remove(ingredient);
            updateBasketState();
            refreshChipStates();
        });
        rvBasketItems.setAdapter(basketAdapter);
    }

    private void populateCategorizedChips() {
        List<Ingredient> allIngredients = MockData.getAllCategorizedIngredients();

        for (Ingredient ing : allIngredients) {
            Chip chip = new Chip(requireContext());
            chip.setText(ing.getName());
            chip.setCheckable(true);
            chip.setClickable(true);
            chip.setChipBackgroundColorResource(R.color.chip_unselected);
            chip.setTextColor(getResources().getColor(R.color.text_primary, null));

            chip.setOnCheckedChangeListener((buttonView, isChecked) -> {
                ing.setSelected(isChecked);
                if (isChecked) {
                    chip.setChipBackgroundColorResource(R.color.primary_orange);
                    chip.setTextColor(getResources().getColor(R.color.white, null));
                    if (!selectedBasketIngredients.contains(ing)) {
                        selectedBasketIngredients.add(ing);
                    }
                } else {
                    chip.setChipBackgroundColorResource(R.color.chip_unselected);
                    chip.setTextColor(getResources().getColor(R.color.text_primary, null));
                    selectedBasketIngredients.remove(ing);
                }
                updateBasketState();
            });

            if ("Vegetables".equalsIgnoreCase(ing.getCategory())) {
                cgVegetables.addView(chip);
            } else if ("Protein".equalsIgnoreCase(ing.getCategory())) {
                cgProtein.addView(chip);
            } else {
                cgGrains.addView(chip);
            }
        }
    }

    private void updateBasketState() {
        int count = selectedBasketIngredients.size();
        tvBasketHeader.setText("Kitchen Basket (" + count + " item" + (count == 1 ? "" : "s") + ")");
        basketAdapter.notifyDataSetChanged();
    }

    private void refreshChipStates() {
        // Uncheck chips in chip groups if item was removed from basket
        uncheckMatchingChips(cgVegetables);
        uncheckMatchingChips(cgProtein);
        uncheckMatchingChips(cgGrains);
    }

    private void uncheckMatchingChips(ChipGroup group) {
        for (int i = 0; i < group.getChildCount(); i++) {
            Chip chip = (Chip) group.getChildAt(i);
            boolean inBasket = false;
            for (Ingredient ing : selectedBasketIngredients) {
                if (ing.getName().equalsIgnoreCase(chip.getText().toString())) {
                    inBasket = true;
                    break;
                }
            }
            if (!inBasket && chip.isChecked()) {
                chip.setChecked(false);
            }
        }
    }

    private void setupCtaButton() {
        btnFindRecipes.setOnClickListener(v -> {
            if (selectedBasketIngredients.isEmpty()) {
                Toast.makeText(getContext(), "Please select at least one ingredient!", Toast.LENGTH_SHORT).show();
                return;
            }

            Intent intent = new Intent(getContext(), RecipeResultsActivity.class);
            ArrayList<String> ingNames = new ArrayList<>();
            for (Ingredient ing : selectedBasketIngredients) {
                ingNames.add(ing.getName());
            }
            intent.putStringArrayListExtra(Constants.EXTRA_INGREDIENTS_LIST, ingNames);
            startActivity(intent);
        });
    }
}
