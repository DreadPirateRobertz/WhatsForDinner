package ramseybros.WhatsForDinner.util

import android.content.Context
import android.util.Log
import androidx.work.*
import okhttp3.*
import okio.IOException
import org.json.JSONArray
import org.json.JSONObject
import ramseybros.WhatsForDinner.data.Recipe
import java.net.URL

class CharacterWorker(context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {
    companion object {
        const val UNIQUE_WORK_NAME = "WhatsForDinner_API_REQUEST"
        const val LOG_TAG = "ramseybros.CharacterWorker"
        private const val CHARACTER_API_KEY = "apiCharacterData"
        fun buildOneTimeWorkRequest() = OneTimeWorkRequest
            .Builder(CharacterWorker::class.java)
            .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
            .build()

        fun getApiData(outputData: Data) = outputData.getString(CHARACTER_API_KEY)
    }

    override fun doWork(): Result {
        Log.d(LOG_TAG, "Work request triggered")
        val ingredients: String = "chicken%2Crice&2cbeans"
        val client = OkHttpClient()
        var apiData: String? = null
        val request: Request = Request.Builder()
            .url("https://spoonacular-recipe-food-nutrition-v1.p.rapidapi.com/recipes/findByIngredients?ingredients=$ingredients&number=10&ignorePantry=true&ranking=1")
            .get()
            .addHeader("x-rapidapi-host", "spoonacular-recipe-food-nutrition-v1.p.rapidapi.com")
            .addHeader("x-rapidapi-key", "61306f5afemsh027abae29051434p12c68bjsnd2f5b7c20c9c")
            .build()
        val response = client.newCall(request).execute()
        apiData = response.body?.string()
        Log.d(LOG_TAG, "Got result $apiData")
        val outputData = workDataOf(CHARACTER_API_KEY to apiData)
        Log.d(LOG_TAG, "outputData contains $outputData")
        return Result.success(outputData) //TODO: return recipeList??
    }
}