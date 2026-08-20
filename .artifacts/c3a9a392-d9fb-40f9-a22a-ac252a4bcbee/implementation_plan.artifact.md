# Implementation Plan - Debugging Project Errors

This plan addresses several issues identified in the SmartChef AI project, ranging from build configuration to logical bugs in the UI and data layers.

## User Review Required

> [!IMPORTANT]
> **Gradle JVM Incompatibility**: The current Gradle version (8.10.2) does not support the selected JVM (Java 25). I will attempt to update to Gradle 8.11, but if it still fails, you may need to select a compatible JDK (e.g., JDK 17 or 21) in **Settings | Build, Execution, Deployment | Build Tools | Gradle**.

## Proposed Changes

---

### Build Configuration

#### [MODIFY] [gradle-wrapper.properties](file:///D:/Projects_Main/SmartChef_AI/gradle/wrapper/gradle-wrapper.properties)
- Update `distributionUrl` to Gradle 8.11 (if available) to improve compatibility with newer Java versions.

---

### Feature: Recipe Results & Filtering

#### [MODIFY] [RecipeResultsActivity.java](file:///D:/Projects_Main/SmartChef_AI/app/src/main/java/com/example/smartchef/activities/RecipeResultsActivity.java)
- **Fix Filter Logic**: Change the "Under 20 mins" filter check from `25` minutes to `20` minutes to match the UI label.
- **Improve Filtering**: Ensure that the "Vegetarian" filter (currently mocked with Italian/Pasta/Pizza checks) is more clearly defined or at least documented.

#### [MODIFY] [RecipeAdapter.java](file:///D:/Projects_Main/SmartChef_AI/app/src/main/java/com/example/smartchef/adapters/RecipeAdapter.java)
- **Performance**: Move `favoritesManager.isFavorite()` check out of `onBindViewHolder` if possible, or at least document the main-thread query.
- **Mock Data Fix**: Improve the logic for `tvIngredientsSummary` to handle cases where ingredients are null or empty more gracefully.

---

### Feature: Ingredient Selection (Kitchen)

#### [MODIFY] [IngredientsFragment.java](file:///D:/Projects_Main/SmartChef_AI/app/src/main/java/com/example/smartchef/fragments/IngredientsFragment.java)
- **Safety**: Add a check in `uncheckMatchingChips` to ensure children are instances of `Chip` before casting, preventing potential `ClassCastException`.

---

### UI Consistency

#### [MODIFY] [RecipeOverviewFragment.java](file:///D:/Projects_Main/SmartChef_AI/app/src/main/java/com/example/smartchef/fragments/RecipeOverviewFragment.java)
- **Inconsistency**: Update units for macros (kcal, g) to match the format used in `RecipeNutritionFragment` (adding spaces and consistent abbreviations).

---

### Model Layer (API Readiness)

#### [MODIFY] [Recipe.java](file:///D:/Projects_Main/SmartChef_AI/app/src/main/java/com/example/smartchef/models/Recipe.java)
- **Serialization**: Add `@SerializedName` annotations to fields like `imageUrl` (`@SerializedName("image")`) to ensure they match the Spoonacular API response if the app switches to live data.

## Verification Plan

### Automated Tests
- Run `./gradlew assembleDebug` to verify the build configuration after JVM/Gradle updates.
- Note: Since there are no unit tests, manual verification is primary.

### Manual Verification
1. **Build Verification**: Ensure Gradle Sync completes successfully.
2. **Filter Test**: Open "Find Recipes" from Home, select "Under 20 mins" filter, and verify recipes shown are actually $\le$ 20 mins.
3. **Kitchen Test**: Add and remove ingredients from the kitchen basket and verify the chips update correctly without crashes.
4. **Detail Test**: Open a recipe, check Overview and Nutrition tabs for consistent units.
