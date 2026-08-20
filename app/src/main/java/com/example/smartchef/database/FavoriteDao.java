package com.example.smartchef.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface FavoriteDao {

    @Query("SELECT * FROM favorite_recipes ORDER BY addedTimestamp DESC")
    List<FavoriteRecipe> getAllFavorites();

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_recipes WHERE id = :recipeId LIMIT 1)")
    boolean isFavorite(String recipeId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertFavorite(FavoriteRecipe recipe);

    @Query("DELETE FROM favorite_recipes WHERE id = :recipeId")
    void deleteFavorite(String recipeId);
}
