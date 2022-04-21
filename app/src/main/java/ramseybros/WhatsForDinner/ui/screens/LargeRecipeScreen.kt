package ramseybros.WhatsForDinner.ui.screens

import android.widget.Toast
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
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
                .weight(0.7f)) {
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
        Spacer(modifier = Modifier.weight(0.1f))
        Box(
            Modifier
                .weight(0.4f)) {
            LargeViewRecipeFooter(recipe = recipe, onSave = onSave, onBack = onBack)
        }
    }

}


@Composable
fun LargeViewRecipeHeader(recipe: Recipe) {
    //probably call small view???
    Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceBetween) {
        Row() {
            Text(
                recipe.title,
                Modifier.weight(1.0f),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            )
            Column() {

                Text(text = stringResource(id = R.string.difficulty, recipe.difficulty.toString()))
                Text(text = stringResource(id = R.string.cook_time, recipe.time.toString()))
            }
        }
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(recipe.imageLink)
                .crossfade(true)
                .build(),
            placeholder = painterResource(R.drawable.pot_image),
            contentDescription = "recipe image",
            contentScale = ContentScale.Fit,
            modifier = Modifier

                .clip(RoundedCornerShape(10))
                .align(Alignment.CenterHorizontally)
                .padding(4.dp)
                .size(400.dp)
                //TODO: Scale image on click

        )
    }
}

@Composable
fun LargeViewRecipeFooter(recipe: Recipe, onSave: (Recipe) -> Unit, onBack: () -> Unit) {
     Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()){
        Button(onClick = { onSave(recipe) }) {
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