package ramseybros.WhatsForDinner.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ramseybros.WhatsForDinner.ui.navigation.specs.IScreenSpec

@Composable
fun WhatsForDinnerNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination =  IScreenSpec.start
    ) {
        IScreenSpec.map.forEach { (route, screen) ->
            composable(
                route = route!!
            ) { backStackEntry ->
                screen?.content(
                    navController = navController,
                    backStackEntry = backStackEntry
                )
            }
        }
    }
}