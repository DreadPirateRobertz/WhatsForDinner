package ramseybros.WhatsForDinner.data

import java.io.Serializable
import java.util.*

class Recipe(var imageLink: String, var title: String, var difficulty: Int, var time: String, var ingredientList: List<Ingredient>, var utensilList: List<String>, var recipeText: String, var id : UUID = UUID.randomUUID()) :
    Serializable {
}