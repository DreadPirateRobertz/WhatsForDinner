package ramseybros.WhatsForDinner.ui.navigation.specs

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import ramseybros.WhatsForDinner.R
import ramseybros.WhatsForDinner.data.Ingredient
import ramseybros.WhatsForDinner.data.Recipe
import ramseybros.WhatsForDinner.ui.screens.HomeScreen
import ramseybros.WhatsForDinner.ui.screens.SavedRecipesScreen
import ramseybros.WhatsForDinner.util.RecipeGenerator
import ramseybros.WhatsForDinner.viewmodels.I_WhatsForDinnerViewModel

object SavedRecipesScreenSpec: IScreenSpec {
    override val route: String
        get() = "saved"
    override val arguments: List<NamedNavArgument> = emptyList()
    override val title: Int = R.string.saved_recipes_screen_title
    @Composable
    override fun Content(
        viewModel: I_WhatsForDinnerViewModel,
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

    @Composable
    override fun TopAppBarActions(navController: NavHostController) {
        IconButton(
            onClick = { navController.navigate(HomeScreenSpec.navigateTo()) }
        ) {
            Icon(
                imageVector = Icons.Filled.Home,
                contentDescription = null
            )
        }
    }

    override fun navigateTo(vararg args: String?): String {
        return route
    }
}