package ramseybros.WhatsForDinner.ui.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ramseybros.WhatsForDinner.R

@Composable
fun SectionList(itemList: List<String>, header: String) {
    Column() {
        Text(header, textAlign = TextAlign.Center, fontWeight = FontWeight.Bold, fontSize = 24.sp)
        LazyRow(Modifier.padding(4.dp), horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            items(itemList.size) {
                Card(
                    Modifier.border(
                        BorderStroke(2.dp, Color.Black),
                        shape = RoundedCornerShape(5)
                    )
                ) {
                    Text(
                        text = itemList[it],
                        Modifier
                            .height(64.dp)
                            .width(64.dp), textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Composable
fun ShoppingList(itemList: List<List<String>>, headerList: List<String>, addRecipe: () -> Unit) {
    Column() {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            horizontalAlignment = Alignment.End
        ) {
            var color: Color = Color.Black
            if (isSystemInDarkTheme()) color = Color.White
            FloatingActionButton(
                backgroundColor = color,
                onClick = { addRecipe() },
                modifier = Modifier.padding(top = 10.dp, end= 16.dp),
                shape = CircleShape,
                contentColor = colorResource(id = R.color.teal_200)
            )
            {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_add_shopping_cart_24),
                    contentDescription = null
                )
            }
        }
        Column(Modifier.fillMaxSize()) {
            Box(
                Modifier
                    .fillMaxWidth()
                    .weight(.75f)
            ) {
                LazyColumn() {
                    items(itemList.size) {
                        SectionList(itemList[it], headerList[it])
                    }
                }
            }
            Box(
                Modifier
                    .fillMaxWidth()
                    .weight(.25f)
            )
        }
    }
}


@Preview(showSystemUi = true, showBackground = true)
@Composable
fun PreviewShoppingList() {
    var headerList: List<String> = listOf(
        "Ingredients",
        "Utensils",
        "Utensils",
        "Utensils",
        "Utensils",
        "Utensils",
        "Utensils",
        "Ingredients"
    )
    var ingredientList: List<String> = listOf(
        "Garlic",
        "Kosher Salt",
        "Paprika",
        "Ground Black Pepper",
        "Cream",
        "Chicken Breast",
        "Beef Stock",
        "Rosemary"
    )
    var utensilList: List<String> = listOf("Spoon", "Whisk", "Pan")
    var itemList: List<List<String>> = listOf(
        ingredientList,
        utensilList,
        utensilList,
        utensilList,
        utensilList,
        utensilList,
        utensilList,
        ingredientList
    )
    ShoppingList(itemList = itemList, headerList = headerList, {})
}