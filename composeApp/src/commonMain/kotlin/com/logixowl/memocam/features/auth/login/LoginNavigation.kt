package com.logixowl.memocam.features.auth.login

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
data object LoginNavigation

fun NavController.navigateToLogin(
    isInclusive: Boolean = true,
    isStartDestination: Boolean = true,
) {
    val navigation = LoginNavigation
    traceNavigation("Navigation: $navigation") {
        navigate(navigation) {
            if (isInclusive) {
                val destinationId = if (isStartDestination) {
                    this@navigateToLogin.graph.id
                } else {
                    this@navigateToLogin.graph.startDestinationId
                }
                popUpTo(destinationId) {
                    inclusive = true
                }
            }
        }
    }
}

fun NavGraphBuilder.loginScreen(
    onClickRegister: () -> Unit,
    onNavigateToDashboard: () -> Unit,
) {
    composable<LoginNavigation> {
        LoginRoute(
            onClickRegister = onClickRegister,
            onNavigateToDashboard = onNavigateToDashboard,
        )
    }
}
