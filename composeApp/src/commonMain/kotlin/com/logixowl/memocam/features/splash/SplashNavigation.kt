package com.logixowl.memocam.features.splash

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.logixowl.memocam.ui.utils.sharedKoinViewModel
import kotlinx.serialization.Serializable

/**
 * Created by AP-Jake
 * on 27/06/2025
 */

@Serializable
data object SplashNavigation

fun NavGraphBuilder.splashScreen(
    navController: NavController,
    onNavigateDashboard: () -> Unit,
    onNavigateLogin: () -> Unit,
) {
    composable<SplashNavigation> {
        SplashRoute(
            viewModel = it.sharedKoinViewModel(navController),
            onNavigateDashboard = onNavigateDashboard,
            onNavigateLogin = onNavigateLogin,
        )
    }
}
