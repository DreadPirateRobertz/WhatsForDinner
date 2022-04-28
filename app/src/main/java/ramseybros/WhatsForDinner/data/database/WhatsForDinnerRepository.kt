package ramseybros.WhatsForDinner.data.database

import android.content.Context
import androidx.lifecycle.LiveData
import ramseybros.WhatsForDinner.data.Ingredient
import ramseybros.WhatsForDinner.data.Recipe
import ramseybros.WhatsForDinner.data.RecipeIngredientListXRef
import ramseybros.WhatsForDinner.data.RecipeUtensil
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

    fun updateRecipeNOTRecommended(recipeId: UUID) {
        executor.execute {
            whatsForDinnerDao.updateRecipeNOTRecommended(recipeId)
        }
    }


    fun updateIngredient(ingredient: Ingredient) {
        executor.execute {
            whatsForDinnerDao.updateIngredient(ingredient)
        }
    }

    fun addIngredientToList(recipeIngredient: RecipeIngredientListXRef) {
        executor.execute {
            whatsForDinnerDao.addIngredientToList(recipeIngredient = recipeIngredient)
        }
    }

    fun addUtensilToList(recipeIngredient: RecipeUtensil) {
        executor.execute {
            whatsForDinnerDao.addUtensilToList(recipeIngredient = recipeIngredient)
        }
    }
    fun getRecommendedRecipes(): LiveData<MutableList<Recipe>> = whatsForDinnerDao.getRecommendedRecipes()
    fun getRecipes(): LiveData<List<Recipe>> = whatsForDinnerDao.getRecipes()
    fun getRecipe(id: UUID): LiveData<Recipe>? = whatsForDinnerDao.getRecipe(id)
    fun getIngredients(): LiveData<List<Ingredient>> = whatsForDinnerDao.getIngredients()
    fun getIngredient(name: String): LiveData<Ingredient>? = whatsForDinnerDao.getIngredient(name)
    fun deleteRecipe(recipe: Recipe) {
        executor.execute {
            whatsForDinnerDao.deleteRecipe(recipe)
        }
    }
    fun deleteIngredient(ingredient: Ingredient) {
        executor.execute {
            whatsForDinnerDao.deleteIngredient(ingredient)
        }
    }

    fun getIngredientList(recipeId: UUID): LiveData<List<String>> =
        whatsForDinnerDao.getIngredientList(recipeId)

    fun getUtensilList(recipeId: UUID): LiveData<List<String>> =
        whatsForDinnerDao.getUtensilList(recipeId)

    fun deleteRecipeFromList(recipeId: UUID) {
        executor.execute {
            whatsForDinnerDao.deleteRecipeFromList(recipeId)
        }
    }

    fun deleteRecipeFromUtensils(recipeId: UUID) {
        executor.execute {
            whatsForDinnerDao.deleteRecipeFromUtensils(recipeId)
        }
    }

    fun deleteIngredientFromRecipe(ingredientName: String, recipeId: UUID) {
        executor.execute {
            whatsForDinnerDao.deleteIngredientFromRecipe(ingredientName, recipeId)
        }
    }
}