package com.logixowl.memocam.ui.navigations

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.logixowl.memocam.features.auth.login.LoginNavigation
import com.logixowl.memocam.features.auth.login.loginScreen
import com.logixowl.memocam.features.auth.login.navigateToLogin
import com.logixowl.memocam.features.auth.register.navigateToRegister
import com.logixowl.memocam.features.auth.register.registerScreen
import com.logixowl.memocam.features.memo.dashboard.dashboardScreen
import com.logixowl.memocam.features.memo.dashboard.navigateToDashboard
import com.logixowl.memocam.features.settings.navigateToSettings
import com.logixowl.memocam.features.settings.settingsScreen
import com.logixowl.memocam.features.splash.splashScreen
import com.logixowl.memocam.ui.transition.AppTransition
import com.logixowl.memocam.ui.transition.DefaultTransition

/**
 * Created by AP-Jake
 * on 26/06/2025
 */


@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: Any = LoginNavigation,
    transition: AppTransition = DefaultTransition,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
        enterTransition = transition.enterTransition,
        exitTransition = transition.exitTransition,
        popEnterTransition = transition.popEnterTransition,
        popExitTransition = transition.popExitTransition,
    ) {
        // splash
        splashScreen(
            navController = navController,
            onNavigateDashboard = navController::navigateToDashboard,
            onNavigateLogin = navController::navigateToLogin
        )

        // authentication
        loginScreen(
            onClickRegister = navController::navigateToRegister,
            onNavigateToDashboard = navController::navigateToDashboard,
        )
        registerScreen(
            onClickLogin = navController::navigateToLogin,
            onNavigateToDashboard = navController::navigateToDashboard,
        )

        // settings
        settingsScreen(
            onNavigateBack = navController::popBackStack,
            onNavigateChangePassword = {},
            onLogoutSuccess = navController::navigateToLogin
        )

        // memo
        dashboardScreen(
            onNavigateCreateFolder = {},
            onNavigateSettings = navController::navigateToSettings,
            onNavigateFolder = {},
        )
    }
}
