package ramseybros.WhatsForDinner.ui.navigation.specs

import android.util.Log
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.core.content.PackageManagerCompat.LOG_TAG
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import ramseybros.WhatsForDinner.R
import ramseybros.WhatsForDinner.data.Ingredient
import ramseybros.WhatsForDinner.data.Recipe
import ramseybros.WhatsForDinner.ui.screens.LargeRecipeView
import ramseybros.WhatsForDinner.ui.theme.colorDarkError
import ramseybros.WhatsForDinner.util.RecipeGenerator
import ramseybros.WhatsForDinner.viewmodels.I_WhatsForDinnerViewModel
import java.util.*

object LargeRecipeScreenSpec : IScreenSpec {
    override val route: String = "LargeRecipeScreen"
    override val arguments: List<NamedNavArgument> = emptyList()
    override val title: Int = R.string.large_recipe_screen_title
    lateinit var recipe: Recipe
    var fromSearch: Boolean? = null
    var ID: String = ""

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
        if(args[0] == "search"){
            fromSearch = true
        }
        else{
           ID = args[0].toString()
            fromSearch = false
        }
        return route
    }

    @Composable
    override fun Content(
        viewModel: I_WhatsForDinnerViewModel,
        navController: NavHostController,
        backStackEntry: NavBackStackEntry
    ) {
        val ingredientList = emptyList<Ingredient>()
        val utensilList = emptyList<String>()
        Log.d("ramseybros", ID)
        if(fromSearch == true) recipe = viewModel.getApiRecipeLiveData().value!!

        else {
            viewModel.recipeListLiveData.observeAsState().value?.forEach {
                if (ID == it.id.toString()) {
                    recipe = it
                }
            }
        }
        var saveButtonFlag = true
        viewModel.recipeListLiveData.value?.forEach {
            if(recipe == it) saveButtonFlag = false
        }

        LargeRecipeView(
            saveButtonFlag = saveButtonFlag,
            recipe = recipe,
            onSave = { viewModel.addRecipe(recipe,ingredientList,utensilList)},
            onBack = { navController.navigate(RecipeSearchScreenSpec.navigateTo()) },
            inKitchenList = listOf("Garlic", "Paprika", "Ground Black Pepper", "Spoon", "Whisk"),
            ingredientList = utensilList,
            utensilList = utensilList
        )
        saveButtonFlag = true
    }

}