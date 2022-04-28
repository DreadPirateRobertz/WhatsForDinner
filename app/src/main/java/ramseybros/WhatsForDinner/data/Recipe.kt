package ramseybros.WhatsForDinner.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.*

@Entity(tableName = "recipe")
class Recipe(
    var recommended: Boolean = false,
    var imageLink: String,
    var title: String,
    var difficulty: Int,
    var time: String,
    var recipeText: String,
    @PrimaryKey var id: UUID = UUID.randomUUID(),
    var searchId: Int
) :
    Serializable {
}