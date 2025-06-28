package com.logixowl.memocam.features.memo.create_folder

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
data object CreateFolderNavigation

fun NavController.navigateToCreateFolder() {
    traceNavigation(CreateFolderNavigation) {
        navigate(it)
    }
}

fun NavGraphBuilder.createFolderScreen(
    onNavigateBack: () -> Unit,
    onSuccessCreated: (String) -> Unit,
) {
    composable<CreateFolderNavigation> {
        CreateFolderRoute(
            onNavigateBack = onNavigateBack,
            onSuccessCreated = onSuccessCreated,
        )
    }
}
