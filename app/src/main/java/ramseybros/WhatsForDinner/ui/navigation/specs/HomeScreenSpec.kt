package ramseybros.WhatsForDinner.ui.navigation.specs

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.*
import ramseybros.WhatsForDinner.data.Ingredient
import ramseybros.WhatsForDinner.data.Recipe
import ramseybros.WhatsForDinner.ui.screens.HomeScreen
import ramseybros.WhatsForDinner.util.RecipeGenerator
import ramseybros.WhatsForDinner.viewmodels.I_WhatsForDinnerViewModel
import java.util.jar.Attributes


object HomeScreenSpec: IScreenSpec {
    override val route: String = "home"

    override val arguments: List<String> = emptyList()

    @Composable
    override fun Content(
        viewModel: I_WhatsForDinnerViewModel,
        navController: NavHostController,
        backStackEntry: NavBackStackEntry) {
        val recentRecipesList: List<Recipe> = listOf(RecipeGenerator.placeHolderRecipe())
        val recommendedRecipesList: List<Recipe> = listOf(RecipeGenerator.placeHolderRecipe())
        val recommendedIngredientsList: List<Ingredient> =
            listOf(RecipeGenerator.placeHolderIngredients())
        HomeScreen(
            recentRecipesList = recentRecipesList,
            recommendedIngredientsList = recommendedIngredientsList,
            recommendedRecipesList = recommendedRecipesList,
            onSelectRecipe =
                fun (recipe: Recipe){
                    navController.navigate(LargeRecipeScreenSpec.navigateTo())},
            onSelectIngredient = {}
        )

    }

    override fun navigateTo(vararg args: String?): String {
       return route
    }
}