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
import com.logixowl.memocam.features.memo.create_folder.createFolderScreen
import com.logixowl.memocam.features.memo.create_folder.navigateToCreateFolder
import com.logixowl.memocam.features.memo.dashboard.dashboardScreen
import com.logixowl.memocam.features.memo.dashboard.navigateToDashboard
import com.logixowl.memocam.features.memo.folder_detail.folderDetailScreen
import com.logixowl.memocam.features.memo.folder_detail.navigateToFolderDetail
import com.logixowl.memocam.features.memo.image_detail.imageDetailScreen
import com.logixowl.memocam.features.memo.image_detail.navigateToImageDetail
import com.logixowl.memocam.features.memo.image_detail.navigateToImageDetailFromUpload
import com.logixowl.memocam.features.memo.take_picture.navigateToTakePicture
import com.logixowl.memocam.features.memo.take_picture.takePictureScreen
import com.logixowl.memocam.features.memo.upload_screen.navigateToUpload
import com.logixowl.memocam.features.memo.upload_screen.uploadScreen
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
            onNavigateCreateFolder = navController::navigateToCreateFolder,
            onNavigateSettings = navController::navigateToSettings,
            onNavigateFolder = navController::navigateToFolderDetail,
        )

        // folders
        createFolderScreen(
            onNavigateBack = navController::popBackStack,
            onSuccessCreated = {
                navController.popBackStack()
            },
        )

        folderDetailScreen(
            onClickedImage = navController::navigateToImageDetail,
            onCaptureImage = navController::navigateToTakePicture,
            onBackPressed = navController::popBackStack
        )

        // images
        takePictureScreen(
            onNavigateBack = navController::popBackStack,
            onSuccessTaken = navController::navigateToUpload,
        )

        imageDetailScreen(
            onClickedBack = navController::popBackStack
        )

        uploadScreen(
            onSuccess = navController::navigateToImageDetailFromUpload
        )
    }
}
