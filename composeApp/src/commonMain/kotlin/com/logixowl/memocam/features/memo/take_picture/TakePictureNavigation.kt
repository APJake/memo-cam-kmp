package com.logixowl.memocam.features.memo.take_picture

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
data class TakePictureNavigation(
    val folderId: String,
)

fun NavController.navigateToTakePicture(folderId: String) {
    traceNavigation(TakePictureNavigation(folderId)) {
        navigate(it)
    }
}

fun NavGraphBuilder.takePictureScreen(
    onNavigateBack: () -> Unit,
    onSuccessTaken: (String, String) -> Unit,
) {
    composable<TakePictureNavigation> {
        TakePictureRoute(
            onNavigateBack = onNavigateBack,
            onSuccessTaken = onSuccessTaken,
        )
    }
}
