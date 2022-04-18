package ramseybros.WhatsForDinner.ui.navigation.specs

import android.util.Log
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.work.WorkInfo
import kotlinx.coroutines.*
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import ramseybros.WhatsForDinner.R
import ramseybros.WhatsForDinner.data.Recipe
import ramseybros.WhatsForDinner.ui.screens.RecipeSearchScreen
import ramseybros.WhatsForDinner.ui.theme.colorDarkError
import ramseybros.WhatsForDinner.util.RecipeWorker
import ramseybros.WhatsForDinner.viewmodels.I_WhatsForDinnerViewModel
import java.lang.StringBuilder

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
        val coroutineScope = rememberCoroutineScope()
        fun onClick() {
            coroutineScope.launch {
                val recipeList = viewModel.getApiRecipeList()
                val apiData = withContext(Dispatchers.IO) { makeApiRequest() }
                recipeList.value = withContext(Dispatchers.IO) { parseJSON(apiData) }
            }
        }
        RecipeSearchScreen(viewModel) { onClick() }
    }


    @Composable
    override fun TopAppBarActions(navController: NavHostController) {
        var color: Color = colorDarkError
        if (!isSystemInDarkTheme()) color = colorResource(R.color.white)
        else color = colorResource(id = R.color.black)
        IconButton(
            onClick = { navController.navigate(HomeScreenSpec.navigateTo()) }
        ) {
            Icon(
                imageVector = Icons.Filled.Home,
                contentDescription = null,
                tint = color
            )
        }
    }

    override fun navigateTo(vararg args: String?): String {
        return route
    }

    suspend fun makeApiRequest(): String {
        Log.d(LOG_TAG, "makeApiRequest() function called")
        val ingredients: String = "chicken%2Crice&2cbeans"
        val client = OkHttpClient()
        val apiData: String?
        val request: Request = Request.Builder()
            .url("https://spoonacular-recipe-food-nutrition-v1.p.rapidapi.com/recipes/findByIngredients?ingredients=$ingredients&number=10&ignorePantry=true&ranking=1")
            .get()
            .addHeader("x-rapidapi-host", "spoonacular-recipe-food-nutrition-v1.p.rapidapi.com")
            .addHeader("x-rapidapi-key", "61306f5afemsh027abae29051434p12c68bjsnd2f5b7c20c9c")
            .build()
        //TODO: use different call that doesn't block execution
        val response = client.newCall(request).execute()
        apiData = response.body?.string()
        if (apiData != null) {
            Log.d(LOG_TAG, "Got result $apiData")
            Log.d(LOG_TAG, "Length ${apiData.length}")
        }
        return apiData!!
    }

    suspend fun parseJSON(apiData: String): MutableList<Recipe> {
        //parses the JSON response
        Log.d(LOG_TAG, "parseJSON() function called")
        val recipeList: MutableList<Recipe> = mutableListOf()
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

}