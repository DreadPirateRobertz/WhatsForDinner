package ramseybros.WhatsForDinner.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import ramseybros.WhatsForDinner.ui.navigation.WhatsForDinnerNavHost
import ramseybros.WhatsForDinner.ui.navigation.WhatsForDinnerTopBar
import ramseybros.WhatsForDinner.ui.navigation.specs.IScreenSpec.Companion.BottomBar
import ramseybros.WhatsForDinner.ui.navigation.specs.IScreenSpec.Companion.FloatingButton
//import ramseybros.WhatsForDinner.ui.navigation.specs.IScreenSpec.Companion.HorizontalSwiper
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


@OptIn(ExperimentalPagerApi::class)
@Composable
private fun MainActivityContent(model: I_WhatsForDinnerViewModel) {

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
                    if (checkRoute(navController = navController) != 1)
                        if (checkRoute(navController = navController) != 2) {
                            FloatingButton(navController = navController)
                        }
                },
//                isFloatingActionButtonDocked = true,
                topBar = {
                    if (checkRoute(navController = navController) != 2) {
                        WhatsForDinnerTopBar(navController = navController)
                    }
                },
                content = {
//                        HorizontalSwiper()
                        WhatsForDinnerNavHost(navController = navController, viewModel = model)
                },
                bottomBar = {
                    if (checkRoute(navController = navController) != 2) {
                        BottomBar(navController = navController)

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
    else if (x == null) return 3
    else return 4

}