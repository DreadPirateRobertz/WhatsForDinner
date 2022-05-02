package ramseybros.WhatsForDinner.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ramseybros.WhatsForDinner.R
import ramseybros.WhatsForDinner.data.Recipe
import ramseybros.WhatsForDinner.viewmodels.I_WhatsForDinnerViewModel

private const val LOG_TAG = "ramseybros.RecipeSearchScreen.kt"


@Composable
fun RecipeSearchScreen(
    apiRecipeList: SnapshotStateList<Recipe>,
    onRequestRecipe: (Recipe) -> Unit
) {
    Column(Modifier.fillMaxSize()) {
        Box(
            Modifier
                .fillMaxSize()
                ) {
                if(apiRecipeList == emptyList<Recipe>()) {
                    Log.d(LOG_TAG, "apiRecipeList is empty")
                } else {
                    Log.d(LOG_TAG, "apiRecipeList has size ${apiRecipeList.size}")
                    LazyColumn(state = rememberLazyListState()){
                        items(apiRecipeList) {
                            SmallRecipeView(recipe = it) {onRequestRecipe(it)}
                        }
                    }
                }
        }
    }
}


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


@Preview
@Composable
private fun Preview() {
    //RecipeSearchScreen(WhatsForDinnerViewModel(), {},{})
}