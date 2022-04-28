package ramseybros.WhatsForDinner.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
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
import java.util.*


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
    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?){
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 102 && resultCode == Activity.RESULT_OK){
            val result = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            viewModel.onSearchTextChanged(result?.get(0).toString())
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

@Composable
private fun AskSpeechInput(context: Context){
    if(if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            !SpeechRecognizer.isOnDeviceRecognitionAvailable(context)// Works if API is 31 and >
        } else {
            !SpeechRecognizer.isRecognitionAvailable(context)  //Remote
        }
    ){
        Toast.makeText(context, "Speech Unavailable", Toast.LENGTH_SHORT).show()
    }
    else{
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "This will allow this app to recognize your speech")

        ActivityCompat.startActivityForResult(context as Activity, intent, 102, null)
    }
}


