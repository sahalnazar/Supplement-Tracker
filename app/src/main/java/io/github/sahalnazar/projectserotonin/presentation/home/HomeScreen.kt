package io.github.sahalnazar.projectserotonin.presentation.home

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.github.sahalnazar.projectserotonin.data.model.local.RichSnackbarVisuals
import io.github.sahalnazar.projectserotonin.ui.components.DaySection
import io.github.sahalnazar.projectserotonin.ui.components.ErrorComponent
import io.github.sahalnazar.projectserotonin.ui.components.LoadingComponent
import io.github.sahalnazar.projectserotonin.ui.components.RichSnackBar
import io.github.sahalnazar.projectserotonin.utils.UiState

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(viewModel: HomeViewModel = hiltViewModel()) {
    val uiState by viewModel.itemsState.collectAsStateWithLifecycle()
    var idToRemoveConsumption by remember { mutableStateOf<String?>(null) }
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.toastMessage.collect { data ->
            data?.let {
                val visuals = RichSnackbarVisuals(
                    message = it.description.orEmpty(),
                    title = it.title,
                    description = it.description,
                    image = it.image,
                    isError = it.isError,
                    duration = SnackbarDuration.Short,
                    actionLabel = null,
                    withDismissAction = false,
                    serving = it.serving
                )
                snackbarHostState.showSnackbar(visuals)
            }
        }
    }

    Scaffold(
        containerColor = Color.White,
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier.padding(16.dp)
            ) { data ->
                RichSnackBar(data)
            }
        },
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            when (val state = uiState) {
                is UiState.Loading -> {
                    LoadingComponent()
                }

                is UiState.Error -> {
                    ErrorComponent(state, viewModel::loadItems)
                }

                is UiState.Success -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        itemsIndexed(
                            items = state.data.filterNotNull(),
                            key = { _, section ->
                                section.code ?: it.hashCode().toString()
                            }
                        ) { index, section ->
                            val contentPadding = PaddingValues(
                                top = if (index == 0) WindowInsets.statusBars.asPaddingValues()
                                    .calculateTopPadding() else 0.dp,
                                bottom = if (index == state.data.lastIndex) WindowInsets.navigationBars.asPaddingValues()
                                    .calculateBottomPadding() else 0.dp
                            )
                            DaySection(
                                modifier = Modifier,
                                section = section,
                                contentPadding = contentPadding,
                                onConsume = viewModel::addConsumption,
                                onRemove = { id ->
                                    idToRemoveConsumption = id
                                }
                            )
                        }
                    }
                }
            }
            if (idToRemoveConsumption != null) {
                AlertDialog(
                    onDismissRequest = { idToRemoveConsumption = null },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                idToRemoveConsumption?.let(viewModel::deleteConsumption)
                                idToRemoveConsumption = null
                            }
                        ) { Text("Confirm") }
                    },
                    dismissButton = {
                        TextButton(onClick = { idToRemoveConsumption = null }) {
                            Text("Cancel")
                        }
                    },
                    title = { Text("Remove Consumption?") }
                )
            }
        }
    }
}
