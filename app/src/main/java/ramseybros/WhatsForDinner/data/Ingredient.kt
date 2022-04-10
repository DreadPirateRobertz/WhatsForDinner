package ramseybros.WhatsForDinner.data

import java.io.Serializable
import androidx.compose.ui.graphics.painter.Painter
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ingredient")
class Ingredient(s: String, image: Int, type: IngredientType): Serializable{
    @PrimaryKey val name = s
    val image = image
    val type = type
}