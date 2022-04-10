package ramseybros.WhatsForDinner.data

import java.io.Serializable
import androidx.compose.ui.graphics.painter.Painter
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ingredient")
class Ingredient(@PrimaryKey val name: String, val image: Int, val type: IngredientType): Serializable{
}