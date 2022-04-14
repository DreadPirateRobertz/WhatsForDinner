package ramseybros.WhatsForDinner.data.database

import android.content.Context
import androidx.lifecycle.LiveData
import ramseybros.WhatsForDinner.data.Ingredient
import ramseybros.WhatsForDinner.data.Recipe
import java.util.*
import java.util.concurrent.Executors

class WhatsForDinnerRepository private constructor(private val whatsForDinnerDao: WhatsForDinnerDao) {
    companion object {
        @Volatile
        private var INSTANCE: WhatsForDinnerRepository? = null
        fun getInstance(context: Context): WhatsForDinnerRepository {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    val database = WhatsForDinnerDatabase.getInstance(context)
                    instance = WhatsForDinnerRepository(database.whatsForDinnerDao)
                    INSTANCE = instance
                }
                return instance
            }
        }
    }

    private val executor = Executors.newSingleThreadExecutor()
    fun addRecipe(recipe: Recipe) {
        executor.execute {
            whatsForDinnerDao.addRecipe(recipe)
        }
    }

    fun addIngredient(ingredient: Ingredient) {
        executor.execute {
            whatsForDinnerDao.addIngredient(ingredient)
        }
    }

    fun updateRecipe(recipe: Recipe) {
        executor.execute {
            whatsForDinnerDao.updateRecipe(recipe)
        }
    }

    fun updateIngredient(ingredient: Ingredient) {
        executor.execute {
            whatsForDinnerDao.updateIngredient(ingredient)
        }
    }

    fun addRecipe(recipe: Recipe, ingredients: List<Ingredient>, utensils: List<String>) {
        executor.execute {
            whatsForDinnerDao.addRecipe(recipe = recipe, ingredients = ingredients, utensils = utensils)
        }
    }

    fun getRecipes(): LiveData<List<Recipe>> = whatsForDinnerDao.getRecipes()
    fun getRecipe(id: UUID): LiveData<Recipe>? = whatsForDinnerDao.getRecipe(id)
    fun getIngredients(): LiveData<List<Ingredient>> = whatsForDinnerDao.getIngredients()
    fun getIngredient(name: String): LiveData<Ingredient>? = whatsForDinnerDao.getIngredient(name)
    fun deleteRecipe(recipe: Recipe) = whatsForDinnerDao.deleteRecipe(recipe)
    fun deleteIngredient(ingredient: Ingredient) = whatsForDinnerDao.deleteIngredient(ingredient)

    fun getIngredientList(recipeId: UUID): LiveData<List<String>> =
        whatsForDinnerDao.getIngredientList(recipeId)

    fun getUtensilList(recipeId: UUID): LiveData<List<String>> =
        whatsForDinnerDao.getUtensilList(recipeId)

}