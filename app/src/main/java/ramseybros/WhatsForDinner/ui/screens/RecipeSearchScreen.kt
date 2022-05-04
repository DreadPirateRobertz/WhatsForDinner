package ramseybros.WhatsForDinner.ui.screens

import android.util.Log
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import ramseybros.WhatsForDinner.R
import ramseybros.WhatsForDinner.data.Recipe
import ramseybros.WhatsForDinner.viewmodels.I_WhatsForDinnerViewModel

private const val LOG_TAG = "ramseybros.RecipeSearchScreen.kt"


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RecipeSearchScreen(
    apiRecipeList: SnapshotStateList<Recipe>,
    onRequestRecipe: (Recipe) -> Unit,
    viewModel: I_WhatsForDinnerViewModel
) {
    Column(Modifier.fillMaxSize()) {
        Box(
            Modifier
                .fillMaxSize()
                ) {
               if(apiRecipeList != emptyList<Recipe>()) {
                    val scope = rememberCoroutineScope()
                    LazyColumn(state = rememberLazyListState()) {
                        items(apiRecipeList,{recipe->recipe.id}) { recipe->
                            val dismissState = rememberDismissState(
                                confirmStateChange = {
                                    when (it){
                                        DismissValue.Default ->{}
                                        DismissValue.DismissedToEnd ->{
                                            apiRecipeList.remove(recipe)
                                            recipe.recommended = false
                                            scope.launch{
                                                viewModel.addRecipe(recipe, emptyList(), emptyList())
                                            }

                                        }
                                        DismissValue.DismissedToStart ->{
                                            apiRecipeList.remove(recipe)
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
                                dismissThresholds = { FractionalThreshold(.3f) },
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
                                        DismissDirection.StartToEnd -> Icons.Default.Star
                                        DismissDirection.EndToStart -> Icons.Default.Delete
                                    }
                                    val scale by animateFloatAsState(targetValue = if(dismissState.targetValue == DismissValue.Default) 0.8f else 1.2f)
                                    val alignment = when(direction){
                                        DismissDirection.StartToEnd -> Alignment.CenterStart
                                        DismissDirection.EndToStart -> Alignment.CenterEnd
                                    }

                                    Box(

                                        modifier = Modifier
                                            .fillMaxSize().background(color)
                                            .padding(start = 12.dp, end = 12.dp),
                                        contentAlignment = alignment
                                    )
                                    {
                                        Icon(imageVector = icon, contentDescription = null, modifier = Modifier.scale(scale))
                                    }

                                }, dismissContent = {

                                SmallRecipeView(recipe = recipe,{onRequestRecipe(recipe)}, dismissState)


                                }
                            )
                        }
                    }
                }
        }
    }
}


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


@Preview
@Composable
private fun Preview() {
    //RecipeSearchScreen(WhatsForDinnerViewModel(), {},{})
}