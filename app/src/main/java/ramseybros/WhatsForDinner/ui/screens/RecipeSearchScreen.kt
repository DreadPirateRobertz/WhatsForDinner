package ramseybros.WhatsForDinner.ui.screens

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun RecipeSearchScreen() {
    Text(
        textAlign = TextAlign.Center,
        text = "Recipe Search",
        fontSize = 24.sp
    )
}

@Preview
@Composable
private fun Preview() {
    RecipeSearchScreen()
}