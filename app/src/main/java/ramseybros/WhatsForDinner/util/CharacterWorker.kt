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

class CharacterWorker(context: Context, workerParams: WorkerParameters)
    : Worker(context, workerParams) {
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
        val client = OkHttpClient()
        val request : Request = Request.Builder()
            .url("https://edamam-recipe-search.p.rapidapi.com/search?q=chicken")
            .get()
            .addHeader("x-rapidapi-host", "edamam-recipe-search.p.rapidapi.com")
            .addHeader("x-rapidapi-key", "61306f5afemsh027abae29051434p12c68bjsnd2f5b7c20c9c")
            .build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {}
            override fun onResponse(call: Call, response: Response) {
                val recipeList: MutableList<Recipe> = mutableListOf()
                val jsonData = response.body?.string()
                val jsonObject = JSONObject(jsonData)
                val hits: JSONArray = jsonObject.getJSONArray("hits")
                for (i in (0 until hits.length())) {
                    val recipeObject = hits.getJSONObject(i).getJSONObject("recipe")
                    val recipe: Recipe = Recipe(
                        imageLink = recipeObject.getString("image"),
                        title = recipeObject.getString("label"),
                        difficulty = 0, //TODO: find or change difficulty param
                        time = recipeObject.getString("totalTime"),
                        recipeText = recipeObject.getString("ingredientLines") //TODO: must further parse this JSON response to separate ingredients
                    )
                    Log.d(LOG_TAG, "Recipe $i: ${recipe.title}")
                    recipeList.add(recipe)
                }
            }
        })
        return Result.success() //TODO: return recipeList??
    }
}