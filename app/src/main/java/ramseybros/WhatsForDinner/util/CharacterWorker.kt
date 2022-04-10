package ramseybros.WhatsForDinner.util

import android.content.Context
import android.util.Log
import androidx.work.*
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
//        val apiData = URL("https://chargen-api.herokuapp.com").readText()  //TODO: FIX THIS
//        Log.d(LOG_TAG, "Got result $apiData")
//        val outputData = workDataOf( CHARACTER_API_KEY to apiData )
//        return Result.success(outputData)
        return Result.success()
    }
}