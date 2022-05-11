package ramseybros.WhatsForDinner.ui.navigation.specs

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import ramseybros.WhatsForDinner.R
import ramseybros.WhatsForDinner.ui.screens.ShoppingList
import ramseybros.WhatsForDinner.viewmodels.I_WhatsForDinnerViewModel

object ShoppingListScreenSpec : IScreenSpec {
    override val route: String = "ShoppingList"
    override val arguments: List<NamedNavArgument> = emptyList()
    override val title: Int = R.string.shopping_list_screen_title

    override fun navigateTo(vararg args: String?): String {
        return route
    }

    @Composable
    override fun Content(
        viewModel: I_WhatsForDinnerViewModel,
        navController: NavHostController,
        backStackEntry: NavBackStackEntry
    ) {
        val ingredientList = viewModel.ingredientListLiveData.observeAsState().value
        if (ingredientList != null) {
            ingredientList.forEach {
                Log.d("recipe", it.name)
            }
            ShoppingList(
                ingredientList = ingredientList,
                viewModel = viewModel,
                onDelete = { viewModel.deleteIngredient(it) }
            )
        }
    }

    @Composable
    override fun TopAppBarActions(
        navController: NavHostController,
        viewModel: I_WhatsForDinnerViewModel
    ) {

    }
}