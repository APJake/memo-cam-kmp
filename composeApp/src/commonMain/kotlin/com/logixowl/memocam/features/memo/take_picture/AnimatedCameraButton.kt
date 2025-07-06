package com.logixowl.memocam.features.memo.take_picture

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.logixowl.memocam.ui.themes.AppTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * Created by AP-Jake
 * on 06/07/2025
 */

@Composable
fun AnimatedCameraButton(
    onCameraClick: () -> Unit = {},
    isLoading: Boolean = false,
    modifier: Modifier = Modifier
) {
    var isPressed by remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }

    // Scale animation for button press
    val buttonScale by animateFloatAsState(
        targetValue = if (isPressed) 0.65f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "buttonScale"
    )

    // Rotation animation for icon
    val iconRotation by animateFloatAsState(
        targetValue = if (isPressed) 20f else 0f,
        animationSpec = tween(150),
        label = "iconRotation"
    )

    // Shake animation for loading state (every 3 seconds)
    val infiniteTransition = rememberInfiniteTransition(label = "shake")
    val shakeOffset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 2000
                0f at 0 using LinearEasing
                0f at 2800 using LinearEasing
                -3f at 2850 using LinearEasing
                3f at 2900 using LinearEasing
                -3f at 2950 using LinearEasing
                3f at 2975 using LinearEasing
                0f at 3000 using LinearEasing
            },
            repeatMode = RepeatMode.Restart
        ),
        label = "shakeAnimation"
    )

    // Pulse animation for pressed state
    val pulseScale by animateFloatAsState(
        targetValue = if (isPressed) 1.1f else 1f,
        animationSpec = tween(200),
        label = "pulseScale"
    )

    // Cute gradient colors
    val gradientColors = listOf(
        Color(0xFFFF6B9D), // Pink
        Color(0xFFB06FFF), // Purple
        Color(0xFF6B73FF)  // Indigo
    )

    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = modifier
            .size(80.dp)
            .scale(buttonScale)
            .clip(CircleShape)
            .background(
                brush = Brush.linearGradient(gradientColors)
            )
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                enabled = !isLoading
            ) {
                if (!isLoading) {
                    isPressed = true
                    // Reset pressed state after animation
                    coroutineScope.launch {
                        delay(200)
                        onCameraClick()
                        isPressed = false
                    }
                }
            },
        contentAlignment = Alignment.Center
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                color = Color.White,
                strokeWidth = 2.dp,
                modifier = Modifier
                    .size(14.dp)
            )
        } else {
            Icon(
                imageVector = Icons.Default.CameraAlt,
                contentDescription = "Take Photo",
                modifier = Modifier
                    .size(32.dp)
                    .graphicsLayer {
                        rotationZ = iconRotation
                        scaleX = pulseScale
                        scaleY = pulseScale
                        translationX = shakeOffset
                    },
                tint = Color.White
            )
        }
    }
}

@Preview
@Composable
fun CameraButtonPreview() {
    AppTheme {
        Box(Modifier.fillMaxSize()) {
            Column(
                Modifier
                    .align(Alignment.BottomCenter)
                    .padding(25.dp)
            ) {
                AnimatedCameraButton(
                    onCameraClick = {},
                    isLoading = true,
                )
                AnimatedCameraButton(
                    onCameraClick = {},
                    isLoading = false,
                )
            }
        }
    }
}
