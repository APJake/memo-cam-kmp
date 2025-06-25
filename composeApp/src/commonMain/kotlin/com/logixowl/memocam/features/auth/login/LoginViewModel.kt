package com.logixowl.memocam.features.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.logixowl.memocam.core.BaseViewModel
import com.logixowl.memocam.domain.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn

/**
 * Created by AP-Jake
 * on 25/06/2025
 */

class LoginViewModel(
): BaseViewModel<LoginEvent>() {

    private val _state = MutableStateFlow(LoginUiState())
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
