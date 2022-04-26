package ramseybros.WhatsForDinner.ui.screens

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
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
    saveButtonFlag: Boolean,
    recipe: Recipe, inKitchenList: List<String>,
    ingredientList: List<String>, utensilList: List<String>,
    onSave: (Recipe) -> Unit, onDelete: (Recipe) -> Unit, onBack: () -> Unit
) {
    //TODO: Make this look good in Landscape
    val configuration = LocalConfiguration.current
    var headerWeight = .8f
    when(configuration.orientation){
        Configuration.ORIENTATION_LANDSCAPE ->{
            headerWeight = .4f
        }
    }
    Column(Modifier.fillMaxSize()) {
        Box(
            Modifier
                .fillMaxWidth()
                .weight(headerWeight)) {
            LargeViewRecipeHeader(recipe = recipe)
        }
        var weight = 1f
        if(!saveButtonFlag) weight = 1.2f
        Box(
            Modifier
                .fillMaxWidth()
                .weight(weight)) {
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
            LargeViewRecipeFooter(recipe = recipe, onSave = onSave, onDelete = onDelete, onBack = onBack, saveButtonFlag = saveButtonFlag)
        }
        Spacer(modifier = Modifier.weight(0.3f))
    }

}


@Composable
fun LargeViewRecipeHeader(recipe: Recipe) {

    Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceEvenly) {
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
                Text(text = stringResource(id = R.string.cook_time, recipe.time))
            }
        }
        val configuration = LocalConfiguration.current
        if(configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
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
                    .size(200.dp)
                //TODO: Scale image on click

            )
        }
    }
}

@Composable
fun LargeViewRecipeFooter(recipe: Recipe, onSave: (Recipe) -> Unit, onDelete: (Recipe) -> Unit, onBack: () -> Unit, saveButtonFlag: Boolean) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()){
        if(saveButtonFlag) {
            Button(onClick = { onSave(recipe) }) {
                Text(stringResource(id = ramseybros.WhatsForDinner.R.string.save_recipe_label))
            }
        } else {
            Button(onClick = { onDelete(recipe) }) {
                Text(stringResource(id = ramseybros.WhatsForDinner.R.string.delete_recipe_label))
            }
        }
    }
}


@Preview(showSystemUi = true, showBackground = true)
@Composable
fun PreviewLargeRecipeView() {
    var inKitchenList: List<String> =
        listOf("Garlic", "Paprika", "Ground Black Pepper", "Spoon", "Whisk")
    LargeRecipeView(
        true,
        RecipeGenerator.singleRecipe(),
        inKitchenList = inKitchenList,
        onSave = fun(recipe: Recipe) {},
        onDelete = fun(recipe: Recipe) {},
        onBack = {},
        ingredientList = inKitchenList,
        utensilList = listOf("spoon")
    )
}