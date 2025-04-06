package io.github.sahalnazar.projectserotonin.data.repository

import android.util.Log
import io.github.sahalnazar.projectserotonin.data.api.ApiService
import io.github.sahalnazar.projectserotonin.data.mapper.ResponseMapper.toTimeWiseSupplementsToConsume
import io.github.sahalnazar.projectserotonin.data.model.local.TimeWiseSupplementsToConsume
import io.github.sahalnazar.projectserotonin.utils.getCurrentDateTime
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SupplementsRepository @Inject constructor(
    private val apiService: ApiService
) {

    suspend fun getItemsToConsume(userId: String): Result<List<TimeWiseSupplementsToConsume?>?> =
        withContext(Dispatchers.IO) {
            try {
                val response = apiService.getItemsToConsume(userId)
                Result.success(response.data?.itemsToConsume?.toTimeWiseSupplementsToConsume())
            } catch (e: Exception) {
                Log.e("getItemsToConsume error", "Error getting items to consume")
                Result.failure(e)
            }
        }

    suspend fun addConsumption(userId: String, supplementId: String): Result<Unit> =
        withContext(Dispatchers.IO) {
            try {
                val dateTime = getCurrentDateTime()
                apiService.addConsumption(
                    userId = userId,
                    id = supplementId,
                    date = dateTime.first,
                    time = dateTime.second
                )
                Result.success(Unit)
            } catch (e: Exception) {
                Log.e("addConsumption error", "supplementId: $supplementId. $e")
                Result.failure(e)
            }
        }

    suspend fun deleteConsumption(userId: String, supplementId: String): Result<Unit> =
        withContext(Dispatchers.IO) {
            try {
                val dateTime = getCurrentDateTime()
                apiService.deleteConsumption(
                    userId = userId,
                    id = supplementId,
                    date = dateTime.first,
                    time = dateTime.second
                )
                Result.success(Unit)
            } catch (e: Exception) {
                Log.e("deleteConsumption error", "supplementId: $supplementId. $e")
                Result.failure(e)
            }
        }
}
