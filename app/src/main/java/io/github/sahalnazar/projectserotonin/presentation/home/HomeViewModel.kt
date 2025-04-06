package io.github.sahalnazar.projectserotonin.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.sahalnazar.projectserotonin.data.model.local.RichSnackbarData
import io.github.sahalnazar.projectserotonin.data.model.local.TimeWiseSupplementsToConsume
import io.github.sahalnazar.projectserotonin.data.repository.SupplementsRepository
import io.github.sahalnazar.projectserotonin.utils.UiState
import io.github.sahalnazar.projectserotonin.utils.getCurrentTimestamp
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: SupplementsRepository
) : ViewModel() {
    private val userId = "65fc6b26903c19785332f6cc"

    private val _itemsState =
        MutableStateFlow<UiState<List<TimeWiseSupplementsToConsume?>>>(UiState.Loading)
    val itemsState = _itemsState.asStateFlow()

    private val _toastMessage = Channel<RichSnackbarData?>(Channel.BUFFERED)
    val toastMessage = _toastMessage.receiveAsFlow()

    fun loadItems() {
        viewModelScope.launch {
            repository.getItemsToConsume(userId).collect { result ->
                result
                    .onSuccess { items ->
                        _itemsState.value = UiState.Success(items)
                    }
                    .onFailure { e ->
                        _itemsState.value = UiState.Error("Error: ${e.message}")
                    }
            }
        }
    }

    fun addConsumption(id: String, richSnackbarData: RichSnackbarData) =
        updateItem(id, true, richSnackbarData)

    fun deleteConsumption(id: String, previousTimestamp: String) =
        updateItem(id, false, previousTimestamp = previousTimestamp)

    private fun updateItem(
        id: String,
        consumed: Boolean,
        richSnackbarData: RichSnackbarData? = null,
        previousTimestamp: String? = null
    ) {
        viewModelScope.launch {
            try {
                val timestamp = if (consumed) getCurrentTimestamp() else previousTimestamp
                val result = repository.updateConsumption(userId, id, consumed, timestamp.orEmpty())
                result.onSuccess {
                    _toastMessage.trySend(richSnackbarData)
                }.onFailure { e ->
                    _toastMessage.trySend(RichSnackbarData(null, "Error: $e", null, null, true))
                }
            } catch (e: Exception) {
                _toastMessage.trySend(RichSnackbarData(null, "Error: $e", null, null, true))
            }
        }
    }
}
