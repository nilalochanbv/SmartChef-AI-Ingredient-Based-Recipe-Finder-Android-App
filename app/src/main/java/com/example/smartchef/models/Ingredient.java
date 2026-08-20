package com.example.smartchef.models;

import java.io.Serializable;

public class Ingredient implements Serializable {
    private String id;
    private String name;
    private String category;
    private boolean isAvailable;
    private boolean isSelected;
    private String amount;

    public Ingredient(String id, String name, String category, boolean isAvailable) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.isAvailable = isAvailable;
        this.isSelected = false;
        this.amount = "";
    }

    public Ingredient(String name, String amount, boolean isAvailable) {
        this.id = name.toLowerCase().replaceAll("\\s+", "_");
        this.name = name;
        this.category = "General";
        this.isAvailable = isAvailable;
        this.isSelected = false;
        this.amount = amount;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public boolean isAvailable() { return isAvailable; }
    public void setAvailable(boolean available) { isAvailable = available; }

    public boolean isSelected() { return isSelected; }
    public void setSelected(boolean selected) { isSelected = selected; }

    public String getAmount() { return amount; }
    public void setAmount(String amount) { this.amount = amount; }
}
