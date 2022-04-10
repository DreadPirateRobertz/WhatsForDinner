package ramseybros.WhatsForDinner.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ramseybros.WhatsForDinner.data.Ingredient
import ramseybros.WhatsForDinner.data.Recipe

@Database(entities = [Ingredient::class, Recipe::class], version = 1)
abstract class WhatsForDinnerDatabase : RoomDatabase() {
    @TypeConverters(WhatsForDinnerTypeConverters::class)
    companion object {
        @Volatile private var INSTANCE: WhatsForDinnerDatabase? = null
        fun getInstance(context: Context): WhatsForDinnerDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if(instance == null) {
                    instance = Room.databaseBuilder(context, WhatsForDinnerDatabase::class.java, "whatsfordinner-database").build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }

    abstract val whatsForDinnerDao: WhatsForDinnerDao
}