package ramseybros.WhatsForDinner.ui.navigation.specs

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.navigation.*
import ramseybros.WhatsForDinner.ui.screens.HomeScreen
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

    @SuppressLint("UnrememberedMutableState")
    @Composable
    override fun Content(
        viewModel: I_WhatsForDinnerViewModel,
        navController: NavHostController,
        backStackEntry: NavBackStackEntry
    ) {

        val savedRecipesList = viewModel.savedRecipesListLiveData.observeAsState(mutableStateListOf())
        val recommendedRecipesList = viewModel.recommendedRecipesListLiveData.observeAsState(mutableStateListOf())



        HomeScreen(
            viewModel = viewModel,
            savedRecipesList = savedRecipesList,
            recommendedIngredientsList = emptyList(),
            recommendedRecipesList = recommendedRecipesList,
            onSelectIngredient = {},
            onSelectRecipe =
            { recipe ->
                navController.navigate(LargeRecipeScreenSpec.navigateTo(recipe.id.toString()))
            }
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