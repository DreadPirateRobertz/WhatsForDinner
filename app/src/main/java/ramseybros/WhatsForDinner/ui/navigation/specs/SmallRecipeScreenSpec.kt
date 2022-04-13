package ramseybros.WhatsForDinner.ui.navigation.specs

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import ramseybros.WhatsForDinner.R
import ramseybros.WhatsForDinner.ui.screens.SmallRecipeView
import ramseybros.WhatsForDinner.util.RecipeGenerator
import ramseybros.WhatsForDinner.viewmodels.I_WhatsForDinnerViewModel

object SmallRecipeScreenSpec : IScreenSpec {
    override val route: String
        get() = "SmallRecipeScreen"
    override val arguments: List<NamedNavArgument>
        get() = listOf()
    override val title: Int = R.string.small_recipe_screen_title

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
}