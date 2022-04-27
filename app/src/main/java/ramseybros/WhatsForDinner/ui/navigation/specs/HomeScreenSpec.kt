package ramseybros.WhatsForDinner.ui.navigation.specs

import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.*
import ramseybros.WhatsForDinner.data.Ingredient
import ramseybros.WhatsForDinner.data.Recipe
import ramseybros.WhatsForDinner.ui.screens.HomeScreen
import ramseybros.WhatsForDinner.util.RecipeGenerator
import ramseybros.WhatsForDinner.viewmodels.I_WhatsForDinnerViewModel
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
        val savedRecipesList: List<Recipe>? = viewModel.recipeListLiveData.observeAsState().value //This is essentially the saved Recipes
        val recommendedRecipesList: List<Recipe> = listOf(RecipeGenerator.placeHolderRecipe())
        val recommendedIngredientsList: List<Ingredient> =
            listOf(RecipeGenerator.placeHolderIngredients())


        HomeScreen(
            ////////TODO: FIX PlaceHolder
            savedRecipesList = savedRecipesList,
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
    override fun TopAppBarActions(
        navController: NavHostController,
        viewModel: I_WhatsForDinnerViewModel
    ) {
    }

    override fun navigateTo(vararg args: String?): String {
        return route
    }
}