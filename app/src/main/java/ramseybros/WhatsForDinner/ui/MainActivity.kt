package ramseybros.WhatsForDinner.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

import ramseybros.WhatsForDinner.ui.screens.LoadingScreen
import ramseybros.WhatsForDinner.ui.theme.WhatsForDinnerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WhatsForDinnerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
//                    HomeScreen(
//                        recentRecipesList = recentRecipesList,
//                        recommendedIngredientsList = recommendedIngredientsList,
//                        recommendedRecipesList = recommendedRecipesList,
//                        onSelectRecipe = {},
//                        onSelectIngredient = {}
//                    )
                }
            }
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