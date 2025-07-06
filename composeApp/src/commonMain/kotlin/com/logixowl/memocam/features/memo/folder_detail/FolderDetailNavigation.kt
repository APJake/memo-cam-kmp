package com.logixowl.memocam.features.memo.folder_detail

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.logixowl.memocam.core.traceNavigation
import kotlinx.serialization.Serializable

/**
 * Created by AP-Jake
 * on 05/07/2025
 */

@Serializable
data object FolderDetailNavigation

fun NavGraphBuilder.folderDetailScreen(
    onClickedImage: (String) -> Unit,
    onCaptureImage: () -> Unit,
    onBackPressed: () -> Unit,
) {
    composable<FolderDetailNavigation> {
        FolderDetailRoute(
            onClickedImage = onClickedImage,
            onCaptureImage = onCaptureImage,
            onBackPressed = onBackPressed,
        )
    }
}

fun NavController.navigateToFolderDetail() {
    traceNavigation(FolderDetailNavigation) {
        navigate(it)
    }
}
