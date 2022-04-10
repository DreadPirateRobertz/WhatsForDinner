package ramseybros.WhatsForDinner.data.database

import androidx.room.TypeConverter
import java.util.*

class WhatsForDinnerTypeConverters {
    @TypeConverter
    fun fromUUID(uuid: UUID?) = uuid?.toString()
    @TypeConverter
    fun toUUID(uuidString: String?) = UUID.fromString(uuidString)
}