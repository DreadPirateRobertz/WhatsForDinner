package ramseybros.WhatsForDinner.ui.navigation.specs

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import ramseybros.WhatsForDinner.R
import ramseybros.WhatsForDinner.data.Ingredient
import ramseybros.WhatsForDinner.data.Recipe
import ramseybros.WhatsForDinner.ui.screens.LargeRecipeView
import ramseybros.WhatsForDinner.viewmodels.I_WhatsForDinnerViewModel

object LargeRecipeScreenSpec : IScreenSpec {
    override val route: String = "LargeRecipeScreen"
    override val arguments: List<NamedNavArgument> = emptyList()
    override val title: Int = R.string.large_recipe_screen_title
    lateinit var recipe: Recipe
    var fromSearch: Boolean? = null
    var ID: String = ""

    @Composable
    override fun TopAppBarActions(
        navController: NavHostController,
        viewModel: I_WhatsForDinnerViewModel
    ) {
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
            viewModel.savedRecipeListLiveData.observeAsState().value?.forEach {
                if (ID == it.id.toString()) {
                    recipe = it
                }
            }
            viewModel.recommendedRecipeListLiveData.observeAsState().value?.forEach {
                if (ID == it.id.toString()) {
                    recipe = it
                }
            }
        }

        viewModel.setIngredientsToAdd(recipe.ingredientString)

        var saveButtonFlag = true
        viewModel.savedRecipeListLiveData.observeAsState().value?.forEach {
            if(recipe.title == it.title &&
                recipe.time == it.time &&
                recipe.imageLink == it.imageLink) {
                saveButtonFlag = false
                recipe = it
            }
        }

        LargeRecipeView(
            saveButtonFlag = saveButtonFlag,
            recipe = recipe,
            onSave = {
                if(it.recommended) it.recommended = false
                viewModel.addRecipe(it,ingredientList,utensilList)},
            onDelete = {
                viewModel.deleteRecipe(recipe = recipe)
                navController.popBackStack()
            },
            onBack = { navController.navigate(RecipeSearchScreenSpec.navigateTo()) },
            inKitchenList = listOf("Garlic", "Paprika", "Ground Black Pepper", "Spoon", "Whisk"),
            ingredientList = utensilList,
            utensilList = utensilList
        )
        saveButtonFlag = true
    }

}