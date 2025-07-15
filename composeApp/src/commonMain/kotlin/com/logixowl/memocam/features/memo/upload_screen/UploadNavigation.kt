package com.logixowl.memocam.features.memo.upload_screen

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.logixowl.memocam.core.traceNavigation
import kotlinx.serialization.Serializable

/**
 * Created by AP-Jake
 * on 15/07/2025
 */


@Serializable
data class UploadNavigation(
    val imageUri: String,
)

fun NavGraphBuilder.uploadScreen(
    onSuccess: (String) -> Unit,
) {
    composable<UploadNavigation> {
        UploadRoute(
            onSuccess = onSuccess,
        )
    }
}

fun NavController.navigateToUpload(imageUri: String) {
    traceNavigation(UploadNavigation(imageUri)) {
        navigate(it)
    }
}
