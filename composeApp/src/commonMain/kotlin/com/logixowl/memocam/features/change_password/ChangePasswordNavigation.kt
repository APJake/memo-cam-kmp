package com.logixowl.memocam.features.change_password

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.logixowl.memocam.core.traceNavigation
import kotlinx.serialization.Serializable

/**
 * Created by AP-Jake
 * on 08/07/2025
 */

@Serializable
data object ChangePasswordNavigation

fun NavGraphBuilder.changePasswordScreen(
    onNavigateBack: () -> Unit,
) {
    composable<ChangePasswordNavigation> {
        ChangePasswordRoute(
            onNavigateBack = onNavigateBack,
        )
    }
}

fun NavController.navigateToChangePassword() {
    traceNavigation(ChangePasswordNavigation) {
        navigate(it)
    }
}
