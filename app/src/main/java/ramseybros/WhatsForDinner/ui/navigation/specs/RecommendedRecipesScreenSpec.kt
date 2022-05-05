package ramseybros.WhatsForDinner.ui.navigation.specs

import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import ramseybros.WhatsForDinner.R
import ramseybros.WhatsForDinner.ui.screens.RecommendedRecipesScreen
import ramseybros.WhatsForDinner.viewmodels.I_WhatsForDinnerViewModel

object RecommendedRecipesScreenSpec: IScreenSpec {
    override val route: String
        get() = "RecommendedRecipesScreen"
    override val arguments: List<NamedNavArgument>
        get() = TODO("Not yet implemented")
    override val title: Int
        get() = R.string.recommended_recipes_header

    @Composable
    override fun Content(
        viewModel: I_WhatsForDinnerViewModel,
        navController: NavHostController,
        backStackEntry: NavBackStackEntry
    ) {
        val recommndedRecipesList = viewModel.recommendedRecipeListLiveData.observeAsState().value
        RecommendedRecipesScreen(
            recommendedRecipesList = recommndedRecipesList,
            onSelectRecipe = {recipe->
                viewModel.setRecipeIdLiveData(recipe.id)
                navController.navigate(LargeRecipeScreenSpec.navigateTo(recipe.id.toString()))
            },
            viewModel = viewModel)
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