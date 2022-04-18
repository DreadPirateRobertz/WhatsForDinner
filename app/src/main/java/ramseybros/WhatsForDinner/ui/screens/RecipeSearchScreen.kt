package ramseybros.WhatsForDinner.ui.screens

import android.util.Log
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkerParameters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import ramseybros.WhatsForDinner.data.Recipe
import ramseybros.WhatsForDinner.ui.navigation.specs.RecipeSearchScreenSpec
import ramseybros.WhatsForDinner.ui.navigation.specs.SmallRecipeScreenSpec
import ramseybros.WhatsForDinner.util.RecipeWorker
import ramseybros.WhatsForDinner.viewmodels.I_WhatsForDinnerViewModel
import ramseybros.WhatsForDinner.viewmodels.WhatsForDinnerViewModel

private const val LOG_TAG = "ramseybros.RecipeSearchScreen.kt"

@Composable
fun RecipeSearchScreen(viewModel: I_WhatsForDinnerViewModel, onClick: () -> Unit) {
    val recipeList = viewModel.getApiRecipeList()
    Button(
        onClick = {
            onClick()
        }
    ) {
        Text(
            textAlign = TextAlign.Center,
            text = "Refresh",
            fontSize = 24.sp
        )
    }
    if(recipeList.value == null) {
        Log.d(LOG_TAG, "recipeList is null")
    } else {
        Log.d(LOG_TAG, "recipeList has size ${recipeList.value!!.size}")
        LazyColumn() {
            items(recipeList.value!!.size) { index ->
                SmallRecipeView(recipe = recipeList.value!![index]) {}
            }
        }
    }

}

@Preview
@Composable
private fun Preview() {
    //RecipeSearchScreen()
}