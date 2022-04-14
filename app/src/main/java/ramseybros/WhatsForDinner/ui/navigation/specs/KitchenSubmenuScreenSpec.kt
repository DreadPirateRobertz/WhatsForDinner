package ramseybros.WhatsForDinner.ui.navigation.specs

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import ramseybros.WhatsForDinner.R
import ramseybros.WhatsForDinner.ui.screens.KitchenList
import ramseybros.WhatsForDinner.ui.screens.ShoppingList
import ramseybros.WhatsForDinner.ui.theme.colorDarkError
import ramseybros.WhatsForDinner.viewmodels.I_WhatsForDinnerViewModel

object KitchenSubmenuScreenSpec : IScreenSpec {
    override val route: String = "KitchenSubmenu"
    override val arguments: List<NamedNavArgument> = emptyList()
    override val title: Int = R.string.kitchen_submenu_screen_title

    override fun navigateTo(vararg args: String?): String {
        return route
    }

    @Composable
    override fun Content(
        viewModel: I_WhatsForDinnerViewModel,
        navController: NavHostController,
        backStackEntry: NavBackStackEntry
    ) {
        var headerList: List<String> = listOf(
            "Ingredients",
            "Utensils",
            "Utensils",
            "Utensils",
            "Utensils",
            "Utensils",
            "Utensils",
            "Ingredients"
        )
        var ingredientList: List<String> = listOf(
            "Garlic",
            "Kosher Salt",
            "Paprika",
            "Ground Black Pepper",
            "Cream",
            "Chicken Breast",
            "Beef Stock",
            "Rosemary"
        )
        var utensilList: List<String> = listOf("Spoon", "Whisk", "Pan")
        var itemList: List<List<String>> = listOf(
            ingredientList,
            utensilList,
            utensilList,
            utensilList,
            utensilList,
            utensilList,
            utensilList,
            ingredientList
        )
        KitchenList(itemList = itemList, headerList = headerList, {})
    }

    @Composable
    override fun TopAppBarActions(navController: NavHostController) {
        var color: Color = colorDarkError
        if (!isSystemInDarkTheme()) color = colorResource(R.color.white)
        else color = colorResource(id = R.color.black)
        IconButton(
            onClick = { navController.navigate(HomeScreenSpec.navigateTo()) }
        ) {
            Icon(
                imageVector = Icons.Filled.Home,
                contentDescription = null,
                tint = color
            )
        }
    }
}