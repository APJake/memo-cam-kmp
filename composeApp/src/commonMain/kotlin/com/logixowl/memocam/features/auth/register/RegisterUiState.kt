package com.logixowl.memocam.features.auth.register

import org.jetbrains.compose.resources.StringResource

/**
 * Created by AP-Jake
 * on 25/06/2025
 */

data class RegisterUiState(
    val username: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val isLoading: Boolean = false,
    val isPasswordVisible: Boolean = false,
    val isConfirmPasswordVisible: Boolean = false,
    val errorMessage: StringResource? = null,
    val usernameError: StringResource? = null,
    val emailError: StringResource? = null,
    val passwordError: StringResource? = null,
    val confirmPasswordError: StringResource? = null,
) {
    val enabledRegister: Boolean
        get() = !isLoading && username.isNotBlank()
                && email.isNotBlank() && password.isNotBlank()
                && confirmPassword.isNotBlank()
}
