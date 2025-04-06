package io.github.sahalnazar.projectserotonin.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [SupplementEntity::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun supplementsDao(): SupplementsDao
}
