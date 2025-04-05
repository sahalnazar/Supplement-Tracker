package io.github.sahalnazar.projectserotonin.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import io.github.sahalnazar.projectserotonin.R
import io.github.sahalnazar.projectserotonin.data.model.RichSnackbarVisuals

@Composable
fun RichSnackBar(data: SnackbarData) {
    val visuals = data.visuals as? RichSnackbarVisuals
    visuals?.let {
        Card(
            colors = CardDefaults.cardColors(containerColor = Color(0xFF6e70ed)),
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(4.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    SupplementPill(
                        isPaused = false,
                        isConsumed = false,
                        image = visuals.image.orEmpty(),
                        serving = visuals.serving.orEmpty(),
                        enabled = false,
                        name = visuals.title,
                        pillHeight = 120.dp,
                        onRemove = {},
                        onConsume = {}
                    )

                    Column {
                        it.title?.let { title ->
                            Text(
                                text = title,
                                modifier = Modifier,
                                style = MaterialTheme.typography.headlineSmall,
                                color = Color.White
                            )
                        }
                        it.description?.let { description ->
                            Text(
                                text = description,
                                modifier = Modifier,
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.White.copy(alpha = 0.9f)
                            )
                        }

                    }
                }

                Icon(
                    painter = painterResource(R.drawable.baseline_arrow_forward_24),
                    contentDescription = null,
                    tint = Color.White
                )
            }
        }
    }
}