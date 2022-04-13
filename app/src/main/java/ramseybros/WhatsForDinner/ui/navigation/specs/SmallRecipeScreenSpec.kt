package ramseybros.WhatsForDinner.ui.navigation.specs

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import ramseybros.WhatsForDinner.R
import ramseybros.WhatsForDinner.ui.screens.SmallRecipeView
import ramseybros.WhatsForDinner.util.RecipeGenerator
import ramseybros.WhatsForDinner.viewmodels.I_WhatsForDinnerViewModel

object SmallRecipeScreenSpec : IScreenSpec {
    override val route: String
        get() = "SmallRecipeScreen"
    override val arguments: List<String>
        get() = listOf()
    override val title: Int = R.string.app_name

    @Composable
    override fun Content(
        viewModel: I_WhatsForDinnerViewModel,
        navController: NavHostController,
        backStackEntry: NavBackStackEntry
    ) {
        SmallRecipeView(RecipeGenerator.singleRecipe()) {}
    }

    override fun navigateTo(vararg args: String?): String {
        return route
    }

    @Composable
    override fun TopAppBarActions(navController: NavHostController) {}
}