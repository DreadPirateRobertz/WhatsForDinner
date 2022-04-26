package ramseybros.WhatsForDinner.viewmodels



import android.content.Context
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkInfo
import androidx.work.WorkManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import ramseybros.WhatsForDinner.data.*
import ramseybros.WhatsForDinner.data.database.WhatsForDinnerRepository
import ramseybros.WhatsForDinner.ui.navigation.specs.LargeRecipeScreenSpec
import ramseybros.WhatsForDinner.util.RecipeGenerator
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

    private val _apiRecipeListLiveData = mutableStateListOf<Recipe>()

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

    override fun setRecipeIdLiveData(uuid: UUID) {
       _recipeIdLiveData.value = uuid
    }

    override fun getRecipe(recipeId: UUID): LiveData<Recipe>? {
       return whatsForDinnerRepository.getRecipe(recipeId)
    }
    override fun getRecipeIngredientList(recipeId: UUID) =
        whatsForDinnerRepository.getIngredientList(recipeId)

    override fun getRecipeUtensilList(recipeId: UUID) =
        whatsForDinnerRepository.getUtensilList(recipeId)

    override fun getApiRecipeList(): SnapshotStateList<Recipe> = _apiRecipeListLiveData
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

    override fun deleteRecipe(recipe: Recipe) {
        whatsForDinnerRepository.deleteRecipeFromList(recipe.id)
        whatsForDinnerRepository.deleteRecipeFromUtensils(recipe.id)
        whatsForDinnerRepository.deleteRecipe(recipe)
    }


    private var allRecipes = mutableListOf<LiveData<Recipe>>()
    private val searchText: MutableStateFlow<String> = MutableStateFlow("")
    private var showProgressBar: MutableStateFlow<Boolean> = MutableStateFlow(false)
    private var matchedRcipes: MutableStateFlow<List<Recipe>> = MutableStateFlow(emptyList())
//Testing Search Bar Implementation
    override val RecipeSearchModelState = combine(
        searchText,
        matchedRcipes,
        showProgressBar

    ){
        text, matchedRecipes, showProgress ->
        ramseybros.WhatsForDinner.data.RecipeSearchModelState(
           text,
           matchedRecipes,
           showProgress
        )
}
    init{
        retrieveRecipes()
    }
    private fun retrieveRecipes(){
        //TODO: Wrong recipe getter right here NOT connected to API for TESTING
        val recipes = whatsForDinnerRepository.getRecipes()
        if(recipes != emptyList<Recipe>() ){
            allRecipes.addAll(recipes)
            }
    }
}

private fun <E> MutableList<E>.addAll(elements: LiveData<List<Recipe>>) {

}
