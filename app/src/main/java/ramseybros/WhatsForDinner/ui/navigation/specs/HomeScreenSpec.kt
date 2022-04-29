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
        recommendedRecipesList?.forEach {
            Log.d("recommended", "r = ${it.recommended}")
        }
//        savedRecipesList?.forEach {
//            Log.d("recommended", "l = ${it.recommended}")
//        }

        if(recipeList != emptyList<Recipe>()){
            recipeList.forEach { apiRecipe ->
                savedRecipesList?.forEach lamb@{ savedRecipe ->
                    if(apiRecipe.id == savedRecipe.id ) {
                        return@lamb
                    }
                        if(recommendedRecipesList?.size!! > 10) {
                            val recipe = recommendedRecipesList[0]
                            recipe.recommended = false
                            viewModel.updateRecipe(recipe)//Probably not working either
                            recommendedRecipesList.remove(recipe)
                            if(qRecommendedRecipes.contains(recipe)) {//Queue may be worthless at this point but helping with debug
                                qRecommendedRecipes.remove(recipe)
                            }
                        }
                        //Log.d("recommended", "p = ${apiRecipe.recommended}")
                        apiRecipe.recommended = true
                        viewModel.updateRecipe(apiRecipe)//Not Updating
                        qRecommendedRecipes.add(apiRecipe)

                }
            }
        }

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