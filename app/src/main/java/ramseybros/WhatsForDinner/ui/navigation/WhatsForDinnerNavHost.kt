package ramseybros.WhatsForDinner.ui.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ramseybros.WhatsForDinner.ui.navigation.specs.*

@Composable
fun WhatsForDinnerNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination =  IScreenSpec.start
    ) {
        composable(
            route = "home"
        ) { backStackEntry ->
            HomeScreenSpec.content(
                navController = navController,
                backStackEntry = backStackEntry
            )
        }

        composable(
            route = "KitchenSubmenu"
        ) { backStackEntry ->
            KitchenSubmenuScreenSpec.content(
                navController = navController,
                backStackEntry = backStackEntry
            )
        }

        composable(
            route = "LargeRecipeScreen"
        ) { backStackEntry ->
            LargeRecipeScreenSpec.content(
                navController = navController,
                backStackEntry = backStackEntry
            )
        }

        composable(
            route = "load"
        ) { backStackEntry ->
            LoadingScreenSpec.content(
                navController = navController,
                backStackEntry = backStackEntry
            )
        }

        composable(
            route = "MyKitchenScreen"
        ) { backStackEntry ->
            MyKitchenScreenSpec.content(
                navController = navController,
                backStackEntry = backStackEntry
            )
        }

        composable(
            route = "RecipeInformationScreen"
        ) { backStackEntry ->
            RecipeInformationScreenSpec.content(
                navController = navController,
                backStackEntry = backStackEntry
            )
        }

        composable(
            route = "RecipeSearchScreen"
        ) { backStackEntry ->
            RecipeSearchScreenSpec.content(
                navController = navController,
                backStackEntry = backStackEntry
            )
        }

        composable(
            route = "saved"
        ) { backStackEntry ->
            SavedRecipesScreenSpec.content(
                navController = navController,
                backStackEntry = backStackEntry
            )
        }

        composable(
            route = "ShoppingList"
        ) { backStackEntry ->
            ShoppingListScreenSpec.content(
                navController = navController,
                backStackEntry = backStackEntry
            )
        }

        composable(
            route = "SmallRecipeScreen"
        ) { backStackEntry ->
            SmallRecipeScreenSpec.content(
                navController = navController,
                backStackEntry = backStackEntry
            )
        }
    }
}