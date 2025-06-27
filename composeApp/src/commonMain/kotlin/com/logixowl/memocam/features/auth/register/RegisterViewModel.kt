package com.logixowl.memocam.features.auth.register

import androidx.lifecycle.viewModelScope
import com.logixowl.memocam.core.AppLogger
import com.logixowl.memocam.core.BaseViewModel
import com.logixowl.memocam.core.onError
import com.logixowl.memocam.core.onSuccess
import com.logixowl.memocam.domain.model.payload.RegisterPayload
import com.logixowl.memocam.domain.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * Created by AP-Jake
 * on 25/06/2025
 */

class RegisterViewModel(
    private val authRepository: AuthRepository,
) : BaseViewModel<RegisterEvent>() {

    private val _state = MutableStateFlow(RegisterUiState())
    val state = _state
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _state.value
        )

    fun onAction(action: RegisterAction) {
        when (action) {
            is RegisterAction.OnChangedConfirmPassword -> _state.update {
                it.copy(
                    confirmPassword = action.password,
                    errorMessage = null,
                )
            }

            is RegisterAction.OnChangedEmail -> _state.update {
                it.copy(
                    email = action.email,
                    errorMessage = null,
                )
            }

            is RegisterAction.OnChangedName -> _state.update {
                it.copy(
                    username = action.name,
                    errorMessage = null,
                )
            }

            is RegisterAction.OnChangedPassword -> _state.update {
                it.copy(
                    password = action.password,
                    errorMessage = null,
                )
            }

            RegisterAction.OnClickedRegister -> onSubmitRegister()
            RegisterAction.OnToggleConfirmPasswordVisibility -> _state.update {
                it.copy(
                    isPasswordVisible = !it.isPasswordVisible,
                )
            }

            RegisterAction.OnTogglePasswordVisibility -> _state.update {
                it.copy(
                    isConfirmPasswordVisible = !it.isConfirmPasswordVisible,
                )
            }

            else -> {}
        }
    }

    private fun onSubmitRegister() = with(state.value) {
        if (password != confirmPassword) {
            _state.update {
                it.copy(
                    errorMessage = "Passwords are not matched"
                )
            }
            return@with
        }

        _state.update {
            it.copy(
                isLoading = true,
                errorMessage = null,
            )
        }

        val payload = RegisterPayload(
            username = username,
            email = email,
            password = password,
        )

        doRegister(payload)
    }

    private fun doRegister(payload: RegisterPayload) {
        viewModelScope.launch {
            authRepository.register(payload)
                .onSuccess {
                    emitEvent(RegisterEvent.RegisterSuccess)
                }
                .onError { error ->
                    AppLogger.e("RegisterViewModel", "LoginFailed: $error")
                    _state.update {
                        it.copy(
                            errorMessage = "Failed to register: $error"
                        )
                    }
                }
        }
    }

}
