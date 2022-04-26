package ramseybros.WhatsForDinner.ui.navigation

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ramseybros.WhatsForDinner.ui.navigation.specs.*
import ramseybros.WhatsForDinner.viewmodels.I_WhatsForDinnerViewModel

@Composable
fun WhatsForDinnerNavHost(
    navController: NavHostController,
    viewModel: I_WhatsForDinnerViewModel,
    paddingValues: PaddingValues
) {
    val configuration = LocalConfiguration.current
    var paddingBottom : Dp = paddingValues.calculateBottomPadding()
    var paddingTop = paddingValues.calculateTopPadding()
    var paddingStart = paddingValues.calculateStartPadding(LayoutDirection.Ltr)
    var paddingEnd = paddingValues.calculateEndPadding(LayoutDirection.Ltr)

    if(configuration.orientation == Configuration.ORIENTATION_LANDSCAPE){
         paddingTop = 0.dp
    }
    Column(
        Modifier.padding(top = paddingTop, bottom = paddingBottom, start = paddingStart, end = paddingEnd)
    ) {


        NavHost(
            navController = navController,
            startDestination = IScreenSpec.start
        ) {
            IScreenSpec.allScreens.forEach { (route, screen) ->
                if (route != null) {
                    composable(route = route) { backStackEntry ->
                        screen?.Content(
                            viewModel,
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
}