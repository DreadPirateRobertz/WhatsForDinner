package ramseybros.WhatsForDinner.util

import ramseybros.WhatsForDinner.data.Ingredient
import ramseybros.WhatsForDinner.data.IngredientType
import ramseybros.WhatsForDinner.data.Recipe

object RecipeGenerator {

    private fun <T> List<T>.rand() = shuffled().first()
    private val recipes = listOf("Cheeseburgers", "Moroccan Chicken", "Spaghetti & Meatballs", "Crab Dip", "Fish Tacos", "Caesar Salad")


    fun placeHolderRecipe() = Recipe("Generating Recipe...","Generating Recipe...", -1,"", "")
    fun placeHolderIngredients() = Ingredient("Genereating Ingredient...",1,IngredientType.SPICE)
    fun singleRecipe() : Recipe {
        var ingredientList: List<Ingredient> = listOf(Ingredient("Garlic",1,IngredientType.VEGETABLE), Ingredient("Kosher Salt",1,IngredientType.SPICE), Ingredient("Paprika",1,IngredientType.SPICE), Ingredient("Ground Black Pepper",1,IngredientType.SPICE), Ingredient("Cream",1,IngredientType.DAIRY))
        var utensilList: List<String> = listOf("Spoon", "Whisk", "Pan")
        var inKitchenList: List<String> = listOf("Garlic", "Paprika", "Ground Black Pepper", "Spoon", "Whisk")
        var recipeText: String = "take the garlic\n\nLorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.\n\n\n\n\n\n\n\n\n\ntake the garlic\n\ntake the garlic\n\nT A K E  T H E  G A R L I C\nLorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.\n\n\n\n\nAAAAAAAH\n\nnewline\nnew\nline\n"
        var title = "Garlic Lorem Ipsum"
        var time = "17 hours"
        //ListItems(ingredientList = ingredientList, utensilList = utensilList, inKitchenList = inKitchenList)
        //RecipeText(recipeText = recipeText)
        var recipe : Recipe = Recipe(recipeText = recipeText, title = title, difficulty = 0, time = time, imageLink = "")
        return recipe
    }
    fun recipeIngredientList() = listOf("Garlic", "Kosher Salt","Paprika","Ground Black Pepper","Cream")
    fun recipeUtensilList() = listOf("Spoon", "Whisk", "Pan")
}