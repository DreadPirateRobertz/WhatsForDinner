package ramseybros.WhatsForDinner.ui.screens

import android.widget.ImageView
import android.widget.ScrollView
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.ui.graphics.graphicsLayer
import ramseybros.WhatsForDinner.util.RecipeGenerator

@Composable
private fun SectionHeader(title: String) {
    Column() {
        Text(
            title,
            fontSize = 25.sp,
        )
        Divider(
            thickness = 3.dp,
            color = colorResource(R.color.light_blue),
        )
    }
}


@Composable
private fun RecentRecipesSection() {
    Column(
    ) {
        SectionHeader(title = stringResource(id = R.string.recent_recipes_header))
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
private fun RecentRecipeRow(onSelectRecipe: (Recipe) -> Any, recentRecipesList: List<Recipe>?) {
    //Clicking A recipe will take you to how to make it...
    Box(Modifier.fillMaxWidth()) {
        Card(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp)
        ) {
            Column(Modifier.fillMaxSize()) {
                RecentRecipesSection()
                if (recentRecipesList != null) {
                    LazyRow {
                        items(recentRecipesList) {
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
    recentRecipesList: List<Recipe>?,
    recommendedIngredientsList: List<Ingredient>?,
    recommendedRecipesList: List<Recipe>?,
    onSelectRecipe: (Recipe) -> Any,
    onSelectIngredient: (Ingredient) -> Any
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 8.dp, bottom = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        Box(
            Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            RecentRecipeRow(
                onSelectRecipe = onSelectRecipe,
                recentRecipesList = recentRecipesList
            )
        }
        Box(
            Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            RecommendedIngredientRow(
                onSelectIngredient = onSelectIngredient,
                recentIngredientsList = recommendedIngredientsList
            )
        }
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
    }
}


@Preview
@Composable
private fun HomeScreenPreview() {
    val recentRecipesList: List<Recipe> = listOf(RecipeGenerator.placeHolderRecipe())
    val recommendedRecipesList: List<Recipe> = listOf(RecipeGenerator.placeHolderRecipe())
    val recommendedIngredientsList: List<Ingredient> =
        listOf(RecipeGenerator.placeHolderIngredients())
    HomeScreen(
        recentRecipesList = recentRecipesList,
        recommendedIngredientsList = recommendedIngredientsList,
        recommendedRecipesList = recommendedRecipesList,
        onSelectRecipe = {},
        onSelectIngredient = {}
    )
}





