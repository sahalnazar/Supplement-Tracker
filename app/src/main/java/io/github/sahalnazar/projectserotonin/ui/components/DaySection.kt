package io.github.sahalnazar.projectserotonin.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import io.github.sahalnazar.projectserotonin.data.model.local.RichSnackbarData
import io.github.sahalnazar.projectserotonin.data.model.local.TimeWiseSupplementsToConsume
import io.github.sahalnazar.projectserotonin.utils.getBgColorAccordingTo
import io.github.sahalnazar.projectserotonin.utils.getTextColorAccordingTo

@Composable
fun DaySection(
    modifier: Modifier,
    section: TimeWiseSupplementsToConsume,
    contentPadding: PaddingValues,
    onConsume: (String, RichSnackbarData) -> Unit,
    onRemove: (String, String) -> Unit,
) {
    Surface(
        color = getBgColorAccordingTo(section.code),
        modifier = modifier.fillMaxWidth(),
    ) {
        Column(
            modifier = Modifier
                .padding(contentPadding)
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = section.title.orEmpty(),
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = getTextColorAccordingTo(section.code)
            )

            EvenlySpacedGrid(
                section = section,
                onConsume = onConsume,
                onRemove = onRemove
            )
        }
    }
}