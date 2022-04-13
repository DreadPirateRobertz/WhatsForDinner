package ramseybros.WhatsForDinner.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import ramseybros.WhatsForDinner.data.Ingredient
import ramseybros.WhatsForDinner.data.Recipe
import ramseybros.WhatsForDinner.data.RecipeIngredientListXRef
import java.util.*

@Dao
interface WhatsForDinnerDao {
    @Insert
    fun addRecipe(recipe: Recipe)
    @Query("SELECT * FROM recipe")
    fun getRecipes() : LiveData<List<Recipe>>
    @Query("SELECT * FROM recipe WHERE id=(:id)")
    fun getRecipe(id : UUID) : LiveData<Recipe>?
    @Update
    fun updateRecipe(recipe: Recipe)
    @Delete
    fun deleteRecipe(recipe: Recipe)

    @Insert
    fun addIngredient(ingredient: Ingredient)
    @Query("SELECT * FROM ingredient")
    fun getIngredients() : LiveData<List<Ingredient>>
    @Query("SELECT * FROM ingredient WHERE name=(:name)")
    fun getIngredient(name : String) : LiveData<Ingredient>?
    @Update
    fun updateIngredient(ingredient: Ingredient)
    @Delete
    fun deleteIngredient(ingredient: Ingredient)

    @Insert
    fun addIngredientToList(recipeIngredient: RecipeIngredientListXRef)
    fun addIngredientsToList(ingredientList: List<Ingredient>, recipe: Recipe) {
        for (ingredient in ingredientList) {
            if(getIngredient(ingredient.name)==null) addIngredient(ingredient = ingredient)
            addIngredientToList(RecipeIngredientListXRef(ingredientName = ingredient.name, recipeId = recipe.id))
        }
    }
    @Query("SELECT recipe_ingredient_list_xref.ingredientName FROM recipe_ingredient_list_xref WHERE recipe_ingredient_list_xref.recipeId=(:recipeId)")
    fun getIngredientList(recipeId: UUID) : LiveData<List<String>>
    @Query("SELECT name FROM utensil WHERE utensil.recipeId=(:recipeId)")
    fun getUtensilList(recipeId: UUID) : LiveData<List<String>>
}