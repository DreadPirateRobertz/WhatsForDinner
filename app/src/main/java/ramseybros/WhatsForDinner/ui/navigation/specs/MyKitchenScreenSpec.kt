package ramseybros.WhatsForDinner.ui.navigation.specs

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import ramseybros.WhatsForDinner.ui.screens.MyKitchen
import ramseybros.WhatsForDinner.viewmodels.I_WhatsForDinnerViewModel

object MyKitchenScreenSpec : IScreenSpec {
    override var route: String = "MyKitchenScreen"
    override val arguments: List<String>
        get() = listOf()

    @Composable
    override fun Content(
        viewModel: I_WhatsForDinnerViewModel,
        navController: NavHostController,
        backStackEntry: NavBackStackEntry
    ) {
        MyKitchen {}
    }

    override fun navigateTo(vararg args: String?): String {
        return route
    }
}