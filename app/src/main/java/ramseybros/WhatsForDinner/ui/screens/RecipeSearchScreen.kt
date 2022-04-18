package ramseybros.WhatsForDinner.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ramseybros.WhatsForDinner.R
import ramseybros.WhatsForDinner.data.Recipe
import ramseybros.WhatsForDinner.viewmodels.I_WhatsForDinnerViewModel
import ramseybros.WhatsForDinner.viewmodels.WhatsForDinnerViewModel

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
        SectionHeader(title = stringResource(id = R.string.recipe_search_screen_title))
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

        Spacer(Modifier.weight(.1f))
        Box(
            Modifier
                .fillMaxWidth()
                .weight(1.0f)) {

                if(recipeList == null) {
                    Log.d(LOG_TAG, "recipeList is null")
                } else {
                    Log.d(LOG_TAG, "recipeList has size ${recipeList.size}")
                    var recipe: Recipe
                    LazyColumn(){
                        items(recipeList.size) { index ->
                            recipe = recipeList[index]
                            SmallRecipeView(recipe = recipe) {onRequestRecipe(recipe)}
                        }
                    }
                }

        }
        Spacer(Modifier.weight(.25f))
    }
}



@Preview
@Composable
private fun Preview() {
    //RecipeSearchScreen(WhatsForDinnerViewModel(), {},{})
}