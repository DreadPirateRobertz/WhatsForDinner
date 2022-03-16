package ramseybros.WhatsForDinner.util

import ramseybros.WhatsForDinner.data.Ingredient
import ramseybros.WhatsForDinner.data.Recipe

object RecipeGenerator {

    private fun <T> List<T>.rand() = shuffled().first()
    private val recipes = listOf("Cheeseburgers", "Moroccan Chicken", "Spaghetti & Meatballs", "Crab Dip", "Fish Tacos", "Caesar Salad")


    fun placeHolderRecipe() = Recipe("Generating Recipe...","Generating Recipe...", -1,"", listOf(Ingredient("")), listOf(""), "")
    fun placeHolderIngredients() = Ingredient("Genereating Ingredient...")
    fun singleRecipe() : Recipe {
        var ingredientList: List<Ingredient> = listOf(Ingredient("Garlic"), Ingredient("Kosher Salt"), Ingredient("Paprika"), Ingredient("Ground Black Pepper"), Ingredient("Cream"))
        var utensilList: List<String> = listOf("Spoon", "Whisk", "Pan")
        var inKitchenList: List<String> = listOf("Garlic", "Paprika", "Ground Black Pepper", "Spoon", "Whisk")
        var recipeText: String = "take the garlic\n\nLorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.\n\n\n\n\n\n\n\n\n\ntake the garlic\n\ntake the garlic\n\nT A K E  T H E  G A R L I C\nLorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.\n\n\n\n\nAAAAAAAH\n\nnewline\nnew\nline\n"
        var title = "Garlic Lorem Ipsum"
        var time = "17 hours"
        //ListItems(ingredientList = ingredientList, utensilList = utensilList, inKitchenList = inKitchenList)
        //RecipeText(recipeText = recipeText)
        var recipe : Recipe = Recipe(ingredientList = ingredientList, utensilList = utensilList, recipeText = recipeText, title = title, difficulty = 0, time = time, imageLink = "")
        return recipe
    }
}