package ramseybros.WhatsForDinner.ui.navigation.specs

import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import ramseybros.WhatsForDinner.R
import ramseybros.WhatsForDinner.ui.screens.RecipeInformation
import ramseybros.WhatsForDinner.util.RecipeGenerator
import ramseybros.WhatsForDinner.viewmodels.I_WhatsForDinnerViewModel

object RecipeInformationScreenSpec : IScreenSpec {
    override val route: String = "RecipeInformationScreen"
    override val arguments: List<NamedNavArgument> = emptyList()
    override val title: Int = R.string.recipe_info_screen_title


    @Composable
    override fun TopAppBarActions(
        navController: NavHostController,
        viewModel: I_WhatsForDinnerViewModel
    ) {
    }

    override fun navigateTo(vararg args: String?): String {
        return route
    }

    @Composable
    override fun Content(
        viewModel: I_WhatsForDinnerViewModel,
        navController: NavHostController,
        backStackEntry: NavBackStackEntry
    ) {
        val recipe = RecipeGenerator.singleRecipe()
        val ingredientList = RecipeGenerator.recipeIngredientList()
        val utensilList = RecipeGenerator.recipeUtensilList()
        RecipeInformation(
            recipe = recipe,
            inKitchenList = listOf("Garlic", "Paprika", "Ground Black Pepper", "Spoon", "Whisk"),
            ingredientList = ingredientList,
            utensilList = utensilList
        )
    }
}