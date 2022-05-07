package ramseybros.WhatsForDinner.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.*

@Entity(
    tableName = "recipe_ingredient_list_xref",
    primaryKeys = ["recipeId", "ingredientName"],
    foreignKeys = arrayOf(
        ForeignKey(
            entity = Recipe::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("recipeId"),
            onDelete = CASCADE
        ),
        ForeignKey(
            entity = Ingredient::class,
            parentColumns = arrayOf("name"),
            childColumns = arrayOf("ingredientName"),
            onDelete = CASCADE
        )
    )
)
class RecipeIngredientListXRef(var recipeId: UUID, var ingredientName: String) : Serializable