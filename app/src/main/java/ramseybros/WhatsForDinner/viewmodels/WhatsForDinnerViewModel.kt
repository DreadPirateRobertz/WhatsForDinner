package ramseybros.WhatsForDinner.viewmodels


import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.core.app.ActivityCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.work.WorkInfo
import androidx.work.WorkManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import org.json.JSONObject
import ramseybros.WhatsForDinner.R
import ramseybros.WhatsForDinner.Secrets
import ramseybros.WhatsForDinner.data.*
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

    override var onHomeFlag: Boolean = true

    private val LOG_TAG = "ramseybros.RecipeSearchScreenSpec"
    private val _apiRecipeListLiveData = mutableStateListOf<Recipe>()

    private val _apiRecipeLiveData =
        MutableLiveData<Recipe>()

    private val _recipeIdLiveData =
        MutableLiveData<UUID>()

    private val _ingredientIdLiveData =
        MutableLiveData<String>()

    override val savedRecipeListLiveData = whatsForDinnerRepository.getSavedRecipes()

    override val recommendedRecipeListLiveData: LiveData<MutableList<Recipe>> = whatsForDinnerRepository.getRecommendedRecipes()

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
        for (ingredient in ingredients) {
            whatsForDinnerRepository.addIngredient(ingredient)
            whatsForDinnerRepository.addIngredientToList(
                RecipeIngredientListXRef(
                    recipe.id,
                    ingredient.name
                )
            )
        }
        for (utensil in utensils) {
            whatsForDinnerRepository.addUtensilToList(RecipeUtensil(utensil, recipe.id))
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

    override fun updateRecipe(recipe: Recipe){
        whatsForDinnerRepository.updateRecipe(recipe)
    }
    override fun updateRecipeNOTRecommended(recipeId: UUID){
        whatsForDinnerRepository.updateRecipeNOTRecommended(recipeId)
    }


    override fun makeApiListRequest(string: String): String {
        Log.d(LOG_TAG, "makeApiListRequest() function called")
        var ingredients: String = string.lowercase()
        if (ingredients.contains(',')) {
            ingredients = ingredients.replace(",", "%2C")
            ingredients = ingredients.replace(" ", "")
        }
        if (ingredients.contains(" ")) {
            ingredients = ingredients.replace(" ", "%2C")
            ingredients = ingredients.replace(" ", "")
        }
        val client = OkHttpClient()
        val apiData: String?
        val key = Secrets().getYvoqmyCS("ramseybros.WhatsForDinner")

        //61306f5afemsh027abae29051434p12c68bjsnd2f5b7c20c9c/
        val request: Request = Request.Builder()
            .url("https://spoonacular-recipe-food-nutrition-v1.p.rapidapi.com/recipes/findByIngredients?ingredients=$ingredients&number=10&ignorePantry=true&ranking=1")
            .get()
            .addHeader("x-rapidapi-host", "spoonacular-recipe-food-nutrition-v1.p.rapidapi.com")
            .addHeader("x-rapidapi-key", key)
            .build()
        //should be fine as a blocking call since this function executes on IO coroutine
        val response = client.newCall(request).execute()
        apiData = response.body?.string()
        response.close()
        if (apiData != null) {
            Log.d(LOG_TAG, "Got result $apiData")
            Log.d(LOG_TAG, "Length ${apiData.length}")
        }
        return apiData!!
    }

    override fun parseListJSON(
        apiData: String,
        viewModel: I_WhatsForDinnerViewModel
    ): SnapshotStateList<Recipe> {
        //parses the JSON response
        Log.d(LOG_TAG, "parseListJSON() function called")
        val recipeList = viewModel.getApiRecipeList()
        Log.d(LOG_TAG, "apiData contains $apiData")
        recipeList.clear()
        val items = JSONArray(apiData)
        for (i in (0 until items.length())) {
            val recipeObject = items.getJSONObject(i)
            val recipe = Recipe(
                imageLink = recipeObject.getString("image"),
                title = recipeObject.getString("title"),
                difficulty = 0,
                time = "Click for more",
                recipeText = "Click for more", //TODO: use separate API call for these
                searchId = recipeObject.getInt("id"),
                ingredientString = ""
            )
            Log.d(LOG_TAG, "Recipe $i: ${recipe.title}")
            val newApiData = makeApiRecipeRequest(recipe)
            if (parseRecipeJSON(newApiData, recipe, viewModel)) {
                recipeList.add(recipe)
            }
        }
        return recipeList
    }

    override fun makeApiRecipeRequest(recipe: Recipe): String {
        Log.d(LOG_TAG, "makeApiRecipeRequest() function called")
        val client = OkHttpClient()
        val apiData: String?
        val key = Secrets().getYvoqmyCS("ramseybros.WhatsForDinner")
        val request: Request = Request.Builder()
            .url("https://spoonacular-recipe-food-nutrition-v1.p.rapidapi.com/recipes/${recipe.searchId}/information?includeNutrition=false")
            .get()
            .addHeader("x-rapidapi-host", "spoonacular-recipe-food-nutrition-v1.p.rapidapi.com")
            .addHeader("x-rapidapi-key", key)
            .build()
        //same as above, this function executes on IO dispatcher. Won't block UI thread
        val response = client.newCall(request).execute()
        apiData = response.body?.string()
        if (apiData != null) {
            Log.d(LOG_TAG, "Got result $apiData")
            Log.d(LOG_TAG, "Length ${apiData.length}")
        }
        return apiData!!
    }

    override fun parseRecipeJSON(
        apiData: String,
        recipe: Recipe,
        viewModel: I_WhatsForDinnerViewModel
    ): Boolean {
        var ingredientString: String = ""
        Log.d(LOG_TAG, "parseRecipeJSON() function called")
        val recipeList = viewModel.getApiRecipeList()
        Log.d(LOG_TAG, "apiData contains $apiData")
        val properties = JSONObject(apiData)
        recipe.recipeText = checkNotNull(properties.getString("instructions"))
        Log.d("Idk anymore", "recipeText = ${recipe.recipeText}")
        val ingredientListArray = properties.getJSONArray("extendedIngredients")
        for (i in (0 until ingredientListArray.length())) {
            val ingredientInfo = ingredientListArray.getJSONObject(i)
            val ingredientObject =
                ingredientInfo.getString("amount") + " " + ingredientInfo.getString("unit") + " " + ingredientInfo.getString(
                    "name"
                )
            ingredientString = "$ingredientString,$ingredientObject"
            val ingredient = Ingredient(ingredientInfo.getString("name"),0, IngredientType.SPICE)
            Log.d("ViewModel", ingredientObject)
            //ingredientList?.add(ingredient)
        }
        recipe.ingredientString = ingredientString //returns commma separated list of ingredients in a string
        recipe.time = properties.getString("readyInMinutes")
        return (recipe.recipeText != "null")
    }

    private val searchText: MutableStateFlow<String> = MutableStateFlow("")
    private var showProgressBar: MutableStateFlow<Boolean> = MutableStateFlow(false)
    private var matchedRecipes: MutableStateFlow<List<Recipe>> = MutableStateFlow(emptyList())
    override val RecipeSearchModelState = combine(
        searchText,
        matchedRecipes,
        showProgressBar

    ) { text, matchedRecipes, showProgress ->
        RecipeSearchModelState(
            text,
            matchedRecipes,
            showProgress
        )
    }

    override fun onSearchTextChanged(changedSearchText: String) {
        searchText.value = changedSearchText
    }

    override fun onClearText() {
        searchText.value = ""
    }

  
    override fun askSpeechInput(context: Context) {
        if (if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                !SpeechRecognizer.isOnDeviceRecognitionAvailable(context)// Works if API is 31 and >
            } else {
                !SpeechRecognizer.isRecognitionAvailable(context)  //Remote
            }
        ) {
            Toast.makeText(context, "Speech Unavailable", Toast.LENGTH_SHORT).show()
        } else {
            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            intent.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH
            )
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, R.string.google_search_prompt)
            ActivityCompat.startActivityForResult(context as Activity, intent, 102, null)
        }
    }

    override fun buildRecommendedRecipeList(recommendedRecipesList: MutableList<Recipe>?){
        val apiRecipeList = getApiRecipeList()
        val savedRecipeList = savedRecipeListLiveData.value
        var fill = 30 - (recommendedRecipesList?.size ?: 0)
        apiRecipeList.forEach {
            Log.d("apirecipe", "name $fill = ${it.title}")
            if (recommendedRecipesList?.find { r -> it.title == r.title && it.imageLink == r.imageLink && it.time == r.time } == null
                && savedRecipeList?.find{ r -> it.title == r.title && it.imageLink == r.imageLink && it.time == r.time } == null) {
                if (fill > 0) {
                    val recipe = it
                    recipe.recommended = true
                    addRecipe(recipe, emptyList(), emptyList())
                    fill--
                } else {
                    val recipe = it
                    recipe.recommended = true
                    addRecipe(recipe, emptyList(), emptyList())
                    val remove = recommendedRecipesList!![0]
                    deleteRecipe(remove)
                }
            }
        }
    }
}







