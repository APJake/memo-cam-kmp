package com.logixowl.memocam.features.memo.image_detail

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
data class ImageDetailNavigation(
    val imageId: String,
)

fun NavGraphBuilder.imageDetailScreen(
    onClickedBack: () -> Unit,
) {
    composable<ImageDetailNavigation> {
        ImageDetailRoute(
            onClickedBack = onClickedBack,
        )
    }
}

fun NavController.navigateToImageDetail(imageId: String) {
    traceNavigation(ImageDetailNavigation(imageId)) {
        navigate(it)
    }
}
