package io.github.sahalnazar.projectserotonin.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import io.github.sahalnazar.projectserotonin.R

@Composable
fun SupplementPill(
    modifier: Modifier = Modifier,
    isPaused: Boolean,
    isConsumed: Boolean,
    image: String,
    name: String?,
    serving: String,
    enabled: Boolean = true,
    pillHeight: Dp = 160.dp,
    onConsume: () -> Unit,
    onRemove: () -> Unit
) {
    val previousConsumedState = remember { mutableStateOf(isConsumed) }
    val rotation = remember { Animatable(if (isConsumed) 180f else 0f) }

    LaunchedEffect(isConsumed) {
        if (previousConsumedState.value != isConsumed) {
            val targetRotation = if (isConsumed) 180f else 0f
            rotation.animateTo(
                targetValue = targetRotation,
                animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing)
            )
            previousConsumedState.value = isConsumed
        }
    }

    Card(
        shape = RoundedCornerShape(32.dp),
        enabled = enabled,
        onClick = { if (isConsumed) onRemove() else onConsume() },
        modifier = modifier
            .height(pillHeight)
            .aspectRatio(0.44f, true),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Box(modifier = Modifier.fillMaxSize().background(Color.White)) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(image)
                    .crossfade(true)
                    .build(),
                contentDescription = name ?: "Supplement",
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 28.dp),
                contentScale = ContentScale.FillHeight
            )

            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 4.dp)
                    .graphicsLayer {
                        rotationY = rotation.value
                        cameraDistance = 12f * density
                    },
                contentAlignment = Alignment.Center
            ) {
                if (rotation.value <= 90f) {
                    Box(
                        modifier = Modifier
                            .background(Color(0xFFFDFDFD), CircleShape)
                            .size(32.dp)
                            .border(width = 1.dp, shape = CircleShape, color = Color.LightGray),
                        contentAlignment = Alignment.Center
                    ) {
                        when {
                            isPaused -> Icon(
                                painter = painterResource(R.drawable.baseline_pause_24),
                                contentDescription = "Paused",
                                tint = Color.Black,
                                modifier = Modifier.size(20.dp)
                            )
                            else -> Text(
                                text = serving,
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.Black
                            )
                        }
                    }
                } else {
                    Box(
                        modifier = Modifier
                            .graphicsLayer {
                                rotationY = 180f
                            }
                            .background(Color(0xFFFDFDFD), CircleShape)
                            .size(32.dp)
                            .border(width = 1.dp, shape = CircleShape, color = Color.LightGray),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.baseline_check_circle_24),
                            contentDescription = "Consumed",
                            tint = Color.Green,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }
            }
        }
    }
}
