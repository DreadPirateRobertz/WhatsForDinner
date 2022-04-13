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
import ramseybros.WhatsForDinner.ui.screens.LargeRecipeView
import ramseybros.WhatsForDinner.ui.screens.RecipeInformation
import ramseybros.WhatsForDinner.ui.theme.colorDarkError
import ramseybros.WhatsForDinner.util.RecipeGenerator
import ramseybros.WhatsForDinner.viewmodels.I_WhatsForDinnerViewModel

object RecipeInformationScreenSpec : IScreenSpec {
    override val route: String = "RecipeInformationScreen"
    override val arguments: List<NamedNavArgument> = emptyList()
    override val title: Int = R.string.recipe_info_screen_title




    @Composable
    override fun TopAppBarActions(navController: NavHostController) {
        var color: Color = colorDarkError
        if(!isSystemInDarkTheme()) color = colorResource(R.color.white)
        else color = colorResource(id = R.color.black)
        IconButton(
            onClick = { navController.navigate(HomeScreenSpec.navigateTo()) }
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
        val recipe = RecipeGenerator.singleRecipe()
        val ingredientList = RecipeGenerator.recipeIngredientList()
        val utensilList = RecipeGenerator.recipeUtensilList()
        RecipeInformation(recipe = recipe,
            inKitchenList = listOf("Garlic", "Paprika", "Ground Black Pepper", "Spoon", "Whisk"),
            ingredientList = ingredientList,
            utensilList = utensilList)
    }
}