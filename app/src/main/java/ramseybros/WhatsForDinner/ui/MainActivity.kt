package ramseybros.WhatsForDinner.ui

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import ramseybros.WhatsForDinner.data.NavigationItem
import ramseybros.WhatsForDinner.ui.navigation.WhatsForDinnerBottomBar
import ramseybros.WhatsForDinner.ui.navigation.WhatsForDinnerFAB
import ramseybros.WhatsForDinner.ui.navigation.WhatsForDinnerNavHost
import ramseybros.WhatsForDinner.ui.navigation.WhatsForDinnerTopBar
import ramseybros.WhatsForDinner.ui.theme.WhatsForDinnerTheme
import ramseybros.WhatsForDinner.viewmodels.I_WhatsForDinnerViewModel
import ramseybros.WhatsForDinner.viewmodels.WhatsForDinnerFactory
import ramseybros.WhatsForDinner.viewmodels.WhatsForDinnerViewModel


class MainActivity : ComponentActivity() {
    private lateinit var viewModel: WhatsForDinnerViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = WhatsForDinnerFactory(this)
        viewModel = ViewModelProvider(this, factory).get(factory.getViewModelClass())
        setContent {
            MainActivityContent(model = viewModel)
        }
    }
}

@Composable
private fun MainActivityContent(model: I_WhatsForDinnerViewModel) {
    val configuration = LocalConfiguration.current

    WhatsForDinnerTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            val navController = rememberNavController()

            Scaffold(
                //Could not do an && on complex function call so resolved with two conditionals
                floatingActionButton = {
//                    if (checkRoute(navController = navController) != 1)
                    if (checkRoute(navController = navController) != 2) {
                        WhatsForDinnerFAB(navController = navController)
                    }
                },
                isFloatingActionButtonDocked = true,
                floatingActionButtonPosition = FabPosition.Center,
//
                topBar = {
                    if ((checkRoute(navController = navController) != 2 && configuration.orientation == Configuration.ORIENTATION_PORTRAIT)) {
                        WhatsForDinnerTopBar(navController = navController, viewModel = model)
                    }
                    //Tried to combine these 2 conditions with an || and was getting a compiler bug
                    if (checkRoute(navController = navController) == 5 && configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
                    ) {
                        WhatsForDinnerTopBar(navController = navController, viewModel = model)
                    }
                },
                content = {
                    WhatsForDinnerNavHost(navController = navController, viewModel = model, it)
                },
                bottomBar = {
                    if (checkRoute(navController = navController) != 2) {
                        WhatsForDinnerBottomBar(navController = navController)
                    }
                }

            )
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    WhatsForDinnerTheme {
        Greeting("Android")
    }
}

@Composable
fun checkRoute(navController: NavHostController): Int {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val x = navBackStackEntry?.destination?.route
    if (x == "ShoppingList") return 1
    else if (x == "splash") return 2
    else if (x == "LargeRecipeScreen") return 3
    else if (x == NavigationItem.SavedRecipes.route) return 4
    else if (x == NavigationItem.RecipeSearch.route) return 5
    else if (x == null) return 6
    else return 7

}