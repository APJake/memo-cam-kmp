package com.logixowl.memocam.features.auth.register

import androidx.lifecycle.viewModelScope
import com.logixowl.memocam.core.AppLogger
import com.logixowl.memocam.core.BaseViewModel
import com.logixowl.memocam.core.onError
import com.logixowl.memocam.core.onSuccess
import com.logixowl.memocam.domain.model.payload.RegisterPayload
import com.logixowl.memocam.domain.repository.AuthRepository
import com.logixowl.memocam.domain.validation.auth.RegisterValidation
import com.logixowl.memocam.ui.error.asStringResource
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
    private val registerValidation: RegisterValidation,
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
                    confirmPasswordError = null,
                )
            }

            is RegisterAction.OnChangedEmail -> _state.update {
                it.copy(
                    email = action.email,
                    errorMessage = null,
                    emailError = null,
                )
            }

            is RegisterAction.OnChangedName -> _state.update {
                it.copy(
                    username = action.name,
                    errorMessage = null,
                    usernameError = null,
                )
            }

            is RegisterAction.OnChangedPassword -> _state.update {
                it.copy(
                    password = action.password,
                    errorMessage = null,
                    passwordError = null,
                )
            }

            RegisterAction.OnClickedRegister -> onSubmitRegister()
            RegisterAction.OnToggleConfirmPasswordVisibility -> _state.update {
                it.copy(
                    isConfirmPasswordVisible = !it.isConfirmPasswordVisible,
                )
            }

            RegisterAction.OnTogglePasswordVisibility -> _state.update {
                it.copy(
                    isPasswordVisible = !it.isPasswordVisible,
                )
            }

            else -> {}
        }
    }

    private fun onSubmitRegister() = with(state.value) {
        val payload = RegisterPayload(
            username = username,
            email = email,
            password = password,
            confirmPassword = confirmPassword
        )

        registerValidation.invoke(payload)
            .onSuccess { doRegister(payload) }
            .onError { error ->
                val errorResource = error.asStringResource
                when (error) {
                    is RegisterValidation.Error.Username -> {
                        _state.update {
                            it.copy(
                                usernameError = errorResource
                            )
                        }
                    }

                    is RegisterValidation.Error.Email -> {
                        _state.update {
                            it.copy(
                                emailError = errorResource
                            )
                        }
                    }

                    is RegisterValidation.Error.Password1 -> {
                        _state.update {
                            it.copy(
                                passwordError = errorResource
                            )
                        }
                    }

                    is RegisterValidation.Error.Password2 -> {
                        _state.update {
                            it.copy(
                                confirmPasswordError = errorResource
                            )
                        }
                    }
                }
            }
    }

    private fun doRegister(payload: RegisterPayload) {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true,
                    errorMessage = null,
                )
            }
            authRepository.register(payload)
                .onSuccess {
                    emitEvent(RegisterEvent.RegisterSuccess)
                }
                .onError { error ->
                    AppLogger.e("RegisterViewModel", "LoginFailed: $error")
                    _state.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = error.asStringResource
                        )
                    }
                }
            _state.update {
                it.copy(
                    isLoading = false,
                    errorMessage = null,
                )
            }
        }
    }

}
