package ramseybros.WhatsForDinner.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ramseybros.WhatsForDinner.R

@Composable
fun SmallRecipeView( onClick: () -> Unit ) {
    Card (modifier = Modifier.padding(16.dp)
        .clickable { onClick() }) {
        Row {
            Image(
                painter = painterResource(R.drawable.pot_image),
                contentDescription = "recipe image"
            )
            Text(
                text = "Recipe Title"
            )
            Column {
                Text(
                    text = "Calories"
                )
                Text(
                    text = "Cook Time"
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview () {
    SmallRecipeView {}
}