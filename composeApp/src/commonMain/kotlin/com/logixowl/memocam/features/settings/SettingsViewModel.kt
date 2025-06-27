package com.logixowl.memocam.features.settings

import androidx.lifecycle.viewModelScope
import com.logixowl.memocam.core.AppLogger
import com.logixowl.memocam.core.BaseViewModel
import com.logixowl.memocam.core.onError
import com.logixowl.memocam.core.onSuccess
import com.logixowl.memocam.domain.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * Created by AP-Jake
 * on 28/06/2025
 */

class SettingsViewModel(
    private val authRepository: AuthRepository,
): BaseViewModel<SettingsEvent>() {

    private val _state = MutableStateFlow(SettingsUiState())
    val state = _state
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            SettingsUiState()
        )

    init {
        _state.update {
            it.copy(
                appVersion = "1.0.0"
            )
        }
    }

    fun onAction(action: SettingsAction) {
        when (action) {
            SettingsAction.OnClickedLogout -> doLogout()
            else -> {

            }
        }
    }

    private fun doLogout() {
        viewModelScope.launch {
            authRepository.logout()
                .onSuccess {
                    emitEvent(SettingsEvent.SuccessLogout)
                }
                .onError {
                    AppLogger.e("SettingsViewModel", "Failed to logout: $it")
                    emitEvent(SettingsEvent.Error("Failed to logout"))
                }
        }
    }

}
