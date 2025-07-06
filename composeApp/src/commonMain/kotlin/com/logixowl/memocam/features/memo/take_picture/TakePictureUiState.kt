package com.logixowl.memocam.features.memo.take_picture

import org.jetbrains.compose.resources.StringResource

/**
 * Created by AP-Jake
 * on 03/07/2025
 */

data class TakePictureUiState(
    val isPermissionLoading: Boolean = true,
    val isPermissionGranted: Boolean = false,
    val isPermissionRequested: Boolean = false,
    val isLoading: Boolean = false,
    val capturedImagePath: String? = null,
    val errorMessage: StringResource? = null,
    val isCameraReady: Boolean = false,
    val flashMode: FlashMode = FlashMode.OFF,
    val cameraFacing: CameraFacing = CameraFacing.BACK
)

enum class FlashMode {
    OFF, ON, AUTO;

    fun toggleNext(): FlashMode {
        return when (this) {
            OFF -> ON
            ON -> AUTO
            AUTO -> OFF
        }
    }
}

enum class CameraFacing {
    FRONT, BACK;

    fun toggleNext(): CameraFacing {
        return when (this) {
            FRONT -> BACK
            BACK -> FRONT
        }
    }
}
