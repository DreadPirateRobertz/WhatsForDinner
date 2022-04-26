package ramseybros.WhatsForDinner.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import ramseybros.WhatsForDinner.R
import ramseybros.WhatsForDinner.data.Recipe
import ramseybros.WhatsForDinner.data.Ingredient
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.request.ImageRequest
import ramseybros.WhatsForDinner.util.RecipeGenerator

@Composable
private fun SectionHeader(title: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            title,
            fontSize = 25.sp,
        )
        Divider(
            thickness = 3.dp,
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
private fun RecommendedRecipesSection() {
    Column(
    ) {
        SectionHeader(title = stringResource(id = R.string.recommended_recipes_header))
    }
}

@Composable
private fun RecommendedIngredientsSection() {
    Column(
    ) {
        SectionHeader(title = stringResource(id = R.string.recommended_ingredients_header))
    }
}


@Composable
private fun SavedRecipesRow(onSelectRecipe: (Recipe) -> Any, savedRecipesList: List<Recipe>?) {
    //Clicking A recipe will take you to how to make it...
    val configuration = LocalConfiguration.current
    var padding = 16.dp
    var size = 0.dp
    size = when(configuration.orientation){
        Configuration.ORIENTATION_LANDSCAPE -> {
            50.dp
        }
        else ->{
            75.dp
        }
    }
    Box(Modifier.fillMaxWidth()) {
        Card(
            modifier = Modifier
                .padding(start = padding, end = padding, top = 8.dp, bottom = 8.dp)
        ) {
            Column(Modifier.fillMaxSize()) {
                SavedRecipesSection()
                if (savedRecipesList != null) {
                    LazyColumn {
                        items(savedRecipesList) {
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 8.dp, end = 8.dp, top = 8.dp, bottom = 8.dp)
                                    .clickable { onSelectRecipe(it) }
                            ) {
                                Row() {
                                    AsyncImage(
                                        model = ImageRequest.Builder(LocalContext.current)
                                            .data(it.imageLink)
                                            .crossfade(true)
                                            .build(),
                                        placeholder = painterResource(R.drawable.pot_image),
                                        contentDescription = "recipe image",
                                        contentScale = ContentScale.Fit,
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(10))
                                            .align(Alignment.CenterVertically)
                                            .padding(4.dp)
                                            .size(size)
                                    )
                                    Text(
                                        text = it.title,
                                        modifier = Modifier
                                            .align(Alignment.CenterVertically)
                                            .padding(end = 8.dp),
                                        textAlign = TextAlign.Center
                                    )

                                }
                            }
                        }
                    }
                }
            }
        }
    }
}


@Composable
private fun RecommendedIngredientRow(
    onSelectIngredient: (Ingredient) -> Any,
    recentIngredientsList: List<Ingredient>?
) {
//if You click on Ingredient Shows you it and maybe some facts about it
    Box(Modifier.fillMaxWidth()) {
        Card(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp)
        ) {
            Column(Modifier.fillMaxSize()) {
                RecommendedIngredientsSection()
                if (recentIngredientsList != null) {
                    LazyRow {
                        items(recentIngredientsList) {
                            Text(text = it.name, modifier = Modifier
                                .selectable(
                                    selected = true,
                                    onClick = { onSelectIngredient(it) }
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun RecommendedRecipeRow(
    onSelectRecipe: (Recipe) -> Any,
    recommendedRecipesList: List<Recipe>?
) {
    Box(Modifier.fillMaxWidth()) {
        Card(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp),
        ) {
            Column(Modifier.fillMaxSize()) {
                RecommendedRecipesSection()
                if (recommendedRecipesList != null) {
                    LazyRow() {
                        items(recommendedRecipesList) {
                            Text(text = it.title, modifier = Modifier
                                .selectable(
                                    selected = true,
                                    onClick = { onSelectRecipe(it) }
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun HomeScreen(
    savedRecipesList: List<Recipe>?,
    recommendedIngredientsList: List<Ingredient>?,
    recommendedRecipesList: List<Recipe>?,
    onSelectRecipe: (Recipe) -> Any,
    onSelectIngredient: (Ingredient) -> Any
) {
    val configuration = LocalConfiguration.current
    when(configuration.orientation){
        Configuration.ORIENTATION_LANDSCAPE -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
//                    .padding(top = 8.dp, bottom = 60.dp)
                  /*  .verticalScroll(rememberScrollState())*/
                ,

                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Row(
                )
                {

                    Box(
                        Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    ) {
                        RecommendedRecipeRow(
                            onSelectRecipe = onSelectRecipe,
                            recommendedRecipesList = recommendedRecipesList
                        )
                    }
                    Box(
                        Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    ) {
                        SavedRecipesRow(
                            onSelectRecipe = onSelectRecipe,
                            savedRecipesList = savedRecipesList
                        )

                    }

                }
            }
        }
        else ->{
            Column(
                modifier = Modifier
                    .fillMaxSize()
//                    .padding(top = 8.dp, bottom = 8.dp)
                    .verticalScroll(rememberScrollState())
                ,

                horizontalAlignment = Alignment.CenterHorizontally
            )
            {

                Box(
                    Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    RecommendedRecipeRow(
                        onSelectRecipe = onSelectRecipe,
                        recommendedRecipesList = recommendedRecipesList
                    )
                }
                Box(
                    Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    SavedRecipesRow(
                        onSelectRecipe = onSelectRecipe,
                        savedRecipesList = savedRecipesList
                    )
                }
                Spacer(Modifier.weight(.1f))
            }
        }
    }

}


@Preview
@Composable
private fun HomeScreenPreview() {
    val savedRecipesList: List<Recipe> = listOf(RecipeGenerator.placeHolderRecipe())
    val recommendedRecipesList: List<Recipe> = listOf(RecipeGenerator.placeHolderRecipe())
    val recommendedIngredientsList: List<Ingredient> =
        listOf(RecipeGenerator.placeHolderIngredients())
    HomeScreen(
        savedRecipesList = savedRecipesList,
        recommendedIngredientsList = recommendedIngredientsList,
        recommendedRecipesList = recommendedRecipesList,
        onSelectRecipe = {},
        onSelectIngredient = {}
    )
}





