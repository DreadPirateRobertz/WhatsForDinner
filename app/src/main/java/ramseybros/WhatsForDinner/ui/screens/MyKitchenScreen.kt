package ramseybros.WhatsForDinner.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ramseybros.WhatsForDinner.R
import ramseybros.WhatsForDinner.data.Ingredient
import ramseybros.WhatsForDinner.data.IngredientType
import ramseybros.WhatsForDinner.util.IngredientListGenerator

@Composable
fun MyKitchen (onClick: () -> Unit) {
    Text(
        text = "My Kitchen"
    )
    Spacer(
        Modifier.height(15.dp)
    )
    LazyColumn() {
        items(10) {
            IngredientRow {}
        }
    }
}



@Composable
private fun IngredientRow(onClick: () -> Unit) {
    IngredientListGenerator.generateList()
    val list = IngredientListGenerator.getList()
    Text(text = IngredientType.DAIRY.toString())
    Spacer(
        Modifier.height(5.dp)
    )
    LazyRow() {
        items(list.size) {
            IngredientIcon(image = painterResource(list[it].image), text = list[it].name) {
            }
        }
    }
}

@Composable
private fun IngredientIcon(image: Painter, text: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(5.dp)
            .border(1.dp, Color.Black, RoundedCornerShape(10))
            .padding(5.dp)
    ) {
        Column {
            Image (
                painter = image,
                contentDescription = text,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            )
            Text(
                text = text,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = false)
@Composable
private fun Preview () {
    IngredientIcon(image = painterResource(R.drawable.butter), text = "Butter") {}
}