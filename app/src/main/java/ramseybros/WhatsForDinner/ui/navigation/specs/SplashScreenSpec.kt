package ramseybros.WhatsForDinner.ui.navigation.specs

import ramseybros.WhatsForDinner.ui.screens.SplashScreen


import androidx.compose.runtime.Composable
import androidx.navigation.*
import ramseybros.WhatsForDinner.R
import ramseybros.WhatsForDinner.viewmodels.I_WhatsForDinnerViewModel


object SplashScreenSpec : IScreenSpec {
    override val route: String
        get() = "splash"
    override val arguments: List<NamedNavArgument> = emptyList()
    override val title: Int = R.string.app_name

    @Composable
    override fun Content(
        viewModel: I_WhatsForDinnerViewModel,
        navController: NavHostController,
        backStackEntry: NavBackStackEntry
    ) {
        SplashScreen(navController = navController)
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