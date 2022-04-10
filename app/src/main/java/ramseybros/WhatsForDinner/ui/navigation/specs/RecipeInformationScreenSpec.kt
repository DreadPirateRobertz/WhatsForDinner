package ramseybros.WhatsForDinner.ui.navigation.specs

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import ramseybros.WhatsForDinner.ui.screens.LargeRecipeView
import ramseybros.WhatsForDinner.ui.screens.RecipeInformation
import ramseybros.WhatsForDinner.util.RecipeGenerator
import ramseybros.WhatsForDinner.viewmodels.I_WhatsForDinnerViewModel

object RecipeInformationScreenSpec : IScreenSpec {
    override val route: String = "RecipeInformationScreen"
    override val arguments: List<String> = emptyList()

    override fun navigateTo(vararg args: String?): String {
        return route
    }

    @Composable
    override fun Content(
        viewModel: I_WhatsForDinnerViewModel,
        navController: NavHostController,
        backStackEntry: NavBackStackEntry
    ) {
        RecipeInformation(recipe = RecipeGenerator.singleRecipe(), inKitchenList = listOf("Garlic", "Paprika", "Ground Black Pepper", "Spoon", "Whisk"))
    }
}