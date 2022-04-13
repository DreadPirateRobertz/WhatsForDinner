package ramseybros.WhatsForDinner.ui.navigation.specs

import ramseybros.WhatsForDinner.ui.screens.SplashScreen


import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.*
import ramseybros.WhatsForDinner.R
import ramseybros.WhatsForDinner.data.Ingredient
import ramseybros.WhatsForDinner.data.Recipe
import ramseybros.WhatsForDinner.ui.screens.HomeScreen
import ramseybros.WhatsForDinner.util.RecipeGenerator
import ramseybros.WhatsForDinner.viewmodels.I_WhatsForDinnerViewModel
import java.util.jar.Attributes


object SplashScreenSpec: IScreenSpec {
    override val route: String
        get() = "splash"
    override val arguments: List<NamedNavArgument> = emptyList()
    override val title: Int = R.string.app_name
    @Composable
    override fun Content(
        viewModel: I_WhatsForDinnerViewModel,
        navController: NavHostController,
        backStackEntry: NavBackStackEntry) {
        SplashScreen(navController = navController)
    }

    @Composable
    override fun TopAppBarActions(navController: NavHostController) {}
    override fun navigateTo(vararg args: String?): String {
        return route
    }
}