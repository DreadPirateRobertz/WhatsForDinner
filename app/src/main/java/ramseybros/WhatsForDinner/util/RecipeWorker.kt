package ramseybros.WhatsForDinner.util

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.work.*
import okhttp3.*
import okio.IOException
import org.json.JSONArray
import org.json.JSONObject
import ramseybros.WhatsForDinner.data.Recipe
import ramseybros.WhatsForDinner.viewmodels.I_WhatsForDinnerViewModel
import ramseybros.WhatsForDinner.viewmodels.WhatsForDinnerViewModel
import java.net.URL

class RecipeWorker(context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {
    companion object {
        const val UNIQUE_WORK_NAME = "WhatsForDinner_API_REQUEST"
        const val LOG_TAG = "ramseybros.RecipeWorker"
        private const val PROGRESS = "Progress"
        fun buildOneTimeWorkRequest() = OneTimeWorkRequest
            .Builder(RecipeWorker::class.java)
            .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
            .build()

        fun getApiData(outputData: Data) = outputData.getString(PROGRESS)
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
        if (apiData != null) {
            Log.d(LOG_TAG, "Got result $apiData")
            Log.d(LOG_TAG, "Length ${apiData.length}")
        }
        var substr: String = ""
        var update: Data
        if (apiData != null) {
            while (apiData!!.isNotEmpty()) {
                if (apiData.length < 10000) {
                    substr = apiData.subSequence(0, apiData.length - 1).toString()
                    apiData = substr
                } else {
                    substr = apiData.subSequence(0, 9999).toString()
                    apiData = substr
                }
                update = workDataOf(PROGRESS to substr)
                setProgressAsync(update)
            }
        }
        return Result.success()
    }
}