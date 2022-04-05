package ramseybros.WhatsForDinner.ui.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ramseybros.WhatsForDinner.ui.navigation.specs.*

@Composable
fun WhatsForDinnerNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination =  IScreenSpec.start
    ) {
        IScreenSpec.map.forEach { (route,screen) ->
            if(route != null) {
                composable( route = route ) { backStackEntry ->
                    screen?.content(
                        navController = navController,
                        backStackEntry = backStackEntry
                    )
                }
            } else {
                Log.d("route", "bruh, idk")
            }
        }
    }
}