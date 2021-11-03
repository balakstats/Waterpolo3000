package com.example.waterpolo3000.workers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.waterpolo3000.data.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

// add initial values to database
class SeedDatabaseWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {

        try {
            val filename = inputData.getString(KEY_FILENAME_2)
            if (filename != null) {
                applicationContext.assets.open(filename).use { inputStream ->
                    JsonReader(inputStream.reader()).use { jsonReader ->
                        val eventType = object : TypeToken<List<GameEventType>>() {}.type
                        val eventTypeList: List<GameEventType> = Gson().fromJson(jsonReader, eventType)

                        Log.v(TAG, "Add to database gameEventTypes: ${eventTypeList.size}")
                        val database = AppDatabase.getInstance(applicationContext)
                        database.gameEventTypeDao().insertAll(eventTypeList)

                        Result.success()
                    }
                }
            } else {
                Log.e(TAG, "Error seeding database - no valid filename")
                Result.failure()
            }
        } catch (ex: Exception) {
            Log.e(TAG, "Error seeding database", ex)
            Result.failure()
        }

        try {
            val filename = inputData.getString(KEY_FILENAME_3)
            if (filename != null) {
                applicationContext.assets.open(filename).use { inputStream ->
                    JsonReader(inputStream.reader()).use { jsonReader ->
                        val functionType = object : TypeToken<List<FunctionTypes>>() {}.type
                        val functionTypeList: List<FunctionTypes> = Gson().fromJson(jsonReader, functionType)

                        Log.v(TAG, "Add to database functionTypes: ${functionTypeList.size}")
                        val database = AppDatabase.getInstance(applicationContext)
                        database.functionTypesDao().insertAll(functionTypeList)

                        Result.success()
                    }
                }
            } else {
                Log.e(TAG, "Error seeding database - no valid filename")
                Result.failure()
            }
        } catch (ex: Exception) {
            Log.e(TAG, "Error seeding database", ex)
            Result.failure()
        }
    }

    companion object {
        private const val TAG = "SeedDatabaseWorker"
        const val KEY_FILENAME_2 = "GAME_EVENT_TYPES_FILENAME"
        const val KEY_FILENAME_3 = "FUNCTION_TYPES_FILENAME"
    }
}
