package ramseybros.WhatsForDinner.ui.navigation.specs
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.*
import ramseybros.WhatsForDinner.data.Ingredient
import ramseybros.WhatsForDinner.data.Recipe
import ramseybros.WhatsForDinner.ui.screens.HomeScreen
import ramseybros.WhatsForDinner.ui.screens.LoadingScreen
import ramseybros.WhatsForDinner.util.RecipeGenerator
import java.util.jar.Attributes


object LoadingScreenSpec: IScreenSpec {
    override val route: String
        get() = "load"
    override val arguments: List<String> = emptyList()

    @Composable
    override fun content(
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

    override fun navigateTo(vararg args: String?): String {
        return route
    }

}