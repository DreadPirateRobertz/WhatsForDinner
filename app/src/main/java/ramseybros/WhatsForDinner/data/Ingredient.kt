package ramseybros.WhatsForDinner.data

import java.io.Serializable
import androidx.compose.ui.graphics.painter.Painter

class Ingredient(s: String, image: Int, type: IngredientType): Serializable{
    val name = s
    val image = image
    val type = type
}