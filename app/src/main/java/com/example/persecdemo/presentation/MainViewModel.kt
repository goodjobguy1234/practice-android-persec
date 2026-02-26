package com.example.persecdemo.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.persecdemo.usecase.NasaRepo
import com.example.persecdemo.usecase.model.ApodModel
import com.example.persecdemo.utils.formatDate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.TimeZone
import javax.inject.Inject

data class PictureListState(
    val list: List<ApodModel>,
    val isLoading: Boolean,
    val startDate: String,
    val endDate: String
)

sealed interface PictureListScreenEvent {
    data object RenderEmptyList: PictureListScreenEvent
    data object ShowBottomSheetError: PictureListScreenEvent
}

sealed interface PictureListScreenIntent {
    data class GetPictureOfDay(
        val startDay: String,
        val endDay: String
    ) : PictureListScreenIntent

    data class UpdateDate(
        val startDay: String? = null,
        val endDay: String? = null
    ): PictureListScreenIntent
}

@HiltViewModel
class MainViewModel @Inject constructor(
    private val nasaRepository: NasaRepo
) : ViewModel() {
    private val _state: MutableStateFlow<PictureListState> = MutableStateFlow(
        PictureListState(
            emptyList(),
            isLoading = true,
            startDate = formatDate(
                Calendar.getInstance(TimeZone.getTimeZone("UTC")).timeInMillis
            ),
            endDate = formatDate(
                Calendar.getInstance(TimeZone.getTimeZone("UTC")).timeInMillis
            )
        )
    )
    val state = _state.asStateFlow()

    private val _effect: MutableSharedFlow<PictureListScreenEvent> = MutableSharedFlow()
    val effect = _effect.asSharedFlow()

    fun handleIntent(intent: PictureListScreenIntent) {
        when(intent) {
            is PictureListScreenIntent.GetPictureOfDay -> {
                fetchPictureOfDay(intent.startDay, intent.endDay)
            }

            is PictureListScreenIntent.UpdateDate -> {
                _state.update { current ->
                    current.copy(
                        startDate = intent.startDay ?: _state.value.startDate,
                        endDate = intent.endDay ?: _state.value.endDate
                    )
                }
            }
        }
    }

    private fun fetchPictureOfDay(
        startDay: String,
        endDay: String
    ) {
        viewModelScope.launch {
            nasaRepository.fetchPictureOfDate(
                startDate = startDay,
                endDate = endDay
            ).onSuccess { result ->
                if (result.isEmpty()) {
                    _effect.emit(PictureListScreenEvent.RenderEmptyList)
                } else {
                    _state.update { current ->
                        current.copy(list = result)
                    }
                }
            }.onFailure {
               _effect.emit(PictureListScreenEvent.ShowBottomSheetError)
            }
        }
    }
}