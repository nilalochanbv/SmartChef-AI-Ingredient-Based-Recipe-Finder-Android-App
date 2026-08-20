package com.example.smartchef.utils;

import com.example.smartchef.R;
import com.example.smartchef.models.Category;
import com.example.smartchef.models.Ingredient;
import com.example.smartchef.models.InstructionStep;
import com.example.smartchef.models.Recipe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
        list.add(new Ingredient("ing_v8", "Mushroom", "Vegetables", true));
        
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

        // 1. Butter Chicken
        Recipe r1 = new Recipe("rec_1", "Butter Chicken", 
                "https://images.unsplash.com/photo-1588166524941-3bf61a9c41db?w=800&q=80", 
                4.8, 45, 4, "Easy", 95, "Main Course", "Indian",
                "Rich and creamy North Indian curry crafted with succulent tender chicken chunks steeped in a velvety butter, tomato, and cashew nut gravy.");
        r1.getIngredients().add(new Ingredient("Chicken", "500g", true));
        r1.getIngredients().add(new Ingredient("Tomato", "4 medium", true));
        r1.getIngredients().add(new Ingredient("Onion", "2 medium", true));
        r1.getIngredients().add(new Ingredient("Garlic", "4 cloves", true));
        r1.getIngredients().add(new Ingredient("Fresh Cream", "1/2 cup", false));
        r1.getMissingIngredients().add("Fresh Cream");
        r1.setInstructionSteps(Arrays.asList(
                new InstructionStep(1, "Marinate chicken chunks with yogurt, lemon juice, and spices for 30 mins.", 300),
                new InstructionStep(2, "Heat butter in a large pan and sear marinated chicken pieces until golden brown.", 300),
                new InstructionStep(3, "In the same pan, sauté chopped onions and garlic until translucent.", 240),
                new InstructionStep(4, "Add tomato puree and simmer gently for 15 minutes.", 450),
                new InstructionStep(5, "Stir in fresh cream, add grilled chicken back to the gravy, and cook for 5 minutes.", 300),
                new InstructionStep(6, "Garnish with fresh cilantro leaves and a swirl of cream. Serve hot!", 60)
        ));
        r1.setCalories(540);
        r1.setProteinGrams(38);
        r1.setCarbsGrams(18);
        r1.setFatsGrams(32);
        recipes.add(r1);

        // 2. Paneer Butter Masala
        Recipe r2 = new Recipe("rec_paneer_bm", "Paneer Butter Masala",
                "https://images.unsplash.com/photo-1631452180519-c014fe946bc7?w=800&q=80",
                4.9, 30, 3, "Easy", 100, "Main Course", "Indian",
                "Mouth-watering cottage cheese cubes simmered in a luscious tomato, onion, and butter gravy.");
        r2.getIngredients().add(new Ingredient("Paneer", "300g", true));
        r2.getIngredients().add(new Ingredient("Tomato", "3 medium", true));
        r2.getIngredients().add(new Ingredient("Onion", "2 medium", true));
        r2.getIngredients().add(new Ingredient("Garlic", "3 cloves", true));
        r2.getIngredients().add(new Ingredient("Butter", "30g", true));
        r2.setInstructionSteps(Arrays.asList(
                new InstructionStep(1, "Cube paneer into bite-sized squares.", 120),
                new InstructionStep(2, "Sauté onion, garlic, and tomato; blend into a smooth puree.", 300),
                new InstructionStep(3, "Melt butter in a pan, add spices and tomato-onion puree.", 240),
                new InstructionStep(4, "Add paneer cubes and simmer gently for 8 minutes.", 480)
        ));
        r2.setCalories(460);
        r2.setProteinGrams(24);
        r2.setCarbsGrams(20);
        r2.setFatsGrams(28);
        recipes.add(r2);

        // 3. Egg Curry
        Recipe r3 = new Recipe("rec_egg_curry", "Spicy Egg Curry",
                "https://images.unsplash.com/photo-1626777552726-4a6b54c97e46?w=800&q=80",
                4.7, 25, 2, "Easy", 100, "Main Course", "Indian",
                "Boiled eggs cooked in a rich, comforting onion-tomato masala gravy with aromatic spices.");
        r3.getIngredients().add(new Ingredient("Egg", "4 pcs", true));
        r3.getIngredients().add(new Ingredient("Tomato", "2 medium", true));
        r3.getIngredients().add(new Ingredient("Onion", "2 medium", true));
        r3.getIngredients().add(new Ingredient("Garlic", "4 cloves", true));
        r3.setInstructionSteps(Arrays.asList(
                new InstructionStep(1, "Hard-boil eggs, peel shells, and lightly prick with a fork.", 600),
                new InstructionStep(2, "Shallow fry eggs until outer skin turns golden brown.", 180),
                new InstructionStep(3, "Sauté finely chopped onions and garlic until caramelized.", 300),
                new InstructionStep(4, "Add tomato puree and simmer with masala for 10 mins.", 600),
                new InstructionStep(5, "Drop fried eggs into gravy and cook for 5 minutes.", 300)
        ));
        r3.setCalories(380);
        r3.setProteinGrams(22);
        r3.setCarbsGrams(14);
        r3.setFatsGrams(20);
        recipes.add(r3);

        // 4. Palak Paneer
        Recipe r4 = new Recipe("rec_palak_paneer", "Palak Paneer",
                "https://images.unsplash.com/photo-1601050690597-df0568f70950?w=800&q=80",
                4.8, 35, 3, "Easy", 100, "Main Course", "Healthy",
                "Soft cottage cheese cubes cooked in a nutritious, flavorful spinach purée seasoned with garlic and ginger.");
        r4.getIngredients().add(new Ingredient("Paneer", "250g", true));
        r4.getIngredients().add(new Ingredient("Spinach", "300g", true));
        r4.getIngredients().add(new Ingredient("Garlic", "5 cloves", true));
        r4.getIngredients().add(new Ingredient("Onion", "1 medium", true));
        r4.setInstructionSteps(Arrays.asList(
                new InstructionStep(1, "Blanch spinach leaves in boiling water for 2 mins, then dip in ice water.", 180),
                new InstructionStep(2, "Puree blanched spinach leaves with garlic in a blender.", 120),
                new InstructionStep(3, "Sauté onions and garlic in oil until translucent.", 240),
                new InstructionStep(4, "Add spinach puree and paneer cubes; simmer for 7 minutes.", 420)
        ));
        r4.setCalories(390);
        r4.setProteinGrams(26);
        r4.setCarbsGrams(12);
        r4.setFatsGrams(24);
        recipes.add(r4);

        // 5. Chicken Fried Rice
        Recipe r5 = new Recipe("rec_2", "Chicken Fried Rice", 
                "https://images.unsplash.com/photo-1603133872878-684f208fb84b?w=800&q=80", 
                4.8, 25, 2, "Easy", 95, "Main Course", "Chinese",
                "Flavorful Indo-Chinese style fried rice packed with diced chicken, crisp wok-tossed vegetables, and aromatic soy seasoning.");
        r5.getIngredients().add(new Ingredient("Chicken", "250g", true));
        r5.getIngredients().add(new Ingredient("Rice", "2 cups cooked", true));
        r5.getIngredients().add(new Ingredient("Onion", "1 medium", true));
        r5.getIngredients().add(new Ingredient("Egg", "2 pcs", true));
        r5.getIngredients().add(new Ingredient("Carrot", "1 medium", true));
        r5.setInstructionSteps(Arrays.asList(
                new InstructionStep(1, "Heat sesame oil in a high-heat wok until lightly smoking.", 120),
                new InstructionStep(2, "Scramble eggs quickly and push them to one side of the wok.", 180),
                new InstructionStep(3, "Add diced chicken and stir-fry briskly for 4-5 minutes.", 300),
                new InstructionStep(4, "Add chilled cooked rice, diced onions, and carrots; toss on high flame.", 240),
                new InstructionStep(5, "Drizzle soy sauce and white pepper around the edges; toss evenly.", 120)
        ));
        r5.setCalories(420);
        r5.setProteinGrams(26);
        r5.setCarbsGrams(48);
        r5.setFatsGrams(14);
        recipes.add(r5);

        // 6. Vegetable Fried Rice
        Recipe r6 = new Recipe("rec_veg_fried_rice", "Vegetable Fried Rice",
                "https://images.unsplash.com/photo-1512058564366-18510be2db19?w=800&q=80",
                4.6, 20, 2, "Easy", 100, "Main Course", "Chinese",
                "A colorful wok-tossed fried rice cooked with fresh carrots, capsicum, onions, and garlic.");
        r6.getIngredients().add(new Ingredient("Rice", "2 cups cooked", true));
        r6.getIngredients().add(new Ingredient("Carrot", "1 medium", true));
        r6.getIngredients().add(new Ingredient("Capsicum", "1 medium", true));
        r6.getIngredients().add(new Ingredient("Onion", "1 medium", true));
        r6.getIngredients().add(new Ingredient("Garlic", "3 cloves", true));
        r6.setInstructionSteps(Arrays.asList(
                new InstructionStep(1, "Sauté minced garlic, diced onions, carrots, and capsicum on high heat.", 240),
                new InstructionStep(2, "Add cold left-over cooked rice and toss vigorously.", 180),
                new InstructionStep(3, "Add soy sauce, pinch of salt, and black pepper; mix well.", 120)
        ));
        r6.setCalories(340);
        r6.setProteinGrams(8);
        r6.setCarbsGrams(54);
        r6.setFatsGrams(9);
        recipes.add(r6);

        // 7. Chicken Biryani
        Recipe r7 = new Recipe("rec_3", "Chicken Biryani", 
                "https://images.unsplash.com/photo-1563379091339-03b21ab4a4f8?w=800&q=80", 
                4.9, 50, 4, "Medium", 90, "Main Course", "Indian",
                "A majestic royal rice dish featuring fragrant basmati rice layered with spiced marinated chicken and caramelized onions.");
        r7.getIngredients().add(new Ingredient("Chicken", "600g", true));
        r7.getIngredients().add(new Ingredient("Rice", "3 cups basmati", true));
        r7.getIngredients().add(new Ingredient("Onion", "3 large", true));
        r7.getIngredients().add(new Ingredient("Garlic", "4 cloves", true));
        r7.setInstructionSteps(Arrays.asList(
                new InstructionStep(1, "Boil basmati rice with whole spices until 70% cooked; drain thoroughly.", 400),
                new InstructionStep(2, "Marinate chicken with yogurt, biryani masala, and ginger-garlic paste.", 600),
                new InstructionStep(3, "Deep fry sliced onions until crisp and golden brown.", 300),
                new InstructionStep(4, "Layer cooked chicken gravy and parboiled rice in a heavy pot.", 200),
                new InstructionStep(5, "Seal pot tightly and cook on low heat (Dum) for 25 minutes.", 900)
        ));
        r7.setCalories(620);
        r7.setProteinGrams(34);
        r7.setCarbsGrams(68);
        r7.setFatsGrams(22);
        recipes.add(r7);

        // 8. Classic Margherita Pizza
        Recipe r8 = new Recipe("rec_4", "Classic Margherita Pizza", 
                "https://images.unsplash.com/photo-1513104890138-7c749659a591?w=800&q=80", 
                4.7, 30, 3, "Medium", 85, "Italian", "Italian",
                "Artisanal thin-crust pizza topped with rich tomato sauce and melted fresh cheese.");
        r8.getIngredients().add(new Ingredient("Flour", "300g", true));
        r8.getIngredients().add(new Ingredient("Tomato", "3 ripe", true));
        r8.getIngredients().add(new Ingredient("Garlic", "2 cloves", true));
        r8.setInstructionSteps(Arrays.asList(
                new InstructionStep(1, "Prepare pizza dough using flour, yeast, and water.", 600),
                new InstructionStep(2, "Spread crushed tomato sauce evenly leaving 1-inch crust edge.", 120),
                new InstructionStep(3, "Bake at high temperature (230°C / 450°F) for 12-14 minutes.", 720)
        ));
        r8.setCalories(480);
        r8.setProteinGrams(20);
        r8.setCarbsGrams(56);
        r8.setFatsGrams(18);
        recipes.add(r8);

        // 9. Creamy Mushroom Pasta
        Recipe r9 = new Recipe("rec_5", "Creamy Mushroom Pasta", 
                "https://images.unsplash.com/photo-1621996346565-e3def616403c?w=800&q=80", 
                4.6, 20, 2, "Easy", 88, "Italian", "Italian",
                "Al dente penne pasta tossed in a garlic and sautéed mushroom cream sauce.");
        r9.getIngredients().add(new Ingredient("Pasta", "250g", true));
        r9.getIngredients().add(new Ingredient("Mushroom", "150g", true));
        r9.getIngredients().add(new Ingredient("Garlic", "4 cloves", true));
        r9.getIngredients().add(new Ingredient("Onion", "1 medium", true));
        r9.setInstructionSteps(Arrays.asList(
                new InstructionStep(1, "Boil penne pasta in salted water for 10 minutes.", 600),
                new InstructionStep(2, "Sauté sliced mushrooms and minced garlic in oil.", 300),
                new InstructionStep(3, "Combine pasta with cream sauce, sprinkle black pepper, and serve!", 120)
        ));
        r9.setCalories(510);
        r9.setProteinGrams(16);
        r9.setCarbsGrams(62);
        r9.setFatsGrams(22);
        recipes.add(r9);

        // 10. Garlic Potato Fry / Aloo Fry
        Recipe r10 = new Recipe("rec_potato_fry", "Crispy Garlic Potato Fry",
                "https://images.unsplash.com/photo-1585109649139-366815a0d713?w=800&q=80",
                4.7, 20, 2, "Easy", 100, "Snacks", "Indian",
                "Crispy golden roasted potato cubes spiced with garlic, chili flakes, and herbs.");
        r10.getIngredients().add(new Ingredient("Potato", "3 large", true));
        r10.getIngredients().add(new Ingredient("Garlic", "5 cloves", true));
        r10.getIngredients().add(new Ingredient("Onion", "1 medium", true));
        r10.getIngredients().add(new Ingredient("Capsicum", "1 medium", true));
        r10.setInstructionSteps(Arrays.asList(
                new InstructionStep(1, "Dice potatoes into small bite-size cubes.", 180),
                new InstructionStep(2, "Shallow fry potatoes until crispy and golden brown.", 480),
                new InstructionStep(3, "Toss fried potatoes with sautéed garlic, onions, and capsicum.", 240)
        ));
        r10.setCalories(310);
        r10.setProteinGrams(6);
        r10.setCarbsGrams(42);
        r10.setFatsGrams(12);
        recipes.add(r10);

        return recipes;
    }

    /**
     * Dynamically matches recipes based on exact selected kitchen ingredients
     * and ranks them by highest match percentage (closest to 100%).
     */
    public static List<Recipe> matchRecipesByIngredients(List<String> selectedKitchenIngredients) {
        List<Recipe> all = getPopularRecipes();
        if (selectedKitchenIngredients == null || selectedKitchenIngredients.isEmpty()) {
            return all;
        }

        List<Recipe> matchedList = new ArrayList<>();

        for (Recipe r : all) {
            List<Ingredient> recipeIngs = r.getIngredients();
            int totalIngs = recipeIngs != null ? recipeIngs.size() : 1;
            int haveCount = 0;
            List<String> missingList = new ArrayList<>();

            for (Ingredient ing : recipeIngs) {
                boolean found = false;
                for (String userIng : selectedKitchenIngredients) {
                    if (ing.getName().equalsIgnoreCase(userIng) ||
                        ing.getName().toLowerCase().contains(userIng.toLowerCase()) || 
                        userIng.toLowerCase().contains(ing.getName().toLowerCase())) {
                        found = true;
                        break;
                    }
                }
                if (found) {
                    haveCount++;
                    ing.setAvailable(true);
                } else {
                    ing.setAvailable(false);
                    missingList.add(ing.getName());
                }
            }

            int percentage = (int) Math.round(((double) haveCount / totalIngs) * 100);
            r.setMatchPercentage(percentage);
            r.setMissingIngredients(missingList);

            matchedList.add(r);
        }

        // Sort descending by match percentage so top matching recipes (e.g. 100%, 80%) appear first!
        Collections.sort(matchedList, (r1, r2) -> Integer.compare(r2.getMatchPercentage(), r1.getMatchPercentage()));

        return matchedList;
    }
}
