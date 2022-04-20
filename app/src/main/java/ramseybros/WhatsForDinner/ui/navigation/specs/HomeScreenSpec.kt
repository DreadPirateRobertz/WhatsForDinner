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
import ramseybros.WhatsForDinner.R

object HomeScreenSpec : IScreenSpec {
    override val route: String
        get() = "home"
    override val title: Int = R.string.app_name
    override val arguments: List<NamedNavArgument> = emptyList()
//        listOf(
//            navArgument(ARG) {type = NavType.StringType }
//        )

    @Composable
    override fun Content(
        viewModel: I_WhatsForDinnerViewModel,
        navController: NavHostController,
        backStackEntry: NavBackStackEntry
    ) {
        val recentRecipesList: List<Recipe> = listOf(RecipeGenerator.placeHolderRecipe())
        val recommendedRecipesList: List<Recipe> = listOf(RecipeGenerator.placeHolderRecipe())
        val recommendedIngredientsList: List<Ingredient> =
            listOf(RecipeGenerator.placeHolderIngredients())


        HomeScreen(
            ////////TODO: FIX PlaceHolder
            recentRecipesList = recentRecipesList,
            recommendedIngredientsList = recommendedIngredientsList,
            recommendedRecipesList = recommendedRecipesList,
            onSelectRecipe =
            { recipe ->
                navController.navigate(LargeRecipeScreenSpec.navigateTo(recipe.id.toString()))
            },
            onSelectIngredient = {}
        )

    }

    @Composable
    override fun TopAppBarActions(navController: NavHostController) {
    }

    override fun navigateTo(vararg args: String?): String {
        return route
    }
}