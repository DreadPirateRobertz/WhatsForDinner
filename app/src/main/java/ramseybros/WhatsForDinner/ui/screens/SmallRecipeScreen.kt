package ramseybros.WhatsForDinner.ui.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import ramseybros.WhatsForDinner.R
import ramseybros.WhatsForDinner.data.Recipe
import ramseybros.WhatsForDinner.util.RecipeGenerator

@Composable
fun SmallRecipeView(recipe: Recipe, onClick: () -> Unit) {
    Box(Modifier.fillMaxWidth()) {
        Column(Modifier.fillMaxSize()) {
            Card(modifier = Modifier
                .padding(10.dp)
                .clickable { onClick() }
                .border(2.dp, Color.Black, RoundedCornerShape(10))
                .padding(6.dp)
            ) {
                Row {
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
                            .border(1.dp, Color.Black, RoundedCornerShape(10))
                            .padding(16.dp)
                            .size(64.dp)
                    )
                    Text(
                        text = recipe.title,
                        modifier = Modifier
                            .padding(10.dp)
                            .align(Alignment.CenterVertically)
                            .border(1.dp, Color.Black, RoundedCornerShape(10))
                            .padding(6.dp)
                    )
//                    Column(
//                    ) {
//                        Text(
//                            text = "\"Calories\"",
//                            modifier = Modifier
//                                .padding(10.dp)
//                                .align(Alignment.CenterHorizontally)
//                                .border(1.dp, Color.Black, RoundedCornerShape(10))
//                                .padding(6.dp)
//                        )
//                        Text(
//                            text = "\"Cook Time\"",
//                            modifier = Modifier
//                                .padding(10.dp)
//                                .align(Alignment.CenterHorizontally)
//                                .border(1.dp, Color.Black, RoundedCornerShape(10))
//                                .padding(6.dp)
//                        )
//                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun Preview() {
    SmallRecipeView(RecipeGenerator.singleRecipe()) {}
}