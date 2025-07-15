package com.logixowl.memocam.features.change_password

import androidx.lifecycle.viewModelScope
import com.logixowl.memocam.core.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn

/**
 * Created by AP-Jake
 * on 08/07/2025
 */

class ChangePasswordViewModel(

) : BaseViewModel<ChangePasswordEvent>() {
    private val _state = MutableStateFlow(ChangePasswordUiState())
    val state = _state
        .onStart {
            doOnStart()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            ChangePasswordUiState()
        )

    fun onAction(action: ChangePasswordAction) {
        when (action) {
            else -> {}
        }
    }

    private fun doOnStart() {

    }
}
