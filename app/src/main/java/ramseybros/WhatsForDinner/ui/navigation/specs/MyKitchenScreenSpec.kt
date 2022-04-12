package ramseybros.WhatsForDinner.ui.navigation.specs

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import ramseybros.WhatsForDinner.R
import ramseybros.WhatsForDinner.ui.screens.MyKitchen
import ramseybros.WhatsForDinner.viewmodels.I_WhatsForDinnerViewModel

object MyKitchenScreenSpec : IScreenSpec {
    override var route: String = "MyKitchenScreen"
    override val arguments: List<String>
        get() = listOf()
    override val title: Int = R.string.my_kitchen_screen_title

    @Composable
    override fun Content(
        viewModel: I_WhatsForDinnerViewModel,
        navController: NavHostController,
        backStackEntry: NavBackStackEntry
    ) {
        MyKitchen {}
    }

    @Composable
    override fun TopAppBarActions(navController: NavHostController) {}

    override fun navigateTo(vararg args: String?): String {
        return route
    }
}