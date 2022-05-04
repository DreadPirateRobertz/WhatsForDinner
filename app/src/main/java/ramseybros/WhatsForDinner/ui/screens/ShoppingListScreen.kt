package ramseybros.WhatsForDinner.ui.screens

import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ramseybros.WhatsForDinner.R
import ramseybros.WhatsForDinner.data.Ingredient
import ramseybros.WhatsForDinner.ui.theme.colorLightSecondary

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

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ShoppingList(ingredientList: List<Ingredient>, onDelete: (Ingredient) -> Unit) {
    Column() {
        LazyVerticalGrid(
            cells = GridCells.Fixed(3),
            contentPadding = PaddingValues(8.dp)
        ) {
            items(ingredientList.size) { index ->
                Box(
                    Modifier
                        .padding(4.dp)
                        .border(2.dp, colorResource(id = R.color.light_blue),shape = RoundedCornerShape(5))
                        .clickable { onDelete(ingredientList[index]) }
                        .height(72.dp)) {
                    Text(
                        text = ingredientList[index].name,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(4.dp)
                    )
                }
            }
        }
    }

}


@Preview(showSystemUi = true, showBackground = true)
@Composable
fun PreviewShoppingList() {
    var ingredientList: List<Ingredient> = listOf()
    ShoppingList(ingredientList = ingredientList, {})
}