package ramseybros.WhatsForDinner.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ramseybros.WhatsForDinner.data.Recipe
import ramseybros.WhatsForDinner.data.Ingredient
import ramseybros.WhatsForDinner.util.RecipeGenerator

@Composable
fun ListItems(recipe: Recipe, inKitchenList: List<String>) {
    Row(Modifier.border(BorderStroke(2.dp, Color.Black), shape = RoundedCornerShape(5))) {
        LazyColumn(Modifier.weight(1.0f).padding(4.dp)) {
            items(recipe.ingredientList.size) {
                if(recipe.ingredientList[it].toString() in inKitchenList) {
                    Text(recipe.ingredientList[it].toString())
                } else {
                    Text(recipe.ingredientList[it].toString(),color = Color.Red)
                }
            }
        }
        LazyColumn(Modifier.weight(1.0f).padding(4.dp)) {
            items(recipe.utensilList.size) {
                if(recipe.utensilList[it] in inKitchenList) {
                    Text(recipe.utensilList[it])
                } else {
                    Text(recipe.utensilList[it],color = Color.Red)
                }
            }
        }
    }
}

@Composable
fun RecipeText(recipeText: String) {
    Text(recipeText, Modifier.padding(4.dp))
}

@Composable
fun RecipeInformation(recipe : Recipe, inKitchenList: List<String>) {
    Column(Modifier.padding(4.dp)) {
        ListItems(recipe = recipe, inKitchenList = inKitchenList)
        Spacer(Modifier.height(16.dp))
        RecipeText(recipeText = recipe.recipeText)
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun previewRecipe() {
    var inKitchenList: List<String> = listOf("Garlic", "Paprika", "Ground Black Pepper", "Spoon", "Whisk")
    RecipeInformation(RecipeGenerator.singleRecipe(), inKitchenList = inKitchenList)
}