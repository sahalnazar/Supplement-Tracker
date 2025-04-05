package io.github.sahalnazar.projectserotonin.data.model

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarVisuals

class RichSnackbarVisuals(
    override val message: String,
    val title: String?,
    val description: String?,
    val image: String?,
    val serving: String?,
    val isError: Boolean,
    override val duration: SnackbarDuration,
    override val actionLabel: String?,
    override val withDismissAction: Boolean,
) : SnackbarVisuals