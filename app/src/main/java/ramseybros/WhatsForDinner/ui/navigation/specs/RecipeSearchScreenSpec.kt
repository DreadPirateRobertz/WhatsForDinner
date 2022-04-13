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
        val LOG_TAG = "RecipeSearchScreen"
        var recipeList = viewModel.getApiRecipeList()
        recipeList.value = mutableListOf()
        val workInfoState = viewModel.outputWorkerInfo.observeAsState()
        workInfoState.value?.let { workInfo ->
            when(workInfo.state) {
                WorkInfo.State.ENQUEUED -> Log.d(LOG_TAG, "workInfo enqueued")
                WorkInfo.State.RUNNING -> Log.d(LOG_TAG, "workInfo running")
                WorkInfo.State.SUCCEEDED -> {
                    Log.d(LOG_TAG, "workInfo succeeded")
                    val apiData = CharacterWorker.getApiData(workInfo.outputData)
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
        RecipeSearchScreen()
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