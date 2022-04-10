package ramseybros.WhatsForDinner.data.database

import androidx.room.TypeConverter
import ramseybros.WhatsForDinner.data.IngredientType
import java.util.*

class WhatsForDinnerTypeConverters {
    @TypeConverter
    fun fromUUID(uuid: UUID?) = uuid?.toString()
    @TypeConverter
    fun toUUID(uuidString: String?) = UUID.fromString(uuidString)
    @TypeConverter
    fun fromIngredientType(ingredientType: IngredientType?) = when(ingredientType) {
        IngredientType.DAIRY-> "Dairy"
        IngredientType.MEAT-> "Meat"
        IngredientType.VEGETABLE-> "Vegetable"
        IngredientType.FRUIT-> "Fruit"
        IngredientType.SPICE-> "Spice"
        else-> "Other"
    }
    @TypeConverter
    fun toIngredientType(ingredientTypeString: String?) = when(ingredientTypeString) {
        "Dairy"->IngredientType.DAIRY
        "Meat"->IngredientType.MEAT
        "Vegetable"->IngredientType.VEGETABLE
        "Fruit"->IngredientType.FRUIT
        "Spice"->IngredientType.SPICE
        else->IngredientType.SPICE
    }

}