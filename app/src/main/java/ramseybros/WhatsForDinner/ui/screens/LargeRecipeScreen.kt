package ramseybros.WhatsForDinner.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import ramseybros.WhatsForDinner.data.Recipe
import ramseybros.WhatsForDinner.R
import ramseybros.WhatsForDinner.util.RecipeGenerator

@Composable
// difficulty is not taken into account now since I don't know how many we're gonna have
fun LargeRecipeView(
    recipe: Recipe, inKitchenList: List<String>,
    ingredientList: List<String>, utensilList: List<String>,
    onSave: (Recipe) -> Unit, onBack: () -> Unit
) {
    Column(Modifier.fillMaxSize()) {
        Box(
            Modifier
                .fillMaxWidth()
                .weight(0.2f)) {
            LargeViewRecipeHeader(recipe = recipe)
        }
        Box(
            Modifier
                .fillMaxWidth()
                .weight(1.0f)) {
            RecipeInformation(
                recipe = recipe,
                inKitchenList = inKitchenList,
                ingredientList = ingredientList,
                utensilList = utensilList
            )
        }
        Box(
            Modifier
                .fillMaxWidth()
                .weight(0.4f)) {
            LargeViewRecipeFooter(recipe = recipe, onSave = onSave, onBack = onBack)
        }
    }
}

@Composable
fun LargeViewRecipeHeader(recipe: Recipe) {
    //probably call small view???
    Column(Modifier.fillMaxWidth()) {
        Row() {
            Text(
                recipe.title,
                Modifier.weight(1.0f),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            )
//            Column() {
//
//                Text(recipe.difficulty.toString())
//                Text(recipe.time)
//            }
        }
    }
}

@Composable
fun LargeViewRecipeFooter(recipe: Recipe, onSave: (Recipe) -> Unit, onBack: () -> Unit) {
    Row(Modifier.wrapContentHeight()) {
        Button(onClick = { onSave(recipe) }, Modifier.weight(1.0f)) {
            Text(stringResource(id = ramseybros.WhatsForDinner.R.string.save_recipe_label))
        }

    }
}


@Preview(showSystemUi = true, showBackground = true)
@Composable
fun PreviewLargeRecipeView() {
    var inKitchenList: List<String> =
        listOf("Garlic", "Paprika", "Ground Black Pepper", "Spoon", "Whisk")
    LargeRecipeView(
        RecipeGenerator.singleRecipe(),
        inKitchenList = inKitchenList,
        onSave = fun(recipe: Recipe) {},
        onBack = {},
        ingredientList = inKitchenList,
        utensilList = listOf("spoon")
    )
}