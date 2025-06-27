package com.logixowl.memocam.features.settings

/**
 * Created by AP-Jake
 * on 28/06/2025
 */

sealed interface SettingsEvent {
    data class Error(val message: String) : SettingsEvent
    data object SuccessLogout : SettingsEvent
}
