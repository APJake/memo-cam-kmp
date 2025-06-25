package com.logixowl.memocam.features.memo.dashboard

import androidx.lifecycle.viewModelScope
import com.logixowl.memocam.core.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn

/**
 * Created by AP-Jake
 * on 26/06/2025
 */

class DashboardViewModel : BaseViewModel<DashboardEvent>() {

    private val _state = MutableStateFlow(DashboardUiState())
    val state = _state
        .onStart {
            observeDashboardData()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _state.value
        )

    private fun observeDashboardData() {

    }
}
