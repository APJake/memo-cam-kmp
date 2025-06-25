package com.logixowl.memocam.features.auth.register

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
    val errorMessage: String? = null
)
