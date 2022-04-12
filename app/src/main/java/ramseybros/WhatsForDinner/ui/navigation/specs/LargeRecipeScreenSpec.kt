package ramseybros.WhatsForDinner.ui.navigation.specs

import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import ramseybros.WhatsForDinner.R
import ramseybros.WhatsForDinner.ui.screens.LargeRecipeView
import ramseybros.WhatsForDinner.util.RecipeGenerator
import ramseybros.WhatsForDinner.viewmodels.I_WhatsForDinnerViewModel

object LargeRecipeScreenSpec : IScreenSpec {
    override val route: String = "LargeRecipeScreen"
    override val arguments: List<NamedNavArgument> = emptyList()
    override val title: Int = R.string.large_recipe_screen_title


    @Composable
    override fun TopAppBarActions(navController: NavHostController) {}

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
        LargeRecipeView(recipe = recipe, onSave = {}, onBack = {navController.navigate(RecipeSearchScreenSpec.navigateTo())}, inKitchenList = listOf("Garlic", "Paprika", "Ground Black Pepper", "Spoon", "Whisk"), ingredientList = ingredientList, utensilList = utensilList)
    }

}