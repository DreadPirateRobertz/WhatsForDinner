package ramseybros.WhatsForDinner.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import ramseybros.WhatsForDinner.ui.navigation.specs.IScreenSpec
import ramseybros.WhatsForDinner.viewmodels.I_WhatsForDinnerViewModel

@Composable
fun WhatsForDinnerBottomBar(navController: NavHostController, viewModel: I_WhatsForDinnerViewModel){
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    IScreenSpec.BottomBar(
        navController = navController,
        navBackStackEntry = navBackStackEntry,
        viewModel = viewModel
    )


}