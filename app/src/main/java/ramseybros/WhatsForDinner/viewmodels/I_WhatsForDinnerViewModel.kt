package ramseybros.WhatsForDinner.viewmodels


import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.work.WorkInfo
import kotlinx.coroutines.flow.Flow
import ramseybros.WhatsForDinner.data.*
import java.util.*

abstract class I_WhatsForDinnerViewModel : ViewModel() {
    abstract val savedRecipeListLiveData: LiveData<MutableList<Recipe>>
    abstract val recommendedRecipeListLiveData: LiveData<MutableList<Recipe>>
    abstract val recipeLiveData: LiveData<Recipe?>
    abstract val ingredientListLiveData: LiveData<List<Ingredient>>
    abstract val ingredientLiveData: LiveData<Ingredient?>
    abstract val outputWorkerInfo: LiveData<WorkInfo>
    abstract val RecipeSearchModelState: Flow<RecipeSearchModelState>
    abstract var onHomeFlag: Boolean
    abstract fun getRecipeIngredientList(recipeId: UUID): LiveData<List<String>>
    abstract fun getRecipeUtensilList(recipeId: UUID): LiveData<List<String>>
    abstract fun getRecipe(recipeId: UUID): LiveData<Recipe>?
    abstract fun getApiRecipeList(): SnapshotStateList<Recipe>
    abstract fun getApiRecipeLiveData(): MutableLiveData<Recipe>
    abstract fun setRecipeIdLiveData(recipeId: UUID)
    abstract fun addRecipe(recipe: Recipe, ingredients: List<Ingredient>, utensils: List<String>)
    abstract fun setIngredientsToAdd(string: String)
    abstract fun addIngredientsToStore()
    abstract fun clearIngredientsFromStore()
    abstract fun deleteIngredient(ingredient: Ingredient)
    abstract fun requestWebRecipes()
    abstract fun deleteRecipe(recipe: Recipe)
    abstract fun makeApiRecipeRequest(recipe: Recipe): String
    abstract fun parseRecipeJSON(apiData: String, recipe: Recipe, viewModel: I_WhatsForDinnerViewModel): Boolean
    abstract fun parseListJSON(apiData: String, viewModel: I_WhatsForDinnerViewModel): SnapshotStateList<Recipe>
    abstract fun makeApiListRequest(string: String): String
    abstract fun onSearchTextChanged(changedSearchText: String)
    abstract fun onClearText()
    abstract fun askSpeechInput(context: Context)
    abstract fun updateRecipe(recipe: Recipe)
    abstract fun updateRecipeNOTRecommended(recipeId: UUID)
    abstract fun buildRecommendedRecipeList(recommendedRecipesList: MutableList<Recipe>?)

    abstract val test: LiveData<SnapshotStateList<Recipe>>

    abstract val test2: LiveData<SnapshotStateList<Recipe>>
}