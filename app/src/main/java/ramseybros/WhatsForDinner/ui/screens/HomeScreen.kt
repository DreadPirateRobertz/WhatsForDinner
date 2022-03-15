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
import androidx.compose.foundation.lazy.items
import ramseybros.WhatsForDinner.util.RecipeGenerator

@Composable
private fun SectionHeader(title: String){
    Column(){
        Text(
            title,
            fontSize = 24.sp,
        )
        Divider(
            thickness = 2.dp,
            color = colorResource(R.color.light_blue),
        )
    }
}


@Composable
private fun RecentRecipesSection(){
    Column(
    ) {
        SectionHeader(title = stringResource(id = R.string.recent_recipes_header))
    }
}

@Composable
private fun RecommendedRecipesSection(){
    Column(
    ) {
        SectionHeader(title = stringResource(id = R.string.recommended_recipes_header))
    }
}

@Composable
private fun RecommendedIngredientsSection(){
    Column(
    ) {
        SectionHeader(title = stringResource(id = R.string.recommended_ingredients_header))
    }
}


@Composable
private fun RecentRecipeRow(recentRecipe: Recipe, onSelectRecipe: (Recipe) -> Any){
  //Clicking A recipe will take you to how to make it...
    Card(modifier = Modifier
        .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp)
        .clickable { onSelectRecipe(recentRecipe) }
    ) {
        Column {
            RecentRecipesSection()
            Text(fontSize = 16.sp, text = stringResource(R.string.placeholder_recent_recipe))
            Text(fontSize = 16.sp, text = stringResource(R.string.placeholder_recent_recipe))
            Text(fontSize = 16.sp, text = stringResource(R.string.placeholder_recent_recipe))
            }
        }
    }

@Composable
private fun RecommendedIngredientRow(recommendedIngredient: Ingredient, onSelectIngredient: (Ingredient) -> Any){
//if You click on Ingredient Shows you it and maybe some facts about it
    Card(modifier = Modifier
        .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp)
        .clickable { onSelectIngredient(recommendedIngredient) }
    ) {
        Column {
            RecommendedIngredientsSection()
            Text(fontSize = 16.sp, text = stringResource(R.string.placeholder_recommended_ingredient))
            Text(fontSize = 16.sp, text = stringResource(R.string.placeholder_recommended_ingredient))
            Text(fontSize = 16.sp, text = stringResource(R.string.placeholder_recommended_ingredient))
        }
    }
}

@Composable
private fun RecommendedRecipeRow(recommendedRecipe: Recipe, onSelectRecipe: (Recipe) -> Any) {
    Card(modifier = Modifier
        .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp)
        .clickable { onSelectRecipe(recommendedRecipe) }
    ) {
        Column {
            RecommendedRecipesSection()
            Text(fontSize = 16.sp, text = stringResource(R.string.placeholder_recommended_recipe))
            Text(fontSize = 16.sp, text = stringResource(R.string.placeholder_recommended_recipe))
            Text(fontSize = 16.sp, text = stringResource(R.string.placeholder_recommended_recipe))
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
){
    Column(

        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        if(recentRecipesList != null){
            LazyColumn(){
               items(recentRecipesList){
                   RecentRecipeRow(recentRecipe = it, onSelectRecipe = onSelectRecipe)
               }
            }
        }

        if(recommendedIngredientsList != null){
            LazyColumn(){
                items(recommendedIngredientsList){
                    RecommendedIngredientRow(recommendedIngredient = it, onSelectIngredient = onSelectIngredient)
                }
            }
        }

        if(recommendedRecipesList != null){
            LazyColumn(){
                items(recommendedRecipesList){
                   RecommendedRecipeRow(recommendedRecipe = it, onSelectRecipe = onSelectRecipe)
                }
            }
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





