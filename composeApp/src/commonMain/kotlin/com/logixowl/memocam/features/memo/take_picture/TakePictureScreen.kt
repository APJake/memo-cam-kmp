package com.logixowl.memocam.features.memo.take_picture


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Cameraswitch
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * Created by AP-Jake
 * on 25/06/2025
 */

@Composable
fun TakePictureScreen(
    uiState: TakePictureUiState,
    onAction: (TakePictureAction) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        // Camera Preview Background (Placeholder)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            Color(0xFF2D3748),
                            Color(0xFF1A202C)
                        )
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "📷 Camera Preview",
                color = Color.White.copy(alpha = 0.7f),
                fontSize = 18.sp
            )
        }

        // Overlay Image (if available)
        uiState.overlayImageUrl?.let { imageUrl ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .alpha(uiState.overlayOpacity)
                    .background(
                        Brush.linearGradient(
                            colors = listOf(
                                Color(0xFFE91E63).copy(alpha = 0.3f),
                                Color(0xFF9C27B0).copy(alpha = 0.3f),
                                Color(0xFF3F51B5).copy(alpha = 0.3f)
                            )
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "🖼️ Overlay Image",
                    color = Color.White,
                    fontSize = 16.sp
                )
            }
        }

        // Top Controls
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .statusBarsPadding(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Back Button
            IconButton(
                onClick = { onAction(TakePictureAction.OnBackClicked) },
                modifier = Modifier
                    .size(48.dp)
                    .background(
                        Color.Black.copy(alpha = 0.4f),
                        CircleShape
                    )
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }

            // Switch Camera Button
            IconButton(
                onClick = { onAction(TakePictureAction.OnSwitchCamera) },
                modifier = Modifier
                    .size(48.dp)
                    .background(
                        Color.Black.copy(alpha = 0.4f),
                        CircleShape
                    )
            ) {
                Icon(
                    imageVector = Icons.Default.Cameraswitch,
                    contentDescription = "Switch Camera",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }
        }

        // Bottom Controls
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(24.dp)
                .navigationBarsPadding(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Opacity Slider (only show if overlay image exists)
            uiState.overlayImageUrl?.let {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 32.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.Black.copy(alpha = 0.7f)
                    ),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "✨ Overlay Opacity",
                            color = Color.White,
                            fontSize = 14.sp,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Text(
                                text = "👻",
                                fontSize = 16.sp
                            )

                            Slider(
                                value = uiState.overlayOpacity,
                                onValueChange = { opacity ->
                                    onAction(TakePictureAction.OnOpacityChanged(opacity))
                                },
                                valueRange = 0f..1f,
                                modifier = Modifier.weight(1f),
                                colors = SliderDefaults.colors(
                                    thumbColor = Color(0xFFFF6B9D),
                                    activeTrackColor = Color(0xFFFF6B9D),
                                    inactiveTrackColor = Color.White.copy(alpha = 0.3f)
                                )
                            )

                            Text(
                                text = "🎨",
                                fontSize = 16.sp
                            )
                        }

                        Text(
                            text = "${(uiState.overlayOpacity * 100).toInt()}%",
                            color = Color(0xFFFF6B9D),
                            fontSize = 12.sp,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }
            }

            // Take Picture Button
            Button(
                onClick = { onAction(TakePictureAction.OnTakePicture) },
                modifier = Modifier
                    .size(80.dp),
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White
                ),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 8.dp,
                    pressedElevation = 12.dp
                ),
                enabled = !uiState.isLoading
            ) {
                if (uiState.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(32.dp),
                        color = Color(0xFFFF6B9D),
                        strokeWidth = 3.dp
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.CameraAlt,
                        contentDescription = "Take Picture",
                        tint = Color(0xFFFF6B9D),
                        modifier = Modifier.size(32.dp)
                    )
                }
            }
        }

        // Error Message
        uiState.errorMessage?.let { error ->
            Card(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(16.dp)
                    .padding(top = 80.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFFF5252).copy(alpha = 0.9f)
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "❌ $error",
                    color = Color.White,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(12.dp)
                )
            }
        }

        // Camera Mode Indicator
        Card(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 100.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.Black.copy(alpha = 0.6f)
            ),
            shape = RoundedCornerShape(20.dp)
        ) {
            Text(
                text = if (uiState.isFrontCamera) "🤳 Front Camera" else "📸 Back Camera",
                color = Color.White,
                fontSize = 12.sp,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
            )
        }
    }
}

@Preview
@Composable
private fun PreviewLoginScreen() {
    MaterialTheme {
        TakePictureScreen(
            uiState = TakePictureUiState(
                overlayImageUrl = "https://media.istockphoto.com/id/1486674561/photo/young-woman-taking-a-selfie.jpg?s=612x612&w=0&k=20&c=ZbH8PakbBL8UAFhvsFSRAWXJakqz92UgnFFxRC3ucj0=",
            ),
            onAction = {},
        )
    }
}
