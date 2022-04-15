package ramseybros.WhatsForDinner.ui.navigation.specs

import android.util.Log
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.work.WorkInfo
import org.json.JSONArray
import ramseybros.WhatsForDinner.R
import ramseybros.WhatsForDinner.data.Recipe
import ramseybros.WhatsForDinner.ui.screens.RecipeSearchScreen
import ramseybros.WhatsForDinner.ui.theme.colorDarkError
import ramseybros.WhatsForDinner.util.CharacterWorker
import ramseybros.WhatsForDinner.viewmodels.I_WhatsForDinnerViewModel

object RecipeSearchScreenSpec : IScreenSpec {
    override val route: String
        get() = "RecipeSearchScreen"
    override val arguments: List<NamedNavArgument>
        get() = listOf()
    override val title: Int = R.string.recipe_search_screen_title

    @Composable
    override fun Content(
        viewModel: I_WhatsForDinnerViewModel,
        navController: NavHostController,
        backStackEntry: NavBackStackEntry
    ) {
        val LOG_TAG = "ramseybros.RecipeSearchScreen"

        val workInfoState = viewModel.outputWorkerInfo.observeAsState()
        workInfoState.value?.let { workInfo ->
            when (workInfo.state) {
                WorkInfo.State.ENQUEUED -> Log.d(LOG_TAG, "workInfo enqueued")
                WorkInfo.State.RUNNING -> Log.d(LOG_TAG, "workInfo running")
                WorkInfo.State.SUCCEEDED -> {
                    Log.d(LOG_TAG, "workInfo succeeded")
                    val recipeList = viewModel.getApiRecipeList()
                    recipeList.value = mutableListOf()
                    val apiData = CharacterWorker.getApiData(workInfo.outputData)
                    Log.d(LOG_TAG, "apiData contains $apiData")
                    if (apiData != null) {
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
                            recipeList.value!!.add(recipe)
                        }
                    } else {
                        Log.d(LOG_TAG, "api call returned null")
                    }
                }
                else -> Log.d(LOG_TAG, "other workInfo state")
            }
        }
        RecipeSearchScreen { viewModel.requestWebRecipes() }
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
}