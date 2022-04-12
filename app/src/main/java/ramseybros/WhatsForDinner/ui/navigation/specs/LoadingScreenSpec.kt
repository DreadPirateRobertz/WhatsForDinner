package ramseybros.WhatsForDinner.ui.navigation.specs
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.*
import ramseybros.WhatsForDinner.R
import ramseybros.WhatsForDinner.data.Ingredient
import ramseybros.WhatsForDinner.data.Recipe
import ramseybros.WhatsForDinner.ui.screens.HomeScreen
import ramseybros.WhatsForDinner.ui.screens.LoadingScreen
import ramseybros.WhatsForDinner.util.RecipeGenerator
import ramseybros.WhatsForDinner.viewmodels.I_WhatsForDinnerViewModel
import java.util.jar.Attributes


object LoadingScreenSpec: IScreenSpec {
    override val route: String
        get() = "load"
    override val arguments: List<NamedNavArgument> = emptyList()
    override val title: Int = R.string.app_name
    @Composable
    override fun Content(
        viewModel: I_WhatsForDinnerViewModel,
        navController: NavHostController,
        backStackEntry: NavBackStackEntry) {
        Log.d("route", "we're here1")
        LoadingScreen(
            onLoad =
                 {
                     Log.d("route", "we're here2")
                    navController.navigate(HomeScreenSpec.navigateTo())},
        )

    }

    @Composable
    override fun TopAppBarActions(navController: NavHostController) {}
    override fun navigateTo(vararg args: String?): String {
        return route
    }

}