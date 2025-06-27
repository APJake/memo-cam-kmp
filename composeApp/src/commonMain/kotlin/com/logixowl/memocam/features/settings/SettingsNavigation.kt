package com.logixowl.memocam.features.settings

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.logixowl.memocam.core.traceNavigation
import kotlinx.serialization.Serializable

/**
 * Created by AP-Jake
 * on 28/06/2025
 */

@Serializable
data object SettingsNavigation

fun NavController.navigateToSettings() {
    traceNavigation(SettingsNavigation) {
        navigate(it)
    }
}

fun NavGraphBuilder.settingsScreen(
    onNavigateBack: () -> Unit,
    onNavigateChangePassword: () -> Unit,
    onLogoutSuccess: () -> Unit,
) {
    composable<SettingsNavigation> {
        SettingsRoute(
            onNavigateBack = onNavigateBack,
            onNavigateChangePassword = onNavigateChangePassword,
            onLogoutSuccess = onLogoutSuccess,
        )
    }
}
