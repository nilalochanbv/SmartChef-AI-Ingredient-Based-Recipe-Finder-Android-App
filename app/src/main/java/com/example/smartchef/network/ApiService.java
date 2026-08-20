package com.example.smartchef.network;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("recipes/complexSearch")
    Call<RecipeSearchResponse> searchRecipes(
            @Query("apiKey") String apiKey,
            @Query("query") String query,
            @Query("includeIngredients") String ingredients,
            @Query("cuisine") String cuisine,
            @Query("number") int number
    );
}
