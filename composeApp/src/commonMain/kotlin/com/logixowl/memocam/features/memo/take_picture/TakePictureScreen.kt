package com.logixowl.memocam.features.memo.take_picture

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraRear
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.FlashAuto
import androidx.compose.material.icons.filled.FlashOff
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.logixowl.memocam.ui.components.CameraPreviewNative
import com.logixowl.memocam.ui.utils.LaunchedEventHandler
import dev.icerock.moko.permissions.compose.BindEffect
import dev.icerock.moko.permissions.compose.rememberPermissionsControllerFactory
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

/**
 * Created by AP-Jake
 * on 03/07/2025
 */

@Composable
fun TakePictureRoute(
    onNavigateBack: () -> Unit,
    onSuccessTaken: (String, String) -> Unit,
) {
    val factory = rememberPermissionsControllerFactory()
    val controller = remember(factory) { factory.createPermissionsController() }

    val viewModel: TakePictureViewModel = koinViewModel {
        parametersOf(controller)
    }

    val uiState by viewModel.state.collectAsState()

    LaunchedEventHandler(viewModel.event) { event ->
        when (event) {
            is TakePictureEvent.OnSuccessImageCaptured ->
                onSuccessTaken(event.folderId, event.imagePath)
        }
    }

    if (uiState.isPermissionLoading) {
        TakePicturePermissionLoadingScreen()
    } else {
        TakePictureScreen(
            uiState = uiState,
            onAction = { action ->
                when (action) {
                    is TakePictureAction.OnNavigateBack -> {
                        onNavigateBack.invoke()
                    }

                    else -> viewModel.onAction(action)
                }
            }
        )
    }

    BindEffect(viewModel.permissionsController)
}

@Composable
private fun TakePicturePermissionLoadingScreen(
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxSize()) {
        Text(
            text = "Checking permissions...",
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun TakePictureScreen(
    uiState: TakePictureUiState,
    onAction: (TakePictureAction) -> Unit,
    modifier: Modifier = Modifier
) {
    var shouldCaptureImage by remember { mutableStateOf(false) }

    Box(modifier = modifier.fillMaxSize()) {
        when {
            !uiState.isPermissionGranted -> {
                CameraPermissionContent(
                    onRequestPermission = {
                        onAction.invoke(TakePictureAction.OnPermissionRequested)
                    }
                )
            }

            uiState.capturedImagePath != null -> {
                CapturedImagePreview(
                    imagePath = uiState.capturedImagePath,
                    onRetake = {
                        onAction.invoke(TakePictureAction.OnRetakePhoto)
                    },
                    onKeep = { /* Handle keep image */ }
                )
            }

            else -> {
                CameraPreviewContent(
                    uiState = uiState,
                    onAction = onAction,
                    shouldCaptureImage = shouldCaptureImage,
                    onCaptureTrigger = { shouldCaptureImage = !shouldCaptureImage }
                )
            }
        }

        // Error message overlay
        uiState.errorMessage?.let { error ->
            ErrorOverlay(
                message = stringResource(error),
                onDismiss = {
                    onAction.invoke(TakePictureAction.OnClearError)
                }
            )
        }
    }
}

@Composable
private fun CameraPermissionContent(
    onRequestPermission: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Default.PhotoCamera,
            contentDescription = "Camera",
            modifier = Modifier.size(80.dp),
            tint = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Camera Permission Required",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "This app needs camera permission to take photos",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = onRequestPermission,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Grant Camera Permission")
        }
    }
}

@Composable
private fun CameraPreviewContent(
    shouldCaptureImage: Boolean,
    uiState: TakePictureUiState,
    onAction: (TakePictureAction) -> Unit,
    onCaptureTrigger: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        // Camera preview
        CameraPreviewNative(
            modifier = Modifier.fillMaxSize(),
            flashMode = uiState.flashMode,
            cameraFacing = uiState.cameraFacing,
            onCameraReady = {
                onAction.invoke(TakePictureAction.OnCameraReady)
            },
            onImageCaptured = {
                onAction.invoke(TakePictureAction.OnImageCaptured(it))
            },
            onError = {
                onAction.invoke(TakePictureAction.OnCameraError(it))
            },
            captureImage = shouldCaptureImage
        )

        // Top controls
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .align(Alignment.TopCenter),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Flash toggle
            IconButton(
                onClick = {
                    onAction.invoke(TakePictureAction.OnFlashToggle)
                },
                modifier = Modifier
                    .background(
                        Color.Black.copy(alpha = 0.5f),
                        CircleShape
                    )
            ) {
                Icon(
                    imageVector = when (uiState.flashMode) {
                        FlashMode.OFF -> Icons.Default.FlashOff
                        FlashMode.ON -> Icons.Default.FlashOn
                        FlashMode.AUTO -> Icons.Default.FlashAuto
                    },
                    contentDescription = "Flash",
                    tint = Color.White
                )
            }

            // Camera switch
            IconButton(
                onClick = {
                    onAction.invoke(TakePictureAction.OnCameraSwitch)
                },
                modifier = Modifier
                    .background(
                        Color.Black.copy(alpha = 0.5f),
                        CircleShape
                    )
            ) {
                Icon(
                    imageVector = Icons.Default.CameraRear,
                    contentDescription = "Switch Camera",
                    tint = Color.White
                )
            }
        }

        // Bottom controls
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp)
                .align(Alignment.BottomCenter),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Gallery button
            IconButton(
                onClick = { /* Handle gallery */ },
                modifier = Modifier
                    .size(56.dp)
                    .background(
                        Color.Black.copy(alpha = 0.5f),
                        CircleShape
                    )
            ) {
                Icon(
                    imageVector = Icons.Default.PhotoLibrary,
                    contentDescription = "Gallery",
                    tint = Color.White
                )
            }

            // Capture button
            IconButton(
                onClick = {
                    onAction.invoke(TakePictureAction.OnTakePicture)
                    onCaptureTrigger()
                },
                modifier = Modifier
                    .size(80.dp)
                    .background(Color.White, CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Default.PhotoCamera,
                    contentDescription = "Take Picture",
                    modifier = Modifier.size(40.dp),
                    tint = Color.Black
                )
            }

            // Settings button
            IconButton(
                onClick = { /* Handle settings */ },
                modifier = Modifier
                    .size(56.dp)
                    .background(
                        Color.Black.copy(alpha = 0.5f),
                        CircleShape
                    )
            ) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "Settings",
                    tint = Color.White
                )
            }
        }
    }
}

@Composable
private fun CapturedImagePreview(
    imagePath: String,
    onRetake: () -> Unit,
    onKeep: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        // Image preview would go here
        Text(
            text = "Image Preview\n$imagePath",
            color = Color.White,
            modifier = Modifier.align(Alignment.Center)
        )

        // Bottom controls
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp)
                .align(Alignment.BottomCenter),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = onRetake,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Red.copy(alpha = 0.8f)
                )
            ) {
                Text("Retake")
            }

            Button(
                onClick = onKeep,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Green.copy(alpha = 0.8f)
                )
            ) {
                Text("Keep")
            }
        }
    }
}

@Composable
private fun ErrorOverlay(
    message: String,
    onDismiss: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.7f)),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.errorContainer
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Default.Error,
                    contentDescription = "Error",
                    tint = MaterialTheme.colorScheme.error
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = message,
                    color = MaterialTheme.colorScheme.onErrorContainer,
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(onClick = onDismiss) {
                    Text("OK")
                }
            }
        }
    }
}
