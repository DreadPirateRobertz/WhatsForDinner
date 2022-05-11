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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
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
import ramseybros.WhatsForDinner.viewmodels.I_WhatsForDinnerViewModel

@Composable
fun SectionList(itemList: List<String>, header: String) {
    Column {
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
fun ShoppingList(
    ingredientList: List<Ingredient>,
    onDelete: (Ingredient) -> Unit,
    viewModel: I_WhatsForDinnerViewModel
) {
    Column(Modifier.fillMaxSize()) {
        LazyVerticalGrid(
            modifier = Modifier.weight(1f),
            cells = GridCells.Fixed(2),
            contentPadding = PaddingValues(8.dp)
        ) {
            items(ingredientList.size) { index ->
                Box(
                    Modifier
                        .padding(4.dp)
                        .border(
                            2.dp,
                            colorResource(id = R.color.light_blue),
                            shape = RoundedCornerShape(5)
                        )
                        .height(60.dp)
                        .clickable { onDelete(ingredientList[index]) },
                    contentAlignment = Alignment.Center) {
                    Text(
                        text = ingredientList[index].name,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(4.dp)
                    )
                }
            }
        }
        Column(verticalArrangement = Arrangement.Bottom, modifier = Modifier.weight(.1f)) {
            val showDialog = rememberSaveable { mutableStateOf(false) }
            IconButton(onClick = { showDialog.value = true }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_delete_sweep_24),
                    contentDescription = null,
                    tint = colorResource(id = R.color.teal_200)
                )
            }
            if (showDialog.value) {
                AlertDialog(
                    onDismissRequest = { showDialog.value = false },
                    title = { Text("Delete Shopping List") },//TODO: MAKE into string resources
                    text = { Text("Press Check to Delete All") },
                    confirmButton = {
                        IconButton(
                            onClick = {
                                viewModel.deleteAllIngredients()
                                showDialog.value = false
                            }) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = null,
                                tint = Color.Green
                            )
                        }
                    },
                    dismissButton = {
                        IconButton(onClick = { showDialog.value = false }) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = null,
                                tint = Color.Red
                            )
                        }
                    },
                )
            }
        }
    }
}


//@Preview(showSystemUi = true, showBackground = true)
//@Composable
//fun PreviewShoppingList() {
//    var ingredientList: List<Ingredient> = listOf()
//    ShoppingList(ingredientList = ingredientList, {})
//}