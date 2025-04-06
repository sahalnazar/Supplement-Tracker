package io.github.sahalnazar.projectserotonin.data.model.local

import android.util.Log
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

data class TimeWiseSupplementsToConsume(
    val code: String? = null,
    val title: String? = null,
    val supplements: List<Supplement>? = null,
)

data class Supplement(
    val id: String? = null,
    val name: String? = null,
    val image: String? = null,
    val isPaused: Boolean = false,
    val servingSize: Int? = null,
    val lastConsumedTimestamp: String? = null,
    val isConsumed: Boolean = false,
    val snackbarTitle: String? = null,
    val snackbarDescription: String? = null,
) {
    fun getLastConsumedDateTime(): String {
        return try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX", Locale.getDefault())
            inputFormat.timeZone = TimeZone.getTimeZone("UTC")

            val outputFormat = SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault())
            outputFormat.timeZone = TimeZone.getDefault()

            val date = lastConsumedTimestamp?.let { inputFormat.parse(it) }
            date?.let { outputFormat.format(date) } ?: kotlin.run {
                Log.d("TimestampFormatter", "date is null: $lastConsumedTimestamp")
                ""
            }
        } catch (e: Exception) {
            Log.e("TimestampFormatter", "Error parsing timestamp: $lastConsumedTimestamp", e)
            "Invalid date"
        }
    }
}
