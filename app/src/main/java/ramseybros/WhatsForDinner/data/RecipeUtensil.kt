package ramseybros.WhatsForDinner.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import ramseybros.WhatsForDinner.data.Recipe
import java.util.*

@Entity(
    tableName = "utensil",
    primaryKeys = ["name", "recipeId"],
    foreignKeys = arrayOf(
        ForeignKey(
            entity = Recipe::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("recipeId"),
            onDelete = ForeignKey.CASCADE
        )
    )
)
class RecipeUtensil(val name: String, val recipeId: UUID) {
}