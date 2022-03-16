package ramseybros.WhatsForDinner.util

import androidx.compose.ui.res.painterResource
import ramseybros.WhatsForDinner.R
import ramseybros.WhatsForDinner.data.Ingredient
import ramseybros.WhatsForDinner.data.IngredientType

object IngredientListGenerator {
    private var ingredientList: MutableList<Ingredient> = mutableListOf()

    fun generateList() {
        ingredientList = mutableListOf()
        for(i in 1..10) {
            ingredientList.add(Ingredient("Temp...", R.drawable.butter, IngredientType.DAIRY))
        }
    }

    fun getList(): MutableList<Ingredient> {
        return ingredientList
    }

}