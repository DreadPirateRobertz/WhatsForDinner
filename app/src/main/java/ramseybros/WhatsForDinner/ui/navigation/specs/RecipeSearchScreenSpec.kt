package ramseybros.WhatsForDinner.ui.navigation.specs

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.SemanticsProperties.ImeAction
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import kotlinx.coroutines.*
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import org.json.JSONObject
import ramseybros.WhatsForDinner.R
import ramseybros.WhatsForDinner.data.Recipe
import ramseybros.WhatsForDinner.ui.screens.RecipeSearchScreen
import ramseybros.WhatsForDinner.ui.theme.colorDarkError
import ramseybros.WhatsForDinner.viewmodels.I_WhatsForDinnerViewModel

object RecipeSearchScreenSpec : IScreenSpec {
    override val route: String
        get() = "RecipeSearchScreen"
    override val arguments: List<NamedNavArgument>
        get() = listOf()
    override val title: Int = R.string.recipe_search_screen_title

    private const val LOG_TAG = "ramseybros.RecipeSearchScreenSpec"

    @Composable
    override fun Content(
        viewModel: I_WhatsForDinnerViewModel,
        navController: NavHostController,
        backStackEntry: NavBackStackEntry
    ) {
        val recipeLiveData = viewModel.getApiRecipeLiveData()
        val coroutineScope = rememberCoroutineScope()
        fun onRequestList() {
            coroutineScope.launch {
                val apiData = withContext(Dispatchers.IO) { makeApiListRequest() }
                withContext(Dispatchers.IO) { parseListJSON(apiData, viewModel) } //updates snapshotstatelist in viewModel, no need to return
            }
        }

        RecipeSearchScreen(
            viewModel,
            { onRequestList() },
            onRequestRecipe = fun (recipe: Recipe) {
                coroutineScope.launch {
                    val apiData = withContext(Dispatchers.IO) { makeApiRecipeRequest(recipe)}
                    withContext(Dispatchers.IO) { parseRecipeJSON(apiData,recipe) }
                    recipeLiveData.value = recipe
                    Log.d(LOG_TAG, "Calling navigateTo() with ${recipe.searchId} on ${Thread.currentThread().name}")
                    withContext(Dispatchers.Main){navController.navigate(LargeRecipeScreenSpec.navigateTo("search"))}
                }
            }
        )
    }


    @OptIn(ExperimentalComposeUiApi::class)
    @Composable
    override fun TopAppBarActions(navController: NavHostController) {
        SearchBar(searchText = "", "SEARCH BAR", {}, {}, {})
        }




    @OptIn(ExperimentalComposeUiApi::class)
    @Composable fun SearchBar(searchText: String,
                              placeholderText: String = "",
                              onSearchTextChanged: (String) -> Unit = {},
                              onClearClick: () -> Unit = {},
                              onNavigateBack: () -> Unit = {}
    ) {
        var showClearButton by remember { mutableStateOf(false) }
        val keyboardController = LocalSoftwareKeyboardController.current
        val focusRequester = remember { FocusRequester() }
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 2.dp)
                .onFocusChanged { focusState ->
                    showClearButton = (focusState.isFocused)
                }
                .focusRequester(focusRequester),
            value = searchText,
            onValueChange = onSearchTextChanged,
            placeholder = {
                Text(text = placeholderText)
            },
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                backgroundColor = Color.Transparent,
                cursorColor = LocalContentColor.current.copy(alpha = LocalContentAlpha.current)
            ),
            trailingIcon = {
                AnimatedVisibility(
                    visible = showClearButton,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    IconButton(onClick = { onClearClick() }) {
                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = "Clear PlaceHolder"
                        )
                    }

                }
            },
            maxLines = 1,
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = androidx.compose.ui.text.input.ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = {
                keyboardController?.hide()
            }),
        )

        LaunchedEffect(Unit) {
            focusRequester.requestFocus()
        }
    }

    @Composable
    fun NoSearchResults() {

        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center,
            horizontalAlignment = CenterHorizontally
        ) {
            Text("No matches found")
        }
    }

    @ExperimentalComposeUiApi
    @Composable
    fun SearchBarUI(
        searchText: String,
        placeholderText: String = "",
        onSearchTextChanged: (String) -> Unit = {},
        onClearClick: () -> Unit = {},
        onNavigateBack: () -> Unit = {},
        matchesFound: Boolean,
        results: @Composable () -> Unit = {}
    ) {

        Box {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {

                SearchBar(
                    searchText,
                    placeholderText,
                    onSearchTextChanged,
                    onClearClick,
                    onNavigateBack
                )

                if (matchesFound) {
                    Text("Results", modifier = Modifier.padding(8.dp), fontWeight = FontWeight.Bold)
                    results()
                } else {
                    if (searchText.isNotEmpty()) {
                        NoSearchResults()
                    }

                }
            }
        }
    }











    override fun navigateTo(vararg args: String?): String {
        return route
    }

    private suspend fun makeApiListRequest(): String {
        Log.d(LOG_TAG, "makeApiListRequest() function called")
        val ingredients: String = "chicken%2Crice&2cbeans"
        val client = OkHttpClient()
        val apiData: String?
        val request: Request = Request.Builder()
            .url("https://spoonacular-recipe-food-nutrition-v1.p.rapidapi.com/recipes/findByIngredients?ingredients=$ingredients&number=10&ignorePantry=true&ranking=1")
            .get()
            .addHeader("x-rapidapi-host", "spoonacular-recipe-food-nutrition-v1.p.rapidapi.com")
            .addHeader("x-rapidapi-key", "61306f5afemsh027abae29051434p12c68bjsnd2f5b7c20c9c")
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

    private suspend fun parseListJSON(apiData: String, viewModel: I_WhatsForDinnerViewModel): SnapshotStateList<Recipe> {
        //parses the JSON response
        Log.d(LOG_TAG, "parseListJSON() function called")
        val recipeList = viewModel.getApiRecipeList()
        Log.d(LOG_TAG, "apiData contains $apiData")
        val items = JSONArray(apiData)
        for (i in (0 until items.length())) {
            val recipeObject = items.getJSONObject(i)
            val recipe = Recipe(
                imageLink = recipeObject.getString("image"),
                title = recipeObject.getString("title"),
                difficulty = 0,
                time = "Click for more",
                recipeText = "Click for more", //TODO: use separate API call for these
                searchId = recipeObject.getInt("id")
            )
            Log.d(LOG_TAG, "Recipe $i: ${recipe.title}")
            recipeList.add(recipe)
        }
        return recipeList
    }

    private suspend fun makeApiRecipeRequest(recipe: Recipe) : String {
        Log.d(LOG_TAG, "makeApiRecipeRequest() function called")
        val client = OkHttpClient()
        val apiData: String?
        val request: Request = Request.Builder()
            .url("https://spoonacular-recipe-food-nutrition-v1.p.rapidapi.com/recipes/${recipe.searchId}/information?includeNutrition=false")
            .get()
            .addHeader("x-rapidapi-host", "spoonacular-recipe-food-nutrition-v1.p.rapidapi.com")
            .addHeader("x-rapidapi-key", "61306f5afemsh027abae29051434p12c68bjsnd2f5b7c20c9c")
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

    private suspend fun parseRecipeJSON(apiData: String, recipe: Recipe) {
        Log.d(LOG_TAG, "parseRecipeJSON() function called")
        Log.d(LOG_TAG, "apiData contains $apiData")
        val properties = JSONObject(apiData)
        recipe.recipeText = properties.getString("instructions")
        recipe.time = properties.getString("readyInMinutes")
    }

}