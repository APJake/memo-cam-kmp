package com.logixowl.memocam.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.logixowl.memocam.features.memo.take_picture.CameraFacing
import com.logixowl.memocam.features.memo.take_picture.FlashMode

@Composable
actual fun CameraPreviewNative(
    modifier: Modifier,
    flashMode: FlashMode,
    cameraFacing: CameraFacing,
    onCameraReady: () -> Unit,
    onImageCaptured: (String) -> Unit,
    onError: (String) -> Unit,
    captureImage: Boolean
) {
    // iOS implementation placeholder
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "iOS Camera Preview\n(Implementation Pending)",
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
