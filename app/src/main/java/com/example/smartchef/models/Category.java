package com.example.smartchef.models;

import java.io.Serializable;

public class Category implements Serializable {
    private String id;
    private String name;
    private String imageUrl;
    private int iconResId;

    public Category(String id, String name, String imageUrl, int iconResId) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.iconResId = iconResId;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public int getIconResId() { return iconResId; }
    public void setIconResId(int iconResId) { this.iconResId = iconResId; }
}
