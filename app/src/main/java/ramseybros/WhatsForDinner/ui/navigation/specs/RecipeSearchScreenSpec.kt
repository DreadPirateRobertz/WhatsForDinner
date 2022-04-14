package ramseybros.WhatsForDinner.ui.navigation.specs

import android.util.Log
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.work.WorkInfo
import org.json.JSONArray
import ramseybros.WhatsForDinner.R
import ramseybros.WhatsForDinner.data.Recipe
import ramseybros.WhatsForDinner.ui.screens.RecipeSearchScreen
import ramseybros.WhatsForDinner.util.RecipeWorker
import ramseybros.WhatsForDinner.viewmodels.I_WhatsForDinnerViewModel
import java.lang.StringBuilder

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
        val sb = StringBuilder()
        workInfoState.value?.let { workInfo ->
            when(workInfo.state) {
                WorkInfo.State.ENQUEUED -> Log.d(LOG_TAG, "workInfo enqueued")
                WorkInfo.State.RUNNING -> {
                    //gets the segments of the JSON request while the worker is running
                    Log.d(LOG_TAG, "workInfo running")
                    val value = RecipeWorker.getApiData(workInfo.progress)
                    sb.append(value)
                    Log.d(LOG_TAG, "sb contains ${sb.length.toString()}")
                }
                WorkInfo.State.SUCCEEDED -> {
                    //parses the JSON response after it has been assembled
                    Log.d(LOG_TAG, "workInfo succeeded")
                    val recipeList = viewModel.getApiRecipeList()
                    recipeList.value = mutableListOf()
                    val apiData = sb.toString()
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
                        recipeList.value!!.add(recipe)
                    }
                }
                else -> Log.d(LOG_TAG, "other workInfo state")
            }
        }
        RecipeSearchScreen { viewModel.requestWebRecipes() }
    }


    @Composable
    override fun TopAppBarActions(navController: NavHostController) {
        IconButton(
            onClick = { navController.navigate(HomeScreenSpec.navigateTo()) }
        ) {
            Icon(
                imageVector = Icons.Filled.Home,
                contentDescription = null
            )
        }
    }

    override fun navigateTo(vararg args: String?): String {
        return route
    }
}