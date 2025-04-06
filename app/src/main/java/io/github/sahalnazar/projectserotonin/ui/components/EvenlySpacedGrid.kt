package io.github.sahalnazar.projectserotonin.ui.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import io.github.sahalnazar.projectserotonin.data.model.local.RichSnackbarData
import io.github.sahalnazar.projectserotonin.data.model.local.TimeWiseSupplementsToConsume
import io.github.sahalnazar.projectserotonin.utils.getTextColorAccordingTo

@Composable
fun EvenlySpacedGrid(
    section: TimeWiseSupplementsToConsume,
    onConsume: (String, RichSnackbarData) -> Unit,
    onRemove: (String) -> Unit
) {
    val items = section.supplements.orEmpty()
    val maxItemsPerRow = 4
    val rows = items.chunked(maxItemsPerRow)

    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        rows.forEach { rowItems ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                rowItems.forEach { item ->
                    val itemId = item.id
                    if (itemId != null) {
                        Column(
                            modifier = Modifier.weight(1f),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(14.dp)
                        ) {
                            val servingSize = item.servingSize?.toString() ?: "0"
                            SupplementPill(
                                isPaused = item.isPaused,
                                isConsumed = item.isConsumed,
                                image = item.image.orEmpty(),
                                name = item.name,
                                serving = servingSize,
                                onConsume = {
                                    val title = item.snackbarTitle?.let {
                                        "Got your $it today!"
                                    }
                                    val description = item.snackbarDescription?.let {
                                        "TIP: $it"
                                    }
                                    onConsume(
                                        itemId, RichSnackbarData(
                                            title = title,
                                            description = description,
                                            image = item.image,
                                            serving = servingSize,
                                            isError = false
                                        )
                                    )
                                },
                                onRemove = { onRemove(itemId) }
                            )

                            Text(
                                textAlign = TextAlign.Center,
                                text = item.getLastConsumedDateTime(),
                                style = MaterialTheme.typography.bodySmall,
                                color = getTextColorAccordingTo(section.code),
                                maxLines = 2,
                                minLines = 2,
                                modifier = Modifier.animateContentSize()
                            )
                        }
                    } else {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }

                if (rowItems.size < maxItemsPerRow) {
                    repeat(maxItemsPerRow - rowItems.size) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
}
