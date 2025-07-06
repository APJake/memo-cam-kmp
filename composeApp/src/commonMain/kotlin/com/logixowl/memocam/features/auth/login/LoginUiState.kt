package com.logixowl.memocam.features.auth.login

import org.jetbrains.compose.resources.StringResource

/**
 * Created by AP-Jake
 * on 25/06/2025
 */

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val isPasswordVisible: Boolean = false,
    val errorMessage: StringResource? = null,
    val emailError: StringResource? = null,
    val passwordError: StringResource? = null,
) {
    val enabledLogin: Boolean
        get() = !isLoading && email.isNotBlank() && password.isNotBlank()
}
