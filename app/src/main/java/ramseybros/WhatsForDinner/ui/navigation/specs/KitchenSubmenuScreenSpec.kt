package ramseybros.WhatsForDinner.ui.navigation.specs

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import ramseybros.WhatsForDinner.ui.screens.KitchenList
import ramseybros.WhatsForDinner.ui.screens.ShoppingList

object KitchenSubmenuScreenSpec : IScreenSpec {
    override val route: String = "KitchenSubmenu"
    override val arguments: List<String> = emptyList()

    override fun navigateTo(vararg args: String?): String {
        return route
    }

    @Composable
    override fun content(navController: NavHostController, backStackEntry: NavBackStackEntry) {
        var headerList: List<String> = listOf("Ingredients", "Utensils", "Utensils", "Utensils", "Utensils", "Utensils", "Utensils", "Ingredients")
        var ingredientList: List<String> = listOf("Garlic", "Kosher Salt", "Paprika", "Ground Black Pepper", "Cream","Chicken Breast", "Beef Stock", "Rosemary")
        var utensilList: List<String> = listOf("Spoon", "Whisk", "Pan")
        var itemList: List<List<String>> = listOf(ingredientList, utensilList, utensilList, utensilList, utensilList, utensilList, utensilList, ingredientList)
        KitchenList(itemList = itemList, headerList = headerList, {})
    }

}