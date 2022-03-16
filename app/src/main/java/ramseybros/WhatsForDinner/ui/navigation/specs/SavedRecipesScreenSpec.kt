package ramseybros.WhatsForDinner.ui.navigation.specs

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import ramseybros.WhatsForDinner.data.Ingredient
import ramseybros.WhatsForDinner.data.Recipe
import ramseybros.WhatsForDinner.ui.screens.HomeScreen
import ramseybros.WhatsForDinner.ui.screens.SavedRecipesScreen
import ramseybros.WhatsForDinner.util.RecipeGenerator

object SavedRecipesScreenSpec: IScreenSpec {
    override val route: String
        get() = "saved"
    override val arguments: List<String> = emptyList()

    @Composable
    override fun content(
        navController: NavHostController,
        backStackEntry: NavBackStackEntry
    ) {
        val savedRecipesList: List<Recipe> = listOf(RecipeGenerator.placeHolderRecipe())
        SavedRecipesScreen(
            savedRecipesList = savedRecipesList,
            onSelectRecipe =
            fun (recipe: Recipe){
                navController.navigate(LargeRecipeScreenSpec.navigateTo())},
        )

    }

    override fun navigateTo(vararg args: String?): String {
        return route
    }
}