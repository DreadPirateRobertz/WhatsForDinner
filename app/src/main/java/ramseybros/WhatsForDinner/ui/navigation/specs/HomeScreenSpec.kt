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
import java.util.*

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
        val savedRecipesList: List<Recipe>? = viewModel.recipeListLiveData.observeAsState().value
        val recommendedRecipesList: MutableList<Recipe>? = viewModel.recommendedRecipeListLiveData.observeAsState().value
        val recommendedIngredientsList: List<Ingredient> =
            listOf(RecipeGenerator.placeHolderIngredients())
        val recipeList = viewModel.getApiRecipeList()
        val qRecommendedRecipes: Queue<Recipe> = LinkedList()
        if(recipeList != emptyList<Recipe>()){
            recipeList.forEach { apiRecipe ->
                savedRecipesList?.forEach{ savedRecipe ->
                    if(apiRecipe != savedRecipe && !qRecommendedRecipes.contains(apiRecipe)){
                        if(qRecommendedRecipes.size == 20) {
                            val recipe = qRecommendedRecipes.poll()
                            viewModel.updateRecipeNOTRecommended(recipe.id)
                            qRecommendedRecipes.remove()
                        }
                        viewModel.updateRecipeRecommended(apiRecipe.id)
                        qRecommendedRecipes.add(apiRecipe)
                    }
                }
            }
        }
        recommendedRecipesList?.addAll(qRecommendedRecipes)

        recommendedRecipesList?.let {
            HomeScreen(
                savedRecipesList = savedRecipesList,
                recommendedIngredientsList = recommendedIngredientsList,
                recommendedRecipesList = it,
                onSelectRecipe =
                { recipe ->
                    navController.navigate(LargeRecipeScreenSpec.navigateTo(recipe.id.toString()))
                }
            ) {}
        }

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