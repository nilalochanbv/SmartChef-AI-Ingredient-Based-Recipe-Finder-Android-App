package com.example.smartchef.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.smartchef.database.FavoriteDatabase;
import com.example.smartchef.database.FavoriteRecipe;
import com.example.smartchef.models.Recipe;

import java.util.HashSet;
import java.util.Set;

public class FavoritesManager {

    private static FavoritesManager instance;
    private final SharedPreferences prefs;
    private final FavoriteDatabase roomDb;

    private FavoritesManager(Context context) {
        prefs = context.getApplicationContext().getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE);
        roomDb = FavoriteDatabase.getInstance(context);
    }

    public static synchronized FavoritesManager getInstance(Context context) {
        if (instance == null) {
            instance = new FavoritesManager(context);
        }
        return instance;
    }

    public boolean isFavorite(String recipeId) {
        if (recipeId == null) return false;
        Set<String> set = prefs.getStringSet(Constants.KEY_FAVORITES_SET, new HashSet<>());
        return set.contains(recipeId) || roomDb.favoriteDao().isFavorite(recipeId);
    }

    public boolean toggleFavorite(Recipe recipe) {
        if (recipe == null || recipe.getId() == null) return false;
        boolean currentlyFavorite = isFavorite(recipe.getId());

        Set<String> set = new HashSet<>(prefs.getStringSet(Constants.KEY_FAVORITES_SET, new HashSet<>()));

        if (currentlyFavorite) {
            set.remove(recipe.getId());
            recipe.setFavorite(false);
            roomDb.favoriteDao().deleteFavorite(recipe.getId());
        } else {
            set.add(recipe.getId());
            recipe.setFavorite(true);
            FavoriteRecipe entity = new FavoriteRecipe(
                    recipe.getId(),
                    recipe.getTitle(),
                    recipe.getImageUrl(),
                    recipe.getRating(),
                    recipe.getCookingTimeMinutes(),
                    recipe.getDifficulty(),
                    recipe.getCuisine(),
                    System.currentTimeMillis()
            );
            roomDb.favoriteDao().insertFavorite(entity);
        }

        prefs.edit().putStringSet(Constants.KEY_FAVORITES_SET, set).apply();
        return !currentlyFavorite;
    }
}
