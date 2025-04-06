package io.github.sahalnazar.projectserotonin.utils

import androidx.compose.ui.graphics.Color
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

fun getBgColorAccordingTo(code: String?): Color {
    return when (code) {
        "DAYTIME" -> Color(0xFFBCE1EE)
        "NIGHT_TIME" -> Color(0xff18183a)
        else -> Color(0xFFE8E8E8)
    }
}

fun getTextColorAccordingTo(code: String?): Color {
    return when (code) {
        "NIGHT_TIME" -> Color(0xFFEEEEEE)
        "DAYTIME" -> Color(0xff18183a)
        else -> Color(0xff18183a)
    }
}

fun getCurrentDateTime(): Pair<String, String> {
    val date = SimpleDateFormat("dd-MM-yyyy", Locale.US).format(Date())
    val time = SimpleDateFormat("HH:mm:ss", Locale.US).format(Date())
    return date to time
}

fun getCurrentTimestamp(): String {
    val formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME
    return Instant.now()
        .atOffset(ZoneOffset.UTC)
        .format(formatter)
}
