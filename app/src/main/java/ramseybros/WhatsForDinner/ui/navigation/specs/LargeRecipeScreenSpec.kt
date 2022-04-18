package ramseybros.WhatsForDinner.ui.navigation.specs

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import ramseybros.WhatsForDinner.R
import ramseybros.WhatsForDinner.data.Recipe
import ramseybros.WhatsForDinner.ui.screens.LargeRecipeView
import ramseybros.WhatsForDinner.ui.theme.colorDarkError
import ramseybros.WhatsForDinner.util.RecipeGenerator
import ramseybros.WhatsForDinner.viewmodels.I_WhatsForDinnerViewModel

object LargeRecipeScreenSpec : IScreenSpec {
    override val route: String = "LargeRecipeScreen"
    override val arguments: List<NamedNavArgument> = emptyList()
    override val title: Int = R.string.large_recipe_screen_title
    lateinit var recipe: Recipe

    @Composable
    override fun TopAppBarActions(navController: NavHostController) {
        var color: Color = colorDarkError
        if (!isSystemInDarkTheme()) color = colorResource(R.color.white)
        else color = colorResource(id = R.color.black)
        IconButton(
            onClick = { navController.navigate(HomeScreenSpec.navigateTo()){
                popUpTo(HomeScreenSpec.route){
                    inclusive = true
                }
            } }
        ) {
            Icon(
                imageVector = Icons.Filled.Home,
                contentDescription = null,
                tint = color
            )
        }
    }


    override fun navigateTo(vararg args: String?): String {
        return route
    }

    @Composable
    override fun Content(
        viewModel: I_WhatsForDinnerViewModel,
        navController: NavHostController,
        backStackEntry: NavBackStackEntry
    ) {
        val ingredientList = RecipeGenerator.recipeIngredientList()
        val utensilList = RecipeGenerator.recipeUtensilList()
        recipe = viewModel.getApiRecipeLiveData().value!!
        LargeRecipeView(
            recipe = recipe,
            onSave = {},
            onBack = { navController.navigate(RecipeSearchScreenSpec.navigateTo()) },
            inKitchenList = listOf("Garlic", "Paprika", "Ground Black Pepper", "Spoon", "Whisk"),
            ingredientList = ingredientList,
            utensilList = utensilList
        )
    }

}