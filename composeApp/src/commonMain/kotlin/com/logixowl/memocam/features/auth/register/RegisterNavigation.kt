package com.logixowl.memocam.features.auth.register

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.logixowl.memocam.core.traceNavigation
import kotlinx.serialization.Serializable

/**
 * Created by AP-Jake
 * on 25/06/2025
 */

@Serializable
data object RegisterNavigation

fun NavController.navigateToRegister() {
    val navigation = RegisterNavigation
    traceNavigation("Navigation: $navigation") {
        navigate(navigation)
    }
}

fun NavGraphBuilder.registerScreen(
    onClickLogin: () -> Unit,
    onNavigateToDashboard: () -> Unit,
) {
    composable<RegisterNavigation> {
        RegisterRoute(
            onClickLogin = onClickLogin,
            onNavigateToDashboard = onNavigateToDashboard,
        )
    }
}
