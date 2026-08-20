package com.example.smartchef.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ShoppingListManager {

    private static ShoppingListManager instance;
    private final SharedPreferences prefs;

    private ShoppingListManager(Context context) {
        prefs = context.getApplicationContext().getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE);
    }

    public static synchronized ShoppingListManager getInstance(Context context) {
        if (instance == null) {
            instance = new ShoppingListManager(context);
        }
        return instance;
    }

    public void addMissingIngredients(List<String> missingItems) {
        if (missingItems == null || missingItems.isEmpty()) return;
        Set<String> set = new HashSet<>(prefs.getStringSet(Constants.KEY_SHOPPING_LIST_SET, new HashSet<>()));
        set.addAll(missingItems);
        prefs.edit().putStringSet(Constants.KEY_SHOPPING_LIST_SET, set).apply();
    }

    public Set<String> getShoppingList() {
        return prefs.getStringSet(Constants.KEY_SHOPPING_LIST_SET, new HashSet<>());
    }
}
