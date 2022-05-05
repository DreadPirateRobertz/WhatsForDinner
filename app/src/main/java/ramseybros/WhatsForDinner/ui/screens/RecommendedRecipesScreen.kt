package ramseybros.WhatsForDinner.ui.screens


import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import ramseybros.WhatsForDinner.R
import ramseybros.WhatsForDinner.data.Recipe
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.request.ImageRequest
import kotlinx.coroutines.launch
import ramseybros.WhatsForDinner.viewmodels.I_WhatsForDinnerViewModel


@Composable
private fun SectionHeader(title: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
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

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun RecommendedRecipeRow(
    recommendedRecipe: Recipe,
    onSelectRecipe: (Recipe) -> Any,
    dismissState: DismissState
) {
    //Clicking A recipe will take you to how to make it...
    val size = 100.dp
    val elevation =
        animateDpAsState(targetValue = if (dismissState.dismissDirection != null) 4.dp else 0.dp).value
    Box(modifier = Modifier.fillMaxWidth()) {
        Card(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp)
                .clickable { onSelectRecipe(recommendedRecipe) },
            elevation = elevation
        ) {
            Row(
                Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(recommendedRecipe.imageLink)
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
                Text(
                    fontSize = 16.sp,
                    text = recommendedRecipe.title,
                    modifier = Modifier
                        .padding(4.dp)
                        .weight(1f),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RecommendedRecipesScreen(
    recommendedRecipesList: State<SnapshotStateList<Recipe>>,
    onSelectRecipe: (Recipe) -> Any,
    viewModel: I_WhatsForDinnerViewModel
) {

    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
//        SavedRecipesSection()
        Box(Modifier.fillMaxSize()) {
            if (recommendedRecipesList.value.isNotEmpty()) {
                val scope = rememberCoroutineScope()
                LazyColumn(state = rememberLazyListState()) {
                    items(recommendedRecipesList.value, key = { recipe -> recipe.id }) { recipe ->
                        val dismissState = rememberDismissState(
                            confirmStateChange = {
                                when (it) {
                                    DismissValue.Default -> {}
                                    DismissValue.DismissedToEnd ->{
                                        recommendedRecipesList.value.remove(recipe)
                                        recipe.recommended = false
                                        scope.launch{
                                            viewModel.addRecipe(recipe, emptyList(), emptyList())
                                        }
                                    }
                                    DismissValue.DismissedToStart -> {
                                        recommendedRecipesList.value.remove(recipe)
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
                            directions = setOf(DismissDirection.EndToStart, DismissDirection.StartToEnd),
                            dismissThresholds = { FractionalThreshold(.3f) },
                            background = {

                                val direction =
                                    dismissState.dismissDirection ?: return@SwipeToDismiss
                                val color by animateColorAsState(
                                    targetValue = when (dismissState.targetValue) {
                                        DismissValue.Default -> Color.Transparent
                                        DismissValue.DismissedToEnd -> Color.Green
                                        DismissValue.DismissedToStart -> Color.Red
                                    }
                                )
                                val icon = when(direction){
                                    DismissDirection.StartToEnd -> painterResource(R.drawable.ic_baseline_star_24)
                                    DismissDirection.EndToStart -> painterResource(R.drawable.ic_baseline_delete_sweep_24)
                                }
                                val scale by animateFloatAsState(targetValue = if (dismissState.targetValue == DismissValue.Default) 0.8f else 1.2f)
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

                                RecommendedRecipeRow(
                                    recommendedRecipe = recipe,
                                    onSelectRecipe = onSelectRecipe,
                                    dismissState = dismissState
                                )

                            }

                        )

                    }
                }
            }
        }
        Spacer(Modifier.weight(0.2f))
    }
}


//@Preview
//@Composable
//private fun SavedRecipesScreenPreview() {
//    val savedRecipesList = mutableListOf(RecipeGenerator.placeHolderRecipe())
//    savedRecipesList.add(RecipeGenerator.placeHolderRecipe())
//    savedRecipesList.add(RecipeGenerator.placeHolderRecipe())
//    savedRecipesList.add(RecipeGenerator.placeHolderRecipe())
//    savedRecipesList.add(RecipeGenerator.placeHolderRecipe())
//    savedRecipesList.add(RecipeGenerator.placeHolderRecipe())
//    savedRecipesList.add(RecipeGenerator.placeHolderRecipe())
//    SavedRecipesScreen(
//        savedRecipesList = savedRecipesList,
//        onSelectRecipe = {},
//    )
//}
