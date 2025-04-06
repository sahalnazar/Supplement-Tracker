package io.github.sahalnazar.projectserotonin.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "supplements")
data class SupplementEntity(
    @PrimaryKey val id: String,
    val name: String? = null,
    val image: String? = null,
    val isPaused: Boolean = false,
    val servingSize: Int? = null,
    val lastConsumedTimestamp: String? = null,
    val isConsumed: Boolean = false,
    val snackbarTitle: String? = null,
    val snackbarDescription: String? = null,
    val timeCode: String,
    val timeTitle: String? = null
)

