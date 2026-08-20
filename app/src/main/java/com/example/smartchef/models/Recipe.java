package com.example.smartchef.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Recipe implements Serializable {
    @SerializedName("id")
    private String id;
    
    @SerializedName("title")
    private String title;
    
    @SerializedName("image")
    private String imageUrl;
    
    @SerializedName("spoonacularScore")
    private double rating;
    
    @SerializedName("readyInMinutes")
    private int cookingTimeMinutes;
    
    @SerializedName("servings")
    private int servings;
    
    private String difficulty;
    private int matchPercentage;
    private boolean isFavorite;
    private String category;
    private String cuisine;
    
    @SerializedName("extendedIngredients")
    private List<Ingredient> ingredients;
    
    private List<String> missingIngredients;
    
    @SerializedName("analyzedInstructions")
    private List<InstructionStep> instructionSteps;
    
    private int calories;
    private int proteinGrams;
    private int carbsGrams;
    private int fatsGrams;
    private String description;

    public Recipe() {
        this.ingredients = new ArrayList<>();
        this.missingIngredients = new ArrayList<>();
        this.instructionSteps = new ArrayList<>();
    }

    public Recipe(String id, String title, String imageUrl, double rating, int cookingTimeMinutes,
                  int servings, String difficulty, int matchPercentage, String category, String cuisine, String description) {
        this.id = id;
        this.title = title;
        this.imageUrl = imageUrl;
        this.rating = rating;
        this.cookingTimeMinutes = cookingTimeMinutes;
        this.servings = servings;
        this.difficulty = difficulty;
        this.matchPercentage = matchPercentage;
        this.isFavorite = false;
        this.category = category;
        this.cuisine = cuisine;
        this.description = description;
        this.ingredients = new ArrayList<>();
        this.missingIngredients = new ArrayList<>();
        this.instructionSteps = new ArrayList<>();
        this.calories = 450;
        this.proteinGrams = 28;
        this.carbsGrams = 42;
        this.fatsGrams = 18;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public double getRating() { return rating; }
    public void setRating(double rating) { this.rating = rating; }

    public int getCookingTimeMinutes() { return cookingTimeMinutes; }
    public void setCookingTimeMinutes(int cookingTimeMinutes) { this.cookingTimeMinutes = cookingTimeMinutes; }

    public int getServings() { return servings; }
    public void setServings(int servings) { this.servings = servings; }

    public String getDifficulty() { return difficulty; }
    public void setDifficulty(String difficulty) { this.difficulty = difficulty; }

    public int getMatchPercentage() { return matchPercentage; }
    public void setMatchPercentage(int matchPercentage) { this.matchPercentage = matchPercentage; }

    public boolean isFavorite() { return isFavorite; }
    public void setFavorite(boolean favorite) { isFavorite = favorite; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getCuisine() { return cuisine; }
    public void setCuisine(String cuisine) { this.cuisine = cuisine; }

    public List<Ingredient> getIngredients() { return ingredients; }
    public void setIngredients(List<Ingredient> ingredients) { this.ingredients = ingredients; }

    public List<String> getMissingIngredients() { return missingIngredients; }
    public void setMissingIngredients(List<String> missingIngredients) { this.missingIngredients = missingIngredients; }

    public List<InstructionStep> getInstructionSteps() { return instructionSteps; }
    public void setInstructionSteps(List<InstructionStep> instructionSteps) { this.instructionSteps = instructionSteps; }

    public int getCalories() { return calories; }
    public void setCalories(int calories) { this.calories = calories; }

    public int getProteinGrams() { return proteinGrams; }
    public void setProteinGrams(int proteinGrams) { this.proteinGrams = proteinGrams; }

    public int getCarbsGrams() { return carbsGrams; }
    public void setCarbsGrams(int carbsGrams) { this.carbsGrams = carbsGrams; }

    public int getFatsGrams() { return fatsGrams; }
    public void setFatsGrams(int fatsGrams) { this.fatsGrams = fatsGrams; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
