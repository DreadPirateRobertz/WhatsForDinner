package ramseybros.WhatsForDinner.data

import androidx.compose.ui.res.stringResource
import ramseybros.WhatsForDinner.R
import ramseybros.WhatsForDinner.ui.navigation.specs.*

sealed class NavigationItem(var route: String, var icon: Int, var title: Int){
    object SavedRecipes : NavigationItem(route = SavedRecipesScreenSpec.route, icon = R.drawable.ic_baseline_star_24, title = R.string.nav_title_saved)
    object RecommendedRecipes : NavigationItem(route = RecommendedRecipesScreenSpec.route, icon = R.drawable.ic_baseline_blender_24, title = R.string.nav_title_recommended)
    object Kitchen : NavigationItem(route = MyKitchenScreenSpec.route, icon = R.drawable.ic_baseline_kitchen_24, title = R.string.nav_title_pantry)
    object Home : NavigationItem(route = HomeScreenSpec.route, icon = R.drawable.ic_baseline_home_24, title = R.string.nav_title_home)
    object RecipeSearch : NavigationItem(route = RecipeSearchScreenSpec.route, icon = R.drawable.ic_baseline_search_24, title = R.string.nav_title_search)
    object Blank : NavigationItem("", 0, 0)
}
