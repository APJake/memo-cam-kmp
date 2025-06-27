package com.logixowl.memocam.features.memo.dashboard

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
data object DashboardNavigation


fun NavController.navigateToDashboard(
    isInclusive: Boolean = true,
    isStartDestination: Boolean = true,
) {
    val navigation = DashboardNavigation
    traceNavigation("Navigation: $navigation") {
        navigate(navigation) {
            if (isInclusive) {
                val destinationId = if (isStartDestination) {
                    this@navigateToDashboard.graph.id
                } else {
                    this@navigateToDashboard.graph.startDestinationId
                }
                popUpTo(destinationId) {
                    inclusive = true
                }
            }
        }
    }
}

fun NavGraphBuilder.dashboardScreen(
    onNavigateCreateFolder: () -> Unit,
    onNavigateSettings: () -> Unit,
    onNavigateFolder: (String) -> Unit,
) {
    composable<DashboardNavigation> {
        DashboardRoute(
            onNavigateCreateFolder = onNavigateCreateFolder,
            onNavigateSettings = onNavigateSettings,
            onNavigateFolder = onNavigateFolder,
        )
    }
}

