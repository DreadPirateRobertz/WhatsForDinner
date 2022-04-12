package ramseybros.WhatsForDinner.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import ramseybros.WhatsForDinner.ui.navigation.WhatsForDinnerNavHost
import ramseybros.WhatsForDinner.ui.navigation.WhatsForDinnerTopBar


import ramseybros.WhatsForDinner.ui.screens.LoadingScreen
import ramseybros.WhatsForDinner.ui.screens.MyKitchen
import ramseybros.WhatsForDinner.ui.theme.WhatsForDinnerTheme
import ramseybros.WhatsForDinner.viewmodels.I_WhatsForDinnerViewModel
import ramseybros.WhatsForDinner.viewmodels.WhatsForDinnerFactory
import ramseybros.WhatsForDinner.viewmodels.WhatsForDinnerViewModel
import ramseybros.WhatsForDinner.ui.navigation.specs.IScreenSpec
import ramseybros.WhatsForDinner.ui.navigation.specs.IScreenSpec.Companion.BottomBar
import ramseybros.WhatsForDinner.ui.navigation.specs.IScreenSpec.Companion.FloatingButton
import ramseybros.WhatsForDinner.ui.navigation.specs.LoadingScreenSpec.route

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
private fun MainActivityContent(model: I_WhatsForDinnerViewModel){

    WhatsForDinnerTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            val navController = rememberNavController()
            Scaffold(

                floatingActionButton = { if(currentRoute(navController = navController) != "load"){
                    FloatingButton(navController = navController)
                }},
//                isFloatingActionButtonDocked = true,
                topBar = { if(currentRoute(navController = navController) != "load"){
                    WhatsForDinnerTopBar(navController = navController)
                } },
                content ={ WhatsForDinnerNavHost(navController = navController, viewModel = model) },
                bottomBar = { if(currentRoute(navController = navController) != "load"){
                    BottomBar(navController = navController,)
                }}
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
fun currentRoute(navController: NavHostController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}