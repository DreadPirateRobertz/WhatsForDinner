package ramseybros.WhatsForDinner.ui.screens

import android.content.res.Configuration
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
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
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import kotlinx.coroutines.launch
import ramseybros.WhatsForDinner.util.RecipeGenerator
import ramseybros.WhatsForDinner.viewmodels.I_WhatsForDinnerViewModel
import java.util.*

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



@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun SavedRecipesRow(onSelectRecipe: (Recipe) -> Any, savedRecipesList: State<SnapshotStateList<Recipe>>, viewModel: I_WhatsForDinnerViewModel) {

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
                if (savedRecipesList.value.isNotEmpty()) {
                    val scope = rememberCoroutineScope()
                   LazyColumn(state = rememberLazyListState()) {
                        items(savedRecipesList.value,key = {recipe->recipe.id}) { recipe->
                            val dismissState = rememberDismissState(
                                confirmStateChange = {
                                    when (it){
                                        DismissValue.Default ->{}
                                        DismissValue.DismissedToEnd ->{}
                                        DismissValue.DismissedToStart ->{
                                            savedRecipesList.value.remove(recipe)
                                            scope.launch {
                                                viewModel.deleteRecipe(recipe)
                                            }
                                        }
                                    }
                                    true
                                }
                            )

                            SwipeToDismiss(
                                state = dismissState,
                                directions = setOf( DismissDirection.EndToStart),
                                dismissThresholds = {FractionalThreshold(.3f)},
                                background ={

                                    val direction = dismissState.dismissDirection ?: return@SwipeToDismiss
                                    val color by animateColorAsState(
                                        targetValue = when(dismissState.targetValue){
                                            DismissValue.Default -> Color.Transparent
                                            DismissValue.DismissedToEnd -> Color.Transparent
                                            DismissValue.DismissedToStart -> Color.Red
                                        }
                                    )
                                    val scale by animateFloatAsState(targetValue = if(dismissState.targetValue == DismissValue.Default) 0.8f else 1.2f)
                                    val alignment = Alignment.CenterEnd

                                    Box(

                                        modifier = Modifier
                                            .fillMaxSize()
                                            .background(color)
                                            .padding(start = 12.dp, end = 12.dp),
                                        contentAlignment = alignment
                                    )
                                    {
                                        Icon(painter = painterResource(id = R.drawable.ic_baseline_delete_sweep_24), tint = colorResource(R.color.teal_200),contentDescription = null, modifier = Modifier.scale(scale))
                                    }

                                }, dismissContent = {
                                    DismissContent(onSelectRecipe = onSelectRecipe, recipe = recipe , size = size, dismissState = dismissState)

                                }

                            )

                        }
                    }
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun DismissContent(
    onSelectRecipe: (Recipe) -> Any,
    recipe: Recipe,
    size: Dp,
    dismissState: DismissState
)  {
    val elevation = animateDpAsState(targetValue = if(dismissState.dismissDirection != null) 4.dp else 0.dp).value
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp, top = 8.dp, bottom = 8.dp)
            .clickable { onSelectRecipe(recipe) },
            elevation = elevation
    ) {
        Row() {
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
                    .align(Alignment.CenterVertically)
                    .padding(4.dp)
                    .size(size)
            )
            Text(
                text = recipe.title,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(end = 8.dp),
                textAlign = TextAlign.Center
            )
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

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun RecommendedRecipeRow(
    onSelectRecipe: (Recipe) -> Any,
    recommendedRecipesList: MutableList<Recipe>,
    viewModel: I_WhatsForDinnerViewModel
) {
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
                RecommendedRecipesSection()
                if (recommendedRecipesList != emptyList<Recipe>()) {
                    val scope = rememberCoroutineScope()
                    LazyColumn(state = rememberLazyListState()) {
                        items(recommendedRecipesList,{recipe->recipe.id}) { recipe->
                            val dismissState = rememberDismissState(
                                confirmStateChange = {
                                    when (it){
                                        DismissValue.Default ->{}
                                        DismissValue.DismissedToEnd ->{
                                            recommendedRecipesList.remove(recipe)
                                            recipe.recommended = false
                                            scope.launch{
                                                viewModel.addRecipe(recipe, emptyList(), emptyList())
                                            }

                                        }
                                        DismissValue.DismissedToStart ->{
                                            recommendedRecipesList.remove(recipe)
                                            scope.launch {
                                                viewModel.deleteRecipe(recipe)
                                            }
                                        }
                                    }
                                    true
                                }
                            )

                            SwipeToDismiss(
                                state = dismissState,
                                directions = setOf( DismissDirection.EndToStart, DismissDirection.StartToEnd),
                                dismissThresholds = {FractionalThreshold(.3f)},
                                background ={

                                    val direction = dismissState.dismissDirection ?: return@SwipeToDismiss
                                    val color by animateColorAsState(
                                        targetValue = when(dismissState.targetValue){
                                            DismissValue.Default -> Color.Transparent
                                            DismissValue.DismissedToEnd -> Color.Green
                                            DismissValue.DismissedToStart -> Color.Red
                                        }
                                    )
                                    val icon = when(direction){
                                        DismissDirection.StartToEnd -> painterResource(R.drawable.ic_baseline_star_24)
                                        DismissDirection.EndToStart -> painterResource(R.drawable.ic_baseline_delete_sweep_24)
                                    }
                                    val scale by animateFloatAsState(targetValue = if(dismissState.targetValue == DismissValue.Default) 0.8f else 1.2f)
                                    val alignment = when(direction){
                                        DismissDirection.StartToEnd -> Alignment.CenterStart
                                        DismissDirection.EndToStart -> Alignment.CenterEnd
                                    }

                                    Box(

                                        modifier = Modifier
                                            .fillMaxSize()
                                            .background(color)
                                            .padding(start = 12.dp, end = 12.dp),
                                        contentAlignment = alignment
                                    )
                                    {
                                        Icon(painter = icon, contentDescription = null, modifier = Modifier.scale(scale),tint = colorResource(R.color.teal_200) )
                                    }

                                }, dismissContent = {
                                    DismissContent(onSelectRecipe = onSelectRecipe, recipe = recipe , size = size, dismissState = dismissState)

                                }
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
    savedRecipesList: State<SnapshotStateList<Recipe>>,
    recommendedIngredientsList: List<Ingredient>?,
    recommendedRecipesList: MutableList<Recipe>,
    onSelectRecipe: (Recipe) -> Any,
    onSelectIngredient: (Ingredient) -> Any,
    viewModel: I_WhatsForDinnerViewModel
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
                            recommendedRecipesList = recommendedRecipesList,
                            viewModel = viewModel
                        )
                    }
                    Box(
                        Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    ) {
                        SavedRecipesRow(
                            onSelectRecipe = onSelectRecipe,
                            savedRecipesList = savedRecipesList,
                            viewModel = viewModel
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
                        recommendedRecipesList = recommendedRecipesList,
                        viewModel = viewModel
                    )
                }
                Box(
                    Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    SavedRecipesRow(
                        onSelectRecipe = onSelectRecipe,
                        savedRecipesList = savedRecipesList,
                        viewModel = viewModel
                    )
                }
                Spacer(Modifier.weight(.1f))
            }
        }
    }

}


//@Preview
//@Composable
//private fun HomeScreenPreview() {
//    val savedRecipesList: List<Recipe> = listOf(RecipeGenerator.placeHolderRecipe())
//    val recommendedRecipesList: List<Recipe> = listOf(RecipeGenerator.placeHolderRecipe())
//    val recommendedIngredientsList: List<Ingredient> =
//        listOf(RecipeGenerator.placeHolderIngredients())
//    HomeScreen(
//        savedRecipesList = savedRecipesList,
//        recommendedIngredientsList = recommendedIngredientsList,
//        recommendedRecipesList = recommendedRecipesList,
//        onSelectRecipe = {}
//    ) {}
//}





