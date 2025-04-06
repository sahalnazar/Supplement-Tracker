package io.github.sahalnazar.projectserotonin.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.sahalnazar.projectserotonin.data.model.local.RichSnackbarData
import io.github.sahalnazar.projectserotonin.data.model.local.TimeWiseSupplementsToConsume
import io.github.sahalnazar.projectserotonin.data.repository.SupplementsRepository
import io.github.sahalnazar.projectserotonin.utils.UiState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
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

    init {
        loadItems()
    }

    fun loadItems() {
        viewModelScope.launch {
            _itemsState.value = UiState.Loading
            try {
                repository.getItemsToConsume(userId)
                    .onSuccess { items ->
                        _itemsState.value = UiState.Success(items ?: emptyList())
                    }
                    .onFailure { e ->
                        _itemsState.value = UiState.Error("Error: ${e.message}")
                    }
            } catch (e: Exception) {
                _itemsState.value = UiState.Error("Error: ${e.message}")
            }
        }
    }

    fun addConsumption(id: String, richSnackbarData: RichSnackbarData) =
        updateItem(id, true, richSnackbarData)

    fun deleteConsumption(id: String) = updateItem(id, false)

    private fun updateItem(
        id: String,
        consumed: Boolean,
        richSnackbarData: RichSnackbarData? = null
    ) {
        // Update local state first
        val previousState = _itemsState.value
        updateLocalState(id, consumed)

        // Then sync with remote
        viewModelScope.launch {
            try {
                val result = if (consumed) {
                    repository.addConsumption(userId, id)
                } else {
                    repository.deleteConsumption(userId, id)
                }

                result.onSuccess {
                    _toastMessage.trySend(richSnackbarData)
                }.onFailure { e ->
                    _itemsState.value = previousState
                    _toastMessage.trySend(RichSnackbarData(null, "Error: $e", null, null, true))
                }
            } catch (e: Exception) {
                _itemsState.value = previousState
                _toastMessage.trySend(RichSnackbarData(null, "Error: $e", null, null, true))
            }
        }
    }

    private fun updateLocalState(id: String, consumed: Boolean) {
        _itemsState.update { currentState ->
            when (currentState) {
                is UiState.Success -> {
                    UiState.Success(currentState.data.map { section ->
                        section?.copy(supplements = section.supplements?.map { item ->
                            if (item.id == id) {
                                item.copy(isConsumed = consumed)
                            } else {
                                item
                            }
                        })
                    })
                }
                else -> currentState
            }
        }
    }
}
