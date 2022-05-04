package ramseybros.WhatsForDinner.ui.screens

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.DismissState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import ramseybros.WhatsForDinner.R
import ramseybros.WhatsForDinner.data.Recipe
import ramseybros.WhatsForDinner.util.RecipeGenerator

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SmallRecipeView(recipe: Recipe, onClick: () -> Unit, dismissState: DismissState) {
    var size = 100.dp
    val elevation = animateDpAsState(targetValue = if(dismissState.dismissDirection != null) 4.dp else 0.dp).value
    Box(Modifier.fillMaxWidth()) {
            Card(modifier = Modifier
                .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp)
                .fillMaxWidth()
                .clickable { onClick() },
                elevation = elevation
//                .border(2.dp, Color.Black, RoundedCornerShape(10))
            ) {
                Row (Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceAround){
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
                            .weight(1.5f)

                    )
                    Text(
                        text = recipe.title,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(4.dp)
                            .align(Alignment.CenterVertically)
                            .weight(1f)
                    )
                }
//            }
        }
    }
}


//@Preview(showBackground = true)
//@Composable
//private fun Preview() {
//    SmallRecipeView(RecipeGenerator.singleRecipe()) {}
//}