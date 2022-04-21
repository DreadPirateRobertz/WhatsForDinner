package ramseybros.WhatsForDinner.data

import ramseybros.WhatsForDinner.R

sealed class NavigationItem(var route: String, var icon: Int, var title: String){
    object SavedRecipes : NavigationItem(route = "saved", icon = R.drawable.ic_baseline_star_24, title = "Saved")
    object Kitchen : NavigationItem(route = "MyKitchenScreen", icon = R.drawable.ic_baseline_kitchen_24, title = "Kitchen")
    object Home : NavigationItem(route = "home", icon = R.drawable.ic_baseline_home_24, title = "Home")
    object RecipeSearch : NavigationItem(route = "RecipeSearchScreen", icon = R.drawable.ic_baseline_search_24, title = "Search")
    object Blank : NavigationItem("", 0, "")
}
