package ramseybros.WhatsForDinner.util

import ramseybros.WhatsForDinner.data.Ingredient
import ramseybros.WhatsForDinner.data.IngredientType
import ramseybros.WhatsForDinner.data.Recipe

object RecipeGenerator {

    private fun <T> List<T>.rand() = shuffled().first()
    private val recipes = listOf("Cheeseburgers", "Moroccan Chicken", "Spaghetti & Meatballs", "Crab Dip", "Fish Tacos", "Caesar Salad")


    fun placeHolderRecipe() = Recipe("Generating Recipe...")
    fun placeHolderIngredients() = Ingredient("Genereating Ingredient...",1,IngredientType.SPICE)
}