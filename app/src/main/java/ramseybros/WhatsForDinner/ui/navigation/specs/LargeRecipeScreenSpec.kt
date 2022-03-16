package ramseybros.WhatsForDinner.ui.navigation.specs

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import ramseybros.WhatsForDinner.ui.screens.LargeRecipeView
import ramseybros.WhatsForDinner.util.RecipeGenerator

object LargeRecipeScreenSpec : IScreenSpec {
    override val route: String = "LargeRecipeScreen"
    override val arguments: List<String> = emptyList()

    override fun navigateTo(vararg args: String?): String {
        return route
    }

    @Composable
    override fun content(navController: NavHostController, backStackEntry: NavBackStackEntry) {
        LargeRecipeView(recipe = RecipeGenerator.singleRecipe(), onSave = {}, onBack = {navController.navigate(RecipeSearchScreenSpec.navigateTo())}, inKitchenList = listOf("Garlic", "Paprika", "Ground Black Pepper", "Spoon", "Whisk"))
    }

}