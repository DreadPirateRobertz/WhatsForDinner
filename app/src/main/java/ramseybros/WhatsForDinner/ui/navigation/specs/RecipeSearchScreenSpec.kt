package ramseybros.WhatsForDinner.ui.navigation.specs

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import ramseybros.WhatsForDinner.ui.screens.RecipeSearchScreen

object RecipeSearchScreenSpec : IScreenSpec {
    override val route: String
        get() = "RecipeSearchScreen"
    override val arguments: List<String>
        get() = listOf()

    @Composable
    override fun content(navController: NavHostController, backStackEntry: NavBackStackEntry) {
        RecipeSearchScreen()
    }

    override fun navigateTo(vararg args: String?): String {
        return route
    }
}