package io.github.sahalnazar.projectserotonin.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface SupplementsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllSupplements(supplements: List<SupplementEntity>)

    @Query("SELECT * FROM supplements")
    fun getAllSupplements(): Flow<List<SupplementEntity>>

    @Query("UPDATE supplements SET isConsumed = :isConsumed, lastConsumedTimestamp = :timestamp WHERE id = :supplementId")
    suspend fun updateSupplementConsumption(supplementId: String, isConsumed: Boolean, timestamp: String)
}

