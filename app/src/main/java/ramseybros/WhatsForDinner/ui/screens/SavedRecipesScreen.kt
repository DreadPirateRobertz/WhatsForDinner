package ramseybros.WhatsForDinner.ui.screens

import android.content.res.Configuration
import android.graphics.Paint
import android.widget.ImageView
import android.widget.ScrollView
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.*
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ramseybros.WhatsForDinner.R
import ramseybros.WhatsForDinner.data.Recipe
import ramseybros.WhatsForDinner.data.Ingredient
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.request.ImageRequest
import ramseybros.WhatsForDinner.util.RecipeGenerator


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
private fun SavedRecipesSection() {
    Column(

    ) {
        SectionHeader(title = stringResource(id = R.string.saved_recipes_header))
    }
}

@Composable
private fun SavedRecipeRow(savedRecipe: Recipe, onSelectRecipe: (Recipe) -> Any) {
    //Clicking A recipe will take you to how to make it...
    val size = 100.dp
//    val configuration = LocalConfiguration.current
//    size = when(configuration.orientation) {
//        Configuration.ORIENTATION_LANDSCAPE -> {
//            150.dp
//        }
//        else -> {
//            100.dp
//        }
//    }

        Box(modifier = Modifier.fillMaxWidth()) {
        Card(modifier = Modifier
            .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp)
            .clickable { onSelectRecipe(savedRecipe) }
        ) {
            Row (Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceAround){
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(savedRecipe.imageLink)
                        .crossfade(true)
                        .build(),
                    placeholder = painterResource(R.drawable.pot_image),
                    contentDescription = "recipe image",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .weight(1.5f)
                        .clip(RoundedCornerShape(10))
                        .align(Alignment.CenterVertically)
                        .padding(4.dp)
                        .size(size)
                )
                Text(fontSize = 16.sp, text = savedRecipe.title, modifier = Modifier.padding(4.dp).weight(1f), textAlign = TextAlign.Center)
            }
        }
    }
}

@Composable
fun SavedRecipesScreen(
    savedRecipesList: List<Recipe>?,
    onSelectRecipe: (Recipe) -> Any,
) {

    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
//        SavedRecipesSection()
        Box(Modifier.fillMaxSize()) {
            if (savedRecipesList != null) {
                LazyColumn() {
                    items(savedRecipesList) {
                        SavedRecipeRow(savedRecipe = it, onSelectRecipe = onSelectRecipe)
                    }
                }
            }
        }
        Spacer(Modifier.weight(0.2f))
    }
}


@Preview
@Composable
private fun SavedRecipesScreenPreview() {
    val savedRecipesList = mutableListOf(RecipeGenerator.placeHolderRecipe())
    savedRecipesList.add(RecipeGenerator.placeHolderRecipe())
    savedRecipesList.add(RecipeGenerator.placeHolderRecipe())
    savedRecipesList.add(RecipeGenerator.placeHolderRecipe())
    savedRecipesList.add(RecipeGenerator.placeHolderRecipe())
    savedRecipesList.add(RecipeGenerator.placeHolderRecipe())
    savedRecipesList.add(RecipeGenerator.placeHolderRecipe())
    SavedRecipesScreen(
        savedRecipesList = savedRecipesList,
        onSelectRecipe = {},
    )
}
