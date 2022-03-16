package ramseybros.WhatsForDinner.ui.navigation.specs

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController

sealed interface IScreenSpec {
    val route: String
    val arguments: List<String>

    companion object {
        val start: String = LoadingScreenSpec.route
        val map = IScreenSpec::class.sealedSubclasses
            .associate { it.objectInstance?.route to it.objectInstance }
    }

    @Composable
    fun content(navController: NavHostController, backStackEntry: NavBackStackEntry)

    fun navigateTo(vararg args: String?) : String

}