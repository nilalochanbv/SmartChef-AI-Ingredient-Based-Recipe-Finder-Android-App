# SmartChef AI 👨‍🍳✨
### *Cook smarter with what you already have.*

SmartChef AI is a native Android application built in **Java** with **XML layouts** following **Material Design 3**. It helps home cooks discover personalized recipes based on ingredients available in their kitchen, cooking preferences, time constraints, and dietary needs.

---

## 🌟 Key Features

- **Animated Splash Screen**: Features custom vector logo mark, tagline, and smooth 2-second transition timer.
- **Home Screen**: Time-aware greetings ("Good Evening 👋"), rounded search bar, interactive quick ingredient chips with scale animations, horizontal popular recipes, and explore cuisines grid.
- **Kitchen Ingredient Finder**: Categorized Material Chips (Vegetables, Protein, Grains) with a visual bottom **Kitchen Basket** that dynamically updates items in real-time.
- **Recipe Results & Match Calculator**: Displays circular match percentage indicators (e.g. `95% Match`), missing ingredient tags (*"Missing: Coriander"*), and horizontal filters (All, Under 20 mins, Easy, Vegetarian, High Protein).
- **Recipe Details & Tabs**: CollapsingToolbarLayout with hero food image, ratings, prep time, servings, and TabLayout (Overview with macros breakdown, Ingredients checklist with missing item shopping list addition, Instructions vertical step timeline, Nutrition facts).
- **Distraction-Free Dark Cooking Mode**: Full-screen cooking assistant with step counter, large instruction text, circular countdown timer (with start/pause/reset), and step navigation.
- **Local Persistence**: Dual storage utilizing **Room Database** (`FavoriteDatabase`) and **SharedPreferences** for saving favorite recipes offline.
- **Shopping List Manager**: Persistent shopping list storage for missing recipe ingredients.
- **Search & Profile**: Dynamic instant recipe search with recent query tags, dietary preferences, and settings.

---

## 🎨 Visual Aesthetics & Design System

- **Primary Accent**: Warm Orange (`#FF6B35`) & Tomato Red (`#E63946`)
- **Background Canvas**: Soft Cream (`#FFFDF7`) & Card Surfaces (`#FFFFFF`)
- **Card Geometry**: 20dp to 28dp rounded corners with soft elevation and borders
- **Typography**: Deep dark text (`#1A1A1A`) for high contrast readability
- **Icons & Graphics**: Custom vector drawables for logo mark, cooking pot, kitchen basket, empty favorites plate, sparkles, and utensils.

---

## 🛠 Tech Stack & Architecture

- **Language**: Java 17 / 21
- **UI Framework**: Android XML Layouts, Material Design 3 (`com.google.android.material:material`), ViewPager2, CollapsingToolbarLayout, ConstraintLayout
- **Image Loading**: Glide (`com.github.bumptech.glide:glide`)
- **API Integration**: Retrofit 2 (`com.squareup.retrofit2:retrofit`) & Gson (`converter-gson`) with offline fallback support
- **Local Database**: Room Database (`androidx.room:room-runtime`) & SharedPreferences
- **Animations**: Lottie Animations (`com.airbnb.android:lottie`) and Android View Property Animators

---

## 📁 Repository Structure

```
SmartChef_AI/
├── app/
│   ├── src/main/
│   │   ├── java/com/example/smartchef/
│   │   │   ├── activities/       # Splash, Main, Results, Detail, CookingMode
│   │   │   ├── fragments/        # Home, Search, Ingredients, Favorites, Profile, Tabs
│   │   │   ├── adapters/         # Popular, Recipe, Ingredient, Basket, Cuisine, Steps
│   │   │   ├── database/         # Room Entity, DAO, FavoriteDatabase
│   │   │   ├── network/          # Retrofit ApiClient, ApiService, Response Models
│   │   │   ├── models/           # Recipe, Ingredient, Step, Category
│   │   │   └── utils/            # FavoritesManager, ShoppingListManager, MockData
│   │   ├── res/
│   │   │   ├── drawable/         # Custom SVG vector drawables & shapes
│   │   │   ├── layout/           # XML layout files
│   │   │   ├── menu/             # bottom_nav_menu.xml
│   │   │   └── values/           # colors.xml, strings.xml, themes.xml
│   │   └── AndroidManifest.xml
│   └── build.gradle
├── build.gradle
├── settings.gradle
└── README.md
```

---

## 🚀 Getting Started

1. Clone this repository:
   ```bash
   git clone https://github.com/nilalochanbv/SmartChef-AI-Ingredient-Based-Recipe-Finder-Android-App.git
   ```
2. Open the project in **Android Studio**.
3. Sync Gradle dependencies (`Sync Project with Gradle Files`).
4. Select an Android Emulator or connected physical device running Android 7.0+ (API 24+).
5. Click **Run** (`Shift + F10`) to launch SmartChef AI!

---

## 📄 License

This project is licensed under the MIT License.
