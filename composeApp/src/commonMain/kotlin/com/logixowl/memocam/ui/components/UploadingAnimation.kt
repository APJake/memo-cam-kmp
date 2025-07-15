package com.logixowl.memocam.ui.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.repeatable
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.ErrorOutline
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.logixowl.memocam.ui.themes.AppTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.ensureActive
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * Created by AP-Jake
 * on 11/07/2025
 */

enum class UploadingAnimationState {
    Loading, Completed, Failed;
}

@Composable
fun UploadingAnimation(
    state: UploadingAnimationState,
    modifier: Modifier = Modifier,
    contentColor: Color = Color.White,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AnimatedContent(
            targetState = state,
            transitionSpec = {
                scaleIn(
                    initialScale = 0f,
                    animationSpec = tween(500)
                ) togetherWith scaleOut(
                    targetScale = 0f,
                    animationSpec = tween(400, delayMillis = 100)
                )
            },
            modifier = modifier
        ) {
            when (it) {
                UploadingAnimationState.Loading -> {
                    AnimatedCloud(
                        color = contentColor
                    )
                }
                UploadingAnimationState.Failed -> {
                    AnimatedError(
                        color = contentColor
                    )
                }
                else -> {
                    AnimatedComplete(
                        color = contentColor
                    )
                }
            }
        }
        Text(
            text = when (state) {
                UploadingAnimationState.Loading -> {
                    "Uploading..."
                }
                UploadingAnimationState.Failed -> {
                    "Failed to upload. :(\nPlease try again"
                }
                else -> {
                    "Completed"
                }
            },
            fontWeight = FontWeight.Bold,
            color = contentColor,
            modifier = Modifier
                .padding(top = 12.dp)
        )
    }
}

@Composable
private fun AnimatedComplete(
    modifier: Modifier = Modifier,
    size: Dp = 140.dp,
    color: Color = Color.White
) {
    var progress by remember { mutableFloatStateOf(0f) }
    var alphaProgress by remember { mutableFloatStateOf(0f) }
    var rotationProgress by remember { mutableFloatStateOf(0f) }
    val animatedProgress by animateFloatAsState(
        animationSpec = repeatable(
            iterations = 1,
            animation = tween(
                durationMillis = 800,
                easing = FastOutSlowInEasing
            ),
        ),
        label = "progress_animator",
        targetValue = progress,
    )
    val animatedAlpha by animateFloatAsState(
        animationSpec = repeatable(
            iterations = 1,
            animation = tween(
                durationMillis = 800,
            ),
        ),
        label = "progress_alpha_animator",
        targetValue = progress,
    )
    val animatedRotation by animateFloatAsState(
        animationSpec = repeatable(
            iterations = 1,
            animation = tween(
                durationMillis = 600,
                easing = FastOutSlowInEasing
            ),
        ),
        label = "progress_rotation_animator",
        targetValue = rotationProgress,
    )
    LaunchedEffect(Unit) {
        delay(200)
        progress = 1f
        alphaProgress = 1f
        delay(200)
        rotationProgress = 360f
    }
    val circleSize = remember { (size.value * 0.64).dp }
    Box(modifier = modifier.size(140.dp)) {
        Box(
            modifier = Modifier
                .size(circleSize)
                .align(Alignment.BottomCenter)
                .clip(CircleShape)
                .background(color)
        ) {
            Icon(
                imageVector = Icons.Rounded.Check,
                contentDescription = "completed",
                tint = Color(0xFFE883C4),
                modifier = Modifier
                    .align(Alignment.Center)
                    .alpha(animatedAlpha)
                    .rotate(animatedRotation)
            )
        }
        CircularProgressIndicator(
            progress = {
                animatedProgress
            },
            color = Color(0xFFE883C4),
            strokeWidth = 1.dp,
            modifier = Modifier
                .size(circleSize)
                .align(Alignment.BottomCenter)
        )
    }
}

@Composable
private fun AnimatedError(
    modifier: Modifier = Modifier,
    size: Dp = 140.dp,
    color: Color = Color.White
) {
    var progress by remember { mutableFloatStateOf(0f) }
    var alphaProgress by remember { mutableFloatStateOf(0f) }
    var rotationProgress by remember { mutableFloatStateOf(0f) }
    var colorProgress by remember { mutableStateOf(Color(0xFFE883C4)) }
    val animatedProgress by animateFloatAsState(
        animationSpec = repeatable(
            iterations = 1,
            animation = tween(
                durationMillis = 800,
                easing = FastOutSlowInEasing
            ),
        ),
        label = "progress_animator",
        targetValue = progress,
    )
    val animatedAlpha by animateFloatAsState(
        animationSpec = repeatable(
            iterations = 1,
            animation = tween(
                durationMillis = 800,
            ),
        ),
        label = "progress_alpha_animator",
        targetValue = progress,
    )
    val animatedRotation by animateFloatAsState(
        animationSpec = repeatable(
            iterations = 1,
            animation = tween(
                durationMillis = 600,
                easing = FastOutSlowInEasing
            ),
        ),
        label = "progress_rotation_animator",
        targetValue = rotationProgress,
    )
    val animatedColor by animateColorAsState(
        animationSpec = repeatable(
            iterations = 1,
            animation = tween(
                durationMillis = 600,
                easing = FastOutSlowInEasing
            ),
        ),
        label = "progress_color_animator",
        targetValue = colorProgress,
    )
    LaunchedEffect(Unit) {
        delay(200)
        progress = 1f
        alphaProgress = 1f
        delay(200)
        colorProgress = Color(0xFFf53d3d)
        rotationProgress = 360f
    }
    val circleSize = remember { (size.value * 0.64).dp }
    Box(modifier = modifier.size(140.dp)) {
        Box(
            modifier = Modifier
                .size(circleSize)
                .align(Alignment.BottomCenter)
                .clip(CircleShape)
                .background(color)
        ) {
            Icon(
                imageVector = Icons.Rounded.ErrorOutline,
                contentDescription = "error",
                tint = animatedColor,
                modifier = Modifier
                    .align(Alignment.Center)
                    .alpha(animatedAlpha)
                    .rotate(animatedRotation)
            )
        }
        CircularProgressIndicator(
            progress = {
                animatedProgress
            },
            color = animatedColor,
            strokeWidth = 1.dp,
            modifier = Modifier
                .size(circleSize)
                .align(Alignment.BottomCenter)
        )
    }
}

@Composable
private fun AnimatedCloud(
    modifier: Modifier = Modifier,
    size: Dp = 140.dp,
    color: Color = Color.White
) {
    val arrowInfiniteTransition = rememberInfiniteTransition(
        "Arrow infinite transition"
    )
    val arrowProgress by arrowInfiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = -30f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 800,
                easing = FastOutSlowInEasing
            ),
            repeatMode = RepeatMode.Reverse
        ),
        label = "progress animation"
    )
    // Scale animation for squeeze effect
    val arrowScaleProgress by arrowInfiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 0.85f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 800,
                easing = FastOutSlowInEasing
            ),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scale_bounce"
    )
    // Scale animation for squeeze effect
    val cloudScaleProgress by arrowInfiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 0.9f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 800,
                easing = FastOutSlowInEasing
            ),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scale_bounce"
    )

    Box(modifier) {
        com.apjake.composeplayground.components.shapes.CloudShape(
            size = size,
            color = color,
            modifier = Modifier
                .graphicsLayer {
                    scaleX = cloudScaleProgress
                    scaleY = cloudScaleProgress
                }
        )
        Icon(
            imageVector = Icons.Rounded.KeyboardArrowUp,
            contentDescription = "Arrow",
            tint = Color(0xFFFFC8EB),
            modifier = Modifier
                .padding(
                    end = 16.dp,
                    bottom = 14.dp
                )
                .size(55.dp)
                .align(Alignment.BottomEnd)
                .graphicsLayer {
                    translationY = arrowProgress
                    scaleX = arrowScaleProgress
                }
        )
    }
}

@Preview
@Composable
private fun UploadingAnimationPreview() {
    var state by remember { mutableStateOf(UploadingAnimationState.Loading) }
    LaunchedEffect(state) {
        if (state != UploadingAnimationState.Loading) {
            return@LaunchedEffect
        }

        // 3 sec loading
        delay(3000)
        ensureActive()
        state = UploadingAnimationState.Completed
        delay(2000)
        ensureActive()
    }
    AppTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFFFFC8EB), // Soft pink
                            Color(0xFF9CD3F5)  // Light blue
                        ),
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            UploadingAnimation(state)

            Button(
                onClick = {
                    state = UploadingAnimationState.Loading
                },
                modifier = Modifier
                    .padding(40.dp)
                    .align(Alignment.BottomCenter)
            ) {
                Text("Restart")
            }
        }
    }
}
