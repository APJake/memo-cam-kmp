package com.logixowl.memocam.features.auth.login

import androidx.lifecycle.viewModelScope
import com.logixowl.memocam.core.AppLogger
import com.logixowl.memocam.core.BaseViewModel
import com.logixowl.memocam.core.onError
import com.logixowl.memocam.core.onSuccess
import com.logixowl.memocam.domain.model.payload.LoginPayload
import com.logixowl.memocam.domain.repository.AuthRepository
import com.logixowl.memocam.domain.repository.PrefsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * Created by AP-Jake
 * on 25/06/2025
 */

class LoginViewModel(
    private val prefsRepository: PrefsRepository,
    private val authRepository: AuthRepository,
) : BaseViewModel<LoginEvent>() {

    private val _state = MutableStateFlow(LoginUiState())
    val state = _state
        .onStart {
            preloadData()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _state.value
        )

    fun onAction(action: LoginAction) {
        when (action) {
            is LoginAction.OnChangedEmail -> {
                _state.update {
                    it.copy(
                        email = action.value,
                        errorMessage = null,
                    )
                }
            }

            is LoginAction.OnChangedPassword -> {
                _state.update {
                    it.copy(
                        password = action.value,
                        errorMessage = null,
                    )
                }
            }

            LoginAction.OnToggledPasswordVisibility -> {
                _state.update {
                    it.copy(
                        isPasswordVisible = !it.isPasswordVisible,
                        errorMessage = null,
                    )
                }
            }

            LoginAction.OnClickLogin -> {
                onSubmitLogin()
            }

            else -> {}
        }
    }

    private fun onSubmitLogin() = with(state.value) {
        if (email.length < 10) {
            _state.update {
                it.copy(
                    errorMessage = "Invalid email"
                )
            }
            return@with
        }

        if (password.isBlank()) {
            _state.update {
                it.copy(
                    errorMessage = "Invalid password"
                )
            }
            return@with
        }

        _state.update {
            it.copy(
                errorMessage = null,
                isLoading = true,
            )
        }

        makeLogin(LoginPayload(email, password))
    }

    private fun makeLogin(payload: LoginPayload) {
        viewModelScope.launch {
            authRepository.login(payload)
                .onSuccess {
                    emitEvent(LoginEvent.LoginSuccess)
                }
                .onError { error ->
                    AppLogger.e("LoginViewModel", "LoginFailed: $error")
                    _state.update {
                        it.copy(
                            errorMessage = "Failed to login"
                        )
                    }
                }

            _state.update {
                it.copy(
                    isLoading = false
                )
            }
        }
    }

    private fun preloadData() {
        viewModelScope.launch {
            val previousEmail = prefsRepository
                .user.firstOrNull()
                ?.email?.takeIf { it.isNotBlank() }
            previousEmail?.let { email ->
                _state.update {
                    it.copy(
                        email = email
                    )
                }
            }
        }
    }

}
