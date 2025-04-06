package io.github.sahalnazar.projectserotonin.data.repository

import android.util.Log
import io.github.sahalnazar.projectserotonin.data.api.ApiService
import io.github.sahalnazar.projectserotonin.data.db.SupplementsDao
import io.github.sahalnazar.projectserotonin.data.mapper.ResponseMapper.toSupplementEntity
import io.github.sahalnazar.projectserotonin.data.mapper.ResponseMapper.toTimeWiseSupplementsToConsume
import io.github.sahalnazar.projectserotonin.data.model.local.TimeWiseSupplementsToConsume
import io.github.sahalnazar.projectserotonin.utils.getCurrentDateTime
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SupplementsRepository @Inject constructor(
    private val apiService: ApiService,
    private val supplementsDao: SupplementsDao
) {
    fun getItemsToConsume(userId: String) =
        flow<Result<List<TimeWiseSupplementsToConsume?>>> {
            try {
                supplementsDao.getAllSupplements().collect { localData ->
                    if (localData.isNotEmpty()) {
                        val grouped = localData.groupBy { it.timeCode }
                        val timeWiseSupplements = grouped.toTimeWiseSupplementsToConsume()
                        emit(Result.success(timeWiseSupplements))
                    } else {
                        val response = apiService.getItemsToConsume(userId)
                        val timeWiseSupplements =
                            response.data?.itemsToConsume?.toTimeWiseSupplementsToConsume()

                        val supplementEntities = timeWiseSupplements.toSupplementEntity()
                        supplementsDao.insertAllSupplements(supplementEntities)

                        emit(Result.success(timeWiseSupplements ?: emptyList()))
                    }
                }
            } catch (e: Exception) {
                Log.e("getItemsToConsume error", "Error getting items to consume: ${e.message}")
                emit(Result.failure(e))
            }
        }.flowOn(Dispatchers.IO)

    suspend fun updateConsumption(
        userId: String,
        id: String,
        isConsumed: Boolean,
        timestamp: String
    ): Result<Unit> =
        withContext(Dispatchers.IO) {
            try {

                supplementsDao.updateSupplementConsumption(id, isConsumed, timestamp)
                launchApiSync(userId, id, isConsumed)
                Result.success(Unit)
            } catch (e: Exception) {
                Log.e("updateConsumption error", "Error while updating consumption: $e")
                Result.failure(e)
            }
        }

    private suspend fun launchApiSync(userId: String, id: String, isConsumed: Boolean) {
        withContext(Dispatchers.IO) {
            try {
                val dateTime = getCurrentDateTime()
                if (isConsumed) {
                    apiService.addConsumption(
                        userId = userId,
                        id = id,
                        date = dateTime.first,
                        time = dateTime.second
                    )
                } else {
                    apiService.deleteConsumption(
                        userId = userId,
                        id = id,
                        date = dateTime.first,
                        time = dateTime.second
                    )
                }
            } catch (e: Exception) {
                Log.e("API Error", "Error during API sync: ${e.message}")
            }
        }
    }
}
