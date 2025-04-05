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
import io.github.sahalnazar.projectserotonin.data.model.MainResponse
import io.github.sahalnazar.projectserotonin.data.model.RichSnackbarData
import io.github.sahalnazar.projectserotonin.utils.getTextColorAccordingTo

@Composable
fun EvenlySpacedGrid(
    section: MainResponse.Data.ItemsToConsume,
    onConsume: (String, RichSnackbarData) -> Unit,
    onRemove: (String) -> Unit
) {
    val items = section.items?.filterNotNull().orEmpty()
    val maxItemsPerRow = 4
    val rows = items.chunked(maxItemsPerRow)

    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        rows.forEach { rowItems ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                rowItems.forEach { item ->
                    val itemId = item.product?.id
                    if (itemId != null) {
                        Column(
                            modifier = Modifier.weight(1f),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(14.dp)
                        ) {
                            SupplementPill(
                                isPaused = item.product.regimen?.status == "PAUSED",
                                isConsumed = item.product.consumed == true,
                                image = item.product.image.orEmpty(),
                                name = item.product.name,
                                serving = item.product.regimen?.servingSize?.toString() ?: "0",
                                onConsume = {
                                    val title = item.product.regimen?.name?.let {
                                        "Got your $it today!"
                                    }
                                    val description = item.product.regimen?.description?.let {
                                        "TIP: $it"
                                    }
                                    onConsume(
                                        itemId, RichSnackbarData(
                                            title = title,
                                            description = description,
                                            image = item.product.image,
                                            serving = item.product.regimen?.servingSize?.toString(),
                                            isError = false
                                        )
                                    )
                                },
                                onRemove = { onRemove(itemId) },

                                )

                            val dosage = item.latestConsumption?.dosage
                            val unit = item.latestConsumption?.unit
                            val time = item.latestConsumption?.time
                            val latestConsumption = dosage?.let { "$dosage $unit\n$time" }.orEmpty()

                            Text(
                                textAlign = TextAlign.Center,
                                text = latestConsumption,
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
