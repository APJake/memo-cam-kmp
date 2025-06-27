package com.logixowl.memocam.features.settings

/**
 * Created by AP-Jake
 * on 28/06/2025
 */

sealed interface SettingsAction {
    data object OnClickedBack: SettingsAction
    data object OnClickedLogout: SettingsAction
    data object OnClickedChangePassword: SettingsAction
}
