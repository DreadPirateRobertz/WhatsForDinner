package ramseybros.WhatsForDinner.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import okio.`-DeprecatedUtf8`
import ramseybros.WhatsForDinner.data.Ingredient
import ramseybros.WhatsForDinner.data.Recipe
import ramseybros.WhatsForDinner.data.RecipeIngredientListXRef
import ramseybros.WhatsForDinner.data.RecipeUtensil
import java.util.*

@Dao
interface WhatsForDinnerDao {
    @Insert
    fun addRecipe(recipe: Recipe)

    @Query("SELECT * FROM recipe")
    fun getRecipes(): LiveData<List<Recipe>>

    @Query("SELECT * FROM recipe WHERE id=(:id)")
    fun getRecipe(id: UUID): LiveData<Recipe>?

    @Update
    fun updateRecipe(recipe: Recipe)

    @Delete
    fun deleteRecipe(recipe: Recipe)

    @Insert
    fun addIngredient(ingredient: Ingredient)

    @Query("SELECT * FROM ingredient")
    fun getIngredients(): LiveData<List<Ingredient>>

    @Query("SELECT * FROM ingredient WHERE name=(:name)")
    fun getIngredient(name: String): LiveData<Ingredient>?

    @Query("SELECT name FROM utensil WHERE name=(:name) AND recipeId=(:recipeId)")
    fun getUtensil(name: String, recipeId: UUID): LiveData<String>?

    @Update
    fun updateIngredient(ingredient: Ingredient)

    @Delete
    fun deleteIngredient(ingredient: Ingredient)

    @Insert
    fun addIngredientToList(recipeIngredient: RecipeIngredientListXRef)
    fun addIngredientsToList(ingredientList: List<Ingredient>, recipe: Recipe) {
        for (ingredient in ingredientList) {
            if (getIngredient(ingredient.name) == null) addIngredient(ingredient = ingredient)
            addIngredientToList(
                RecipeIngredientListXRef(
                    ingredientName = ingredient.name,
                    recipeId = recipe.id
                )
            )
        }
    }
    @Insert
    fun addUtensilToList(recipeIngredient: RecipeUtensil)
    fun addUtensilsToList(utensilList: List<String>, recipe: Recipe) {
        for (utensil in utensilList) {
            if (getUtensil(utensil, recipe.id) == null)
                addUtensilToList(
                    RecipeUtensil(
                    recipeId = recipe.id,
                    name = utensil)
                )
        }
    }



    fun addRecipe(recipe: Recipe, ingredients: List<Ingredient>?, utensils: List<String>?) {
        if(getRecipe(recipe.id) == null) addRecipe(recipe)
        else updateRecipe(recipe)
        if(utensils != null) addUtensilsToList(utensils, recipe)
        if(ingredients != null) addIngredientsToList(ingredients, recipe)
    }

    @Query("SELECT recipe_ingredient_list_xref.ingredientName FROM recipe_ingredient_list_xref WHERE recipe_ingredient_list_xref.recipeId=(:recipeId)")
    fun getIngredientList(recipeId: UUID): LiveData<List<String>>

    @Query("SELECT name FROM utensil WHERE utensil.recipeId=(:recipeId)")
    fun getUtensilList(recipeId: UUID): LiveData<List<String>>
}