package ramseybros.WhatsForDinner.ui.screens

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ramseybros.WhatsForDinner.data.Recipe
import ramseybros.WhatsForDinner.data.Ingredient
import ramseybros.WhatsForDinner.util.RecipeGenerator
import ramseybros.WhatsForDinner.R

@Composable
fun ListItems(
    recipe: Recipe,
    inKitchenList: List<String>,
    ingredientList: List<String>,
    utensilList: List<String>
) {
    Row(Modifier.border(BorderStroke(2.dp, Color.Black), shape = RoundedCornerShape(5))) {
        LazyColumn(
            Modifier
                .weight(1.0f)
                .padding(4.dp)
        ) {
            items(ingredientList.size) {
                if (ingredientList[it] in inKitchenList) {
                    Text(ingredientList[it])
                } else {
                    Text(ingredientList[it], color = Color.Red)
                }
            }
        }
        LazyColumn(
            Modifier
                .weight(1.0f)
                .padding(4.dp)
        ) {
            items(utensilList.size) {
                if (utensilList[it] in inKitchenList) {
                    Text(utensilList[it])
                } else {
                    Text(utensilList[it], color = Color.Red)
                }
            }
        }
    }
}

@Composable
fun RecipeText(recipeText: String) {
    Text(recipeText, Modifier.padding(4.dp), textAlign = TextAlign.Center)
}

@Composable
fun IngredientText(ingredientString: String) {
    Log.d("RecipeInformationScreen.kt", "ingredientString: $ingredientString")
    val list = ingredientString.split(",")

    Column(Modifier.fillMaxSize(), Arrangement.Center, Alignment.CenterHorizontally) {
        list.forEachIndexed { index, string ->
            if (index == 0) {
            } else Text(text = "- ${list[index]}", textAlign = TextAlign.Center)
        }
    }
}

@Composable
fun RecipeInformation(
    recipe: Recipe,
    inKitchenList: List<String>,
    ingredientList: List<String>,
    utensilList: List<String>
) {
    val configuration = LocalConfiguration.current

    when (configuration.orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> {
            Row(
                Modifier
                    .fillMaxSize()
                    .padding(4.dp)
//
            ) {

                Column(
                    Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState())
                ) {
                    IngredientText(recipe.ingredientString)
                }
                Divider(
                    color = colorResource(id = R.color.teal_200), modifier = Modifier
                        .fillMaxHeight()
                        .width(1.dp)
                )
                Column(
                    Modifier
                        .weight(1.5f)
                        .verticalScroll(rememberScrollState())
                ) {
                    RecipeText(recipeText = recipe.recipeText)
                }
            }
        }
        else -> {
            Column(
                Modifier
                    .padding(4.dp)
            ) {
                Column(
                    Modifier
                        .verticalScroll(rememberScrollState())
                        .weight(1f)) {
                    IngredientText(recipe.ingredientString)
                }
                Divider(color = colorResource(id = R.color.teal_200), thickness = 1.dp)
                Column(
                    Modifier
                        .verticalScroll(rememberScrollState())
                        .weight(1.5f)) {
                    RecipeText(recipeText = recipe.recipeText)
                }
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun previewRecipe() {
    var inKitchenList: List<String> =
        listOf("Garlic", "Paprika", "Ground Black Pepper", "Spoon", "Whisk")
    var ingredientList: List<String> =
        listOf("Garlic", "Paprika", "Ground Black Pepper", "Spoon", "Whisk")
    RecipeInformation(
        RecipeGenerator.singleRecipe(),
        inKitchenList = inKitchenList,
        ingredientList = ingredientList,
        utensilList = listOf("spoon")
    )
}