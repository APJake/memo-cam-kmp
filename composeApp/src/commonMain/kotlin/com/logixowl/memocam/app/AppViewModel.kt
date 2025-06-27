package com.logixowl.memocam.app

import androidx.lifecycle.viewModelScope
import com.logixowl.memocam.core.BaseViewModel
import com.logixowl.memocam.domain.repository.AuthRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * Created by AP-Jake
 * on 26/06/2025
 */

class AppViewModel(
    private val authRepository: AuthRepository,
) : BaseViewModel<AppEvent>() {
    private val _state = MutableStateFlow(AppUiState())
    val state = _state
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            AppUiState()
        )

    init {
        preloadData()
    }

    private fun preloadData() {
        viewModelScope.launch {
            val isLoggedIn = authRepository.isLoggedIn.firstOrNull() ?: false
            delay(3000)
            _state.update {
                it.copy(
                    isLoading = false,
                    isLoggedIn = isLoggedIn,
                )
            }
        }
    }
}
