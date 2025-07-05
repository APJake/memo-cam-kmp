package com.logixowl.memocam.ui.components

/**
 * Created by AP-Jake
 * on 03/07/2025
 */

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.logixowl.memocam.features.memo.take_picture.CameraFacing
import com.logixowl.memocam.features.memo.take_picture.FlashMode

@Composable
expect fun CameraPreviewNative(
    modifier: Modifier = Modifier,
    flashMode: FlashMode,
    cameraFacing: CameraFacing,
    onCameraReady: () -> Unit,
    onImageCaptured: (String) -> Unit,
    onError: (String) -> Unit,
    captureImage: Boolean
)
