package com.logixowl.memocam.features.auth.register

/**
 * Created by AP-Jake
 * on 25/06/2025
 */

sealed interface RegisterAction {
    data class OnChangedName(val name: String) : RegisterAction
    data class OnChangedEmail(val email: String) : RegisterAction
    data class OnChangedPassword(val password: String) : RegisterAction
    data class OnChangedConfirmPassword(val password: String) : RegisterAction
    data object OnTogglePasswordVisibility : RegisterAction
    data object OnToggleConfirmPasswordVisibility : RegisterAction
    data object OnClickedRegister : RegisterAction
    data object OnClickedBackToLogin : RegisterAction
}
