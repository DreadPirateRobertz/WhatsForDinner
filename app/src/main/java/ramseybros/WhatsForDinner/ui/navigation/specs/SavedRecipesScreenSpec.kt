package ramseybros.WhatsForDinner.ui.navigation.specs

import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import ramseybros.WhatsForDinner.R
import ramseybros.WhatsForDinner.data.Recipe
import ramseybros.WhatsForDinner.ui.screens.SavedRecipesScreen
import ramseybros.WhatsForDinner.viewmodels.I_WhatsForDinnerViewModel

object SavedRecipesScreenSpec : IScreenSpec {
    override val route: String
        get() = "saved"
    override val arguments: List<NamedNavArgument> = emptyList()
    override val title: Int = R.string.saved_recipes_screen_title

    @Composable
    override fun Content(
        viewModel: I_WhatsForDinnerViewModel,
        navController: NavHostController,
        backStackEntry: NavBackStackEntry
    ) {

        val savedRecipesList: List<Recipe>? = viewModel.recipeListLiveData.observeAsState().value
        SavedRecipesScreen(
            savedRecipesList = savedRecipesList,
            onSelectRecipe = { recipe ->
                viewModel.setRecipeIdLiveData(recipe.id)
                navController.navigate(LargeRecipeScreenSpec.navigateTo(recipe.id.toString()))

            }
        )

    }

    @Composable
    override fun TopAppBarActions(
        navController: NavHostController,
        viewModel: I_WhatsForDinnerViewModel
    ) {

    }

    override fun navigateTo(vararg args: String?): String {
        return route
    }
}