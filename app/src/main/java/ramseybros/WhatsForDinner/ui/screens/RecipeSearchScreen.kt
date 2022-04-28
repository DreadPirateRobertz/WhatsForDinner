package ramseybros.WhatsForDinner.ui.screens

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.startActivityForResult
import ramseybros.WhatsForDinner.R
import ramseybros.WhatsForDinner.data.Recipe
import ramseybros.WhatsForDinner.viewmodels.I_WhatsForDinnerViewModel
import ramseybros.WhatsForDinner.viewmodels.WhatsForDinnerViewModel
import java.util.*

private const val LOG_TAG = "ramseybros.RecipeSearchScreen.kt"







@Composable
private fun SectionHeader(title: String) {
    Column(        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            textAlign = TextAlign.Center,
            text = title,
            fontSize = 24.sp,
        )
        Divider(
            thickness = 2.dp,
            color = colorResource(R.color.teal_200),
        )
    }
}
@Composable
fun RecipeSearchScreen(
    viewModel: I_WhatsForDinnerViewModel,
    onClick: () -> Unit,
    onRequestRecipe: (Recipe) -> Unit
) {
    val recipeList = viewModel.getApiRecipeList()
    Column(Modifier.fillMaxSize()) {
        val context = LocalContext.current
        Button(onClick = { AskSpeechInput(context) }) {
        }

        Box(
            Modifier
                .fillMaxSize()
                ) {
            Text(text = viewModel.talk)
                if(recipeList == emptyList<Recipe>()) {
                    Log.d(LOG_TAG, "recipeList is empty")
                } else {
                    Log.d(LOG_TAG, "recipeList has size ${recipeList.size}")
                    LazyColumn(){
                        items(recipeList) {
                            SmallRecipeView(recipe = it) {onRequestRecipe(it)}
                        }
                    }
                }
        }
    }
}

fun AskSpeechInput(context: Context){
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


@Preview
@Composable
private fun Preview() {
    //RecipeSearchScreen(WhatsForDinnerViewModel(), {},{})
}