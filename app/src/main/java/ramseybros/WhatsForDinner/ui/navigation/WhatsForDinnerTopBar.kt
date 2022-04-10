package ramseybros.WhatsForDinner.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import ramseybros.WhatsForDinner.ui.navigation.specs.IScreenSpec

@Composable
fun WhatsForDinnerTopBar(navController: NavHostController){
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    IScreenSpec.TopBar(
        navController = navController,
        navBackStackEntry = navBackStackEntry
    )
}