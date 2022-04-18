package ramseybros.WhatsForDinner.viewmodels



import android.content.Context
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkInfo
import androidx.work.WorkManager
import ramseybros.WhatsForDinner.data.Ingredient
import ramseybros.WhatsForDinner.data.Recipe
import ramseybros.WhatsForDinner.data.RecipeIngredientListXRef
import ramseybros.WhatsForDinner.data.RecipeUtensil
import ramseybros.WhatsForDinner.data.database.WhatsForDinnerRepository
import ramseybros.WhatsForDinner.util.RecipeWorker

import java.util.*


class WhatsForDinnerViewModel(
    private val whatsForDinnerRepository: WhatsForDinnerRepository,
    context: Context
) : I_WhatsForDinnerViewModel() {
    private val workManager = WorkManager.getInstance(context)
    private val workRequest = RecipeWorker.buildOneTimeWorkRequest()
    override val outputWorkerInfo: LiveData<WorkInfo> =
        workManager.getWorkInfoByIdLiveData(workRequest.id)

    private val _apiRecipeListLiveData =
        MutableLiveData<MutableList<Recipe>>()

    private val _apiRecipeLiveData =
        MutableLiveData<Recipe>()

    private val _recipeIdLiveData =
        MutableLiveData<UUID>()

    private val _ingredientIdLiveData =
        MutableLiveData<String>()

    override val recipeListLiveData = whatsForDinnerRepository.getRecipes()
    override val ingredientListLiveData = whatsForDinnerRepository.getIngredients()


    override val recipeLiveData = Transformations.switchMap(_recipeIdLiveData) { recipeId ->
        whatsForDinnerRepository.getRecipe(recipeId)
    }

    override val ingredientLiveData =
        Transformations.switchMap(_ingredientIdLiveData) { ingredientId ->
            whatsForDinnerRepository.getIngredient(ingredientId)
        }


    override fun getRecipeIngredientList(recipeId: UUID) =
        whatsForDinnerRepository.getIngredientList(recipeId)

    override fun getRecipeUtensilList(recipeId: UUID) =
        whatsForDinnerRepository.getUtensilList(recipeId)

    override fun getApiRecipeList(): MutableLiveData<MutableList<Recipe>> = _apiRecipeListLiveData
    override fun getApiRecipeLiveData(): MutableLiveData<Recipe> = _apiRecipeLiveData

    override fun addRecipe(recipe: Recipe, ingredients: List<Ingredient>, utensils: List<String>) {
        whatsForDinnerRepository.addRecipe(recipe)
        for(ingredient in ingredients) {
            whatsForDinnerRepository.addIngredient(ingredient)
            whatsForDinnerRepository.addIngredientToList(
                RecipeIngredientListXRef(
                    recipe.id,
                    ingredient.name
                )
            )
        }
        for(utensil in utensils) {
            whatsForDinnerRepository.addUtensilToList(RecipeUtensil(utensil,recipe.id))
        }
    }

    override fun requestWebRecipes() {
        TODO("Not yet implemented")
    }

}