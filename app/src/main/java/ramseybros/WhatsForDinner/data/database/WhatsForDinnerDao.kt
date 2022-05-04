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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addRecipe(recipe: Recipe)

    @Query("SELECT * FROM recipe WHERE recommended = 0")
    fun getSavedRecipes(): LiveData<MutableList<Recipe>>


    @Query("SELECT * FROM recipe WHERE recommended = 0")
    fun getTestSaved(): LiveData<MutableList<Recipe>>


    @Query("SELECT * FROM recipe WHERE recipe.recommended = 1")
    fun getRecommendedRecipes(): LiveData<MutableList<Recipe>>

    @Query("SELECT * FROM recipe WHERE id=(:id)")
    fun getRecipe(id: UUID): LiveData<Recipe>?

//    @Query("UPDATE recipe SET recommended = 1 WHERE recipe.id=(:recipeId)")
    @Update
    fun updateRecipe(recipe: Recipe)

    @Query("UPDATE recipe SET recommended = 0 WHERE recipe.id=(:recipeId)")
    fun updateRecipeNOTRecommended(recipeId: UUID)

    @Delete
    fun deleteRecipe(recipe: Recipe)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
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

    @Insert
    fun addUtensilToList(recipeIngredient: RecipeUtensil)

    @Query("SELECT recipe_ingredient_list_xref.ingredientName FROM recipe_ingredient_list_xref WHERE recipe_ingredient_list_xref.recipeId=(:recipeId)")
    fun getIngredientList(recipeId: UUID): LiveData<List<String>>

    @Query("SELECT name FROM utensil WHERE utensil.recipeId=(:recipeId)")
    fun getUtensilList(recipeId: UUID): LiveData<List<String>>

    @Query("DELETE FROM recipe_ingredient_list_xref WHERE recipeId=(:recipeId)")
    fun deleteRecipeFromList(recipeId: UUID)

    @Query("DELETE FROM utensil WHERE recipeId=(:recipeId)")
    fun deleteRecipeFromUtensils(recipeId: UUID)

    @Query("DELETE FROM recipe_ingredient_list_xref WHERE ingredientName=(:ingredientName) AND recipeId=(:recipeId)")
    fun deleteIngredientFromRecipe(ingredientName: String, recipeId: UUID)

    @Query("DELETE FROM ingredient")
    fun deleteAllIngredients()
}