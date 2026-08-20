package com.example.smartchef.utils;

import com.example.smartchef.R;
import com.example.smartchef.models.Category;
import com.example.smartchef.models.Ingredient;
import com.example.smartchef.models.InstructionStep;
import com.example.smartchef.models.Recipe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MockData {

    public static List<Ingredient> getQuickIngredients() {
        List<Ingredient> list = new ArrayList<>();
        list.add(new Ingredient("ing_1", "Chicken", "Protein", true));
        list.add(new Ingredient("ing_2", "Egg", "Protein", true));
        list.add(new Ingredient("ing_3", "Tomato", "Vegetables", true));
        list.add(new Ingredient("ing_4", "Potato", "Vegetables", true));
        list.add(new Ingredient("ing_5", "Paneer", "Protein", true));
        list.add(new Ingredient("ing_6", "Rice", "Grains", true));
        list.add(new Ingredient("ing_7", "Onion", "Vegetables", true));
        list.add(new Ingredient("ing_8", "Carrot", "Vegetables", true));
        return list;
    }

    public static List<Ingredient> getAllCategorizedIngredients() {
        List<Ingredient> list = new ArrayList<>();
        // Vegetables
        list.add(new Ingredient("ing_v1", "Tomato", "Vegetables", true));
        list.add(new Ingredient("ing_v2", "Onion", "Vegetables", true));
        list.add(new Ingredient("ing_v3", "Potato", "Vegetables", true));
        list.add(new Ingredient("ing_v4", "Carrot", "Vegetables", true));
        list.add(new Ingredient("ing_v5", "Capsicum", "Vegetables", true));
        list.add(new Ingredient("ing_v6", "Garlic", "Vegetables", true));
        list.add(new Ingredient("ing_v7", "Spinach", "Vegetables", true));
        
        // Protein
        list.add(new Ingredient("ing_p1", "Chicken", "Protein", true));
        list.add(new Ingredient("ing_p2", "Egg", "Protein", true));
        list.add(new Ingredient("ing_p3", "Paneer", "Protein", true));
        list.add(new Ingredient("ing_p4", "Fish", "Protein", true));
        
        // Grains
        list.add(new Ingredient("ing_g1", "Rice", "Grains", true));
        list.add(new Ingredient("ing_g2", "Pasta", "Grains", true));
        list.add(new Ingredient("ing_g3", "Bread", "Grains", true));
        list.add(new Ingredient("ing_g4", "Flour", "Grains", true));
        
        return list;
    }

    public static List<Category> getCuisines() {
        List<Category> list = new ArrayList<>();
        list.add(new Category("c_1", "Indian", "https://images.unsplash.com/photo-1589301760014-d929f3979dbc?w=600&q=80", R.drawable.ic_utensils));
        list.add(new Category("c_2", "Italian", "https://images.unsplash.com/photo-1551183053-bf91a1d81141?w=600&q=80", R.drawable.ic_utensils));
        list.add(new Category("c_3", "Chinese", "https://images.unsplash.com/photo-1563245372-f21724e3856d?w=600&q=80", R.drawable.ic_utensils));
        list.add(new Category("c_4", "Mexican", "https://images.unsplash.com/photo-1565299585323-38d6b0865b47?w=600&q=80", R.drawable.ic_utensils));
        list.add(new Category("c_5", "Japanese", "https://images.unsplash.com/photo-1611143669185-af224c5e3252?w=600&q=80", R.drawable.ic_utensils));
        list.add(new Category("c_6", "Healthy", "https://images.unsplash.com/photo-1512621776951-a57141f2eefd?w=600&q=80", R.drawable.ic_utensils));
        return list;
    }

    public static List<Recipe> getPopularRecipes() {
        List<Recipe> recipes = new ArrayList<>();

        // Butter Chicken
        Recipe r1 = new Recipe("rec_1", "Butter Chicken", 
                "https://images.unsplash.com/photo-1588166524941-3bf61a9c41db?w=800&q=80", 
                4.8, 45, 4, "Easy", 95, "Main Course", "Indian",
                "Rich and creamy North Indian curry crafted with succulent tender chicken chunks steeped in a velvety butter, tomato, and cashew nut gravy.");
        r1.getIngredients().add(new Ingredient("Chicken", "500g", true));
        r1.getIngredients().add(new Ingredient("Tomato", "4 medium", true));
        r1.getIngredients().add(new Ingredient("Onion", "2 medium", true));
        r1.getIngredients().add(new Ingredient("Butter", "50g", true));
        r1.getIngredients().add(new Ingredient("Fresh Cream", "1/2 cup", false));
        r1.getIngredients().add(new Ingredient("Garam Masala", "1 tsp", true));
        r1.getMissingIngredients().add("Fresh Cream");
        r1.setInstructionSteps(Arrays.asList(
                new InstructionStep(1, "Marinate chicken chunks with yogurt, lemon juice, and red chili powder for 30 mins.", 300),
                new InstructionStep(2, "Heat butter in a large pan and sear marinated chicken pieces until golden brown.", 300),
                new InstructionStep(3, "In the same pan, sauté chopped onions and garlic until translucent and fragrant.", 240),
                new InstructionStep(4, "Add tomato puree, cashew paste, and garam masala; simmer gently for 15 minutes.", 450),
                new InstructionStep(5, "Stir in fresh cream, add grilled chicken back to the gravy, and cook for 5 minutes.", 300),
                new InstructionStep(6, "Garnish with fresh cilantro leaves and a swirl of cream. Serve hot with Naan!", 60)
        ));
        r1.setCalories(540);
        r1.setProteinGrams(38);
        r1.setCarbsGrams(18);
        r1.setFatsGrams(32);
        recipes.add(r1);

        // Chicken Fried Rice
        Recipe r2 = new Recipe("rec_2", "Chicken Fried Rice", 
                "https://images.unsplash.com/photo-1603133872878-684f208fb84b?w=800&q=80", 
                4.8, 25, 2, "Easy", 95, "Main Course", "Chinese",
                "Flavorful Indo-Chinese style fried rice packed with diced chicken, crisp wok-tossed vegetables, and aromatic soy seasoning.");
        r2.getIngredients().add(new Ingredient("Chicken", "250g", true));
        r2.getIngredients().add(new Ingredient("Rice", "2 cups cooked", true));
        r2.getIngredients().add(new Ingredient("Onion", "1 medium", true));
        r2.getIngredients().add(new Ingredient("Egg", "2 pcs", true));
        r2.getIngredients().add(new Ingredient("Soy Sauce", "2 tbsp", true));
        r2.getIngredients().add(new Ingredient("Coriander", "1 sprig", false));
        r2.getMissingIngredients().add("Coriander");
        r2.setInstructionSteps(Arrays.asList(
                new InstructionStep(1, "Heat sesame oil in a high-heat wok until lightly smoking.", 120),
                new InstructionStep(2, "Scramble eggs quickly and push them to one side of the wok.", 180),
                new InstructionStep(3, "Add diced chicken and stir-fry briskly for 4-5 minutes until cooked through.", 300),
                new InstructionStep(4, "Add chilled cooked rice, diced onions, and carrots; toss on high flame.", 240),
                new InstructionStep(5, "Drizzle soy sauce and white pepper around the edges; toss evenly and serve!", 120)
        ));
        r2.setCalories(420);
        r2.setProteinGrams(26);
        r2.setCarbsGrams(48);
        r2.setFatsGrams(14);
        recipes.add(r2);

        // Chicken Biryani
        Recipe r3 = new Recipe("rec_3", "Chicken Biryani", 
                "https://images.unsplash.com/photo-1563379091339-03b21ab4a4f8?w=800&q=80", 
                4.9, 50, 4, "Medium", 90, "Main Course", "Indian",
                "A majestic royal rice dish featuring fragrant basmati rice layered with spiced marinated chicken, saffron milk, and caramelized onions.");
        r3.getIngredients().add(new Ingredient("Chicken", "600g", true));
        r3.getIngredients().add(new Ingredient("Rice", "3 cups basmati", true));
        r3.getIngredients().add(new Ingredient("Onion", "3 large", true));
        r3.getIngredients().add(new Ingredient("Yogurt", "1 cup", true));
        r3.getIngredients().add(new Ingredient("Mint Leaves", "1/2 cup", false));
        r3.getMissingIngredients().add("Mint Leaves");
        r3.setInstructionSteps(Arrays.asList(
                new InstructionStep(1, "Boil basmati rice with whole spices until 70% cooked; drain thoroughly.", 400),
                new InstructionStep(2, "Marinate chicken with yogurt, biryani masala, ginger-garlic paste, and salt.", 600),
                new InstructionStep(3, "Deep fry sliced onions until crisp and golden brown (Birista).", 300),
                new InstructionStep(4, "Layer cooked chicken gravy and parboiled rice in a heavy-bottomed pot.", 200),
                new InstructionStep(5, "Seal pot tightly with foil/dough and cook on low heat (Dum) for 25 minutes.", 900)
        ));
        r3.setCalories(620);
        r3.setProteinGrams(34);
        r3.setCarbsGrams(68);
        r3.setFatsGrams(22);
        recipes.add(r3);

        // Gourmet Pizza
        Recipe r4 = new Recipe("rec_4", "Classic Margherita Pizza", 
                "https://images.unsplash.com/photo-1513104890138-7c749659a591?w=800&q=80", 
                4.7, 30, 3, "Medium", 85, "Italian", "Italian",
                "Artisanal thin-crust pizza topped with rich San Marzano tomato sauce, melted fresh mozzarella cheese, and sweet basil leaves.");
        r4.getIngredients().add(new Ingredient("Flour", "300g", true));
        r4.getIngredients().add(new Ingredient("Tomato", "3 ripe", true));
        r4.getIngredients().add(new Ingredient("Mozzarella Cheese", "200g", true));
        r4.getIngredients().add(new Ingredient("Fresh Basil", "6 leaves", false));
        r4.getMissingIngredients().add("Fresh Basil");
        r4.setInstructionSteps(Arrays.asList(
                new InstructionStep(1, "Prepare pizza dough using flour, yeast, water, and olive oil; let it rest.", 600),
                new InstructionStep(2, "Stretch dough into a round shape on parchment paper.", 180),
                new InstructionStep(3, "Spread crushed tomato sauce evenly leaving 1-inch crust edge.", 120),
                new InstructionStep(4, "Top generously with sliced mozzarella cheese.", 90),
                new InstructionStep(5, "Bake at high temperature (230°C / 450°F) for 12-14 minutes until crust is golden.", 720)
        ));
        r4.setCalories(480);
        r4.setProteinGrams(20);
        r4.setCarbsGrams(56);
        r4.setFatsGrams(18);
        recipes.add(r4);

        // Creamy Mushroom Pasta
        Recipe r5 = new Recipe("rec_5", "Creamy Mushroom Pasta", 
                "https://images.unsplash.com/photo-1621996346565-e3def616403c?w=800&q=80", 
                4.6, 20, 2, "Easy", 88, "Italian", "Italian",
                "Al dente penne pasta tossed in a luxurious garlic, parmesan, and sautéed mushroom white cream sauce.");
        r5.getIngredients().add(new Ingredient("Pasta", "250g", true));
        r5.getIngredients().add(new Ingredient("Mushroom", "150g", true));
        r5.getIngredients().add(new Ingredient("Garlic", "4 cloves", true));
        r5.getIngredients().add(new Ingredient("Heavy Cream", "1/2 cup", false));
        r5.getMissingIngredients().add("Heavy Cream");
        r5.setInstructionSteps(Arrays.asList(
                new InstructionStep(1, "Boil penne pasta in salted water for 10 minutes until al dente.", 600),
                new InstructionStep(2, "Sauté sliced mushrooms and minced garlic in olive oil until golden.", 300),
                new InstructionStep(3, "Pour in cream, parmesan cheese, and black pepper; simmer gently.", 180),
                new InstructionStep(4, "Combine pasta with cream sauce, sprinkle parsley, and enjoy!", 120)
        ));
        r5.setCalories(510);
        r5.setProteinGrams(16);
        r5.setCarbsGrams(62);
        r5.setFatsGrams(22);
        recipes.add(r5);

        return recipes;
    }
}
