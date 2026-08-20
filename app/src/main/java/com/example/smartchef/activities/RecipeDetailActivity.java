package com.example.smartchef.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.example.smartchef.R;
import com.example.smartchef.fragments.RecipeIngredientsFragment;
import com.example.smartchef.fragments.RecipeInstructionsFragment;
import com.example.smartchef.fragments.RecipeNutritionFragment;
import com.example.smartchef.fragments.RecipeOverviewFragment;
import com.example.smartchef.models.Recipe;
import com.example.smartchef.utils.Constants;
import com.example.smartchef.utils.FavoritesManager;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class RecipeDetailActivity extends AppCompatActivity {

    private ImageView ivHero, btnBack, btnFavorite;
    private TextView tvTitle, tvRating, tvTime, tvServings;
    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    private MaterialButton btnStartCooking;

    private Recipe recipe;
    private FavoritesManager favoritesManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        favoritesManager = FavoritesManager.getInstance(this);

        ivHero = findViewById(R.id.iv_detail_hero_image);
        btnBack = findViewById(R.id.btn_detail_back);
        btnFavorite = findViewById(R.id.btn_detail_favorite);
        tvTitle = findViewById(R.id.tv_detail_title);
        tvRating = findViewById(R.id.tv_detail_rating);
        tvTime = findViewById(R.id.tv_detail_time);
        tvServings = findViewById(R.id.tv_detail_servings);
        tabLayout = findViewById(R.id.tab_layout_recipe);
        viewPager = findViewById(R.id.view_pager_tabs);
        btnStartCooking = findViewById(R.id.btn_start_cooking_mode);

        recipe = (Recipe) getIntent().getSerializableExtra(Constants.EXTRA_RECIPE);

        btnBack.setOnClickListener(v -> finish());

        if (recipe != null) {
            tvTitle.setText(recipe.getTitle());
            tvRating.setText(String.valueOf(recipe.getRating()));
            tvTime.setText(recipe.getCookingTimeMinutes() + " mins");
            tvServings.setText(recipe.getServings() + " servings");

            Glide.with(this)
                    .load(recipe.getImageUrl())
                    .placeholder(R.drawable.bg_pill_indicator)
                    .into(ivHero);

            updateFavoriteIcon();

            btnFavorite.setOnClickListener(v -> {
                boolean isFav = favoritesManager.toggleFavorite(recipe);
                updateFavoriteIcon();
                Toast.makeText(this, isFav ? "Saved to Favorites ❤️" : "Removed from Favorites", Toast.LENGTH_SHORT).show();
            });

            setupViewPagerAndTabs();

            btnStartCooking.setOnClickListener(v -> {
                Intent intent = new Intent(RecipeDetailActivity.this, CookingModeActivity.class);
                intent.putExtra(Constants.EXTRA_RECIPE, recipe);
                startActivity(intent);
            });
        }
    }

    private void updateFavoriteIcon() {
        boolean isFav = favoritesManager.isFavorite(recipe.getId());
        btnFavorite.setImageResource(isFav ? R.drawable.ic_heart_filled : R.drawable.ic_heart_outline);
    }

    private void setupViewPagerAndTabs() {
        RecipeTabsPagerAdapter adapter = new RecipeTabsPagerAdapter(this, recipe);
        viewPager.setAdapter(adapter);

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText(R.string.tab_overview);
                    break;
                case 1:
                    tab.setText(R.string.tab_ingredients);
                    break;
                case 2:
                    tab.setText(R.string.tab_instructions);
                    break;
                case 3:
                    tab.setText(R.string.tab_nutrition);
                    break;
            }
        }).attach();
    }

    private static class RecipeTabsPagerAdapter extends FragmentStateAdapter {
        private final Recipe recipe;

        public RecipeTabsPagerAdapter(@NonNull FragmentActivity fragmentActivity, Recipe recipe) {
            super(fragmentActivity);
            this.recipe = recipe;
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position) {
                case 0:
                    return RecipeOverviewFragment.newInstance(recipe);
                case 1:
                    return RecipeIngredientsFragment.newInstance(recipe);
                case 2:
                    return RecipeInstructionsFragment.newInstance(recipe);
                case 3:
                    return RecipeNutritionFragment.newInstance(recipe);
                default:
                    return RecipeOverviewFragment.newInstance(recipe);
            }
        }

        @Override
        public int getItemCount() {
            return 4;
        }
    }
}
