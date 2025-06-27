package com.logixowl.memocam.features.splash

import androidx.compose.animation.core.FastOutSlowInEasing
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
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.logixowl.memocam.app.AppViewModel
import kotlinx.coroutines.delay

/**
 * Created by AP-Jake
 * on 27/06/2025
 */

@Composable
fun SplashRoute(
    viewModel: AppViewModel,
    onNavigateDashboard: () -> Unit,
    onNavigateLogin: () -> Unit,
) {
    val state by viewModel.state.collectAsState()
    SplashScreen(
        isLoading = state.isLoading,
        onSplashFinished = {
            if (state.isLoggedIn) {
                onNavigateDashboard.invoke()
            } else {
                onNavigateLogin.invoke()
            }
        },
    )
}

@Composable
fun SplashScreen(
    isLoading: Boolean,
    onSplashFinished: () -> Unit = {}
) {
    val infiniteTransition = rememberInfiniteTransition(label = "splash_transition")

    // Camera icon bounce animation
    val bounceOffset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = -20f,
        animationSpec = infiniteRepeatable(
            animation = tween(800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "bounce"
    )

    // Pulse animation for sparkles
    val sparkleAlpha by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "sparkle_pulse"
    )

    // Gradient animation
    val gradientOffset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "gradient_animation"
    )

    // Scale animation for entrance
    val scale by animateFloatAsState(
        targetValue = 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "scale_animation"
    )

    // Fade in animation
    val alpha by animateFloatAsState(
        targetValue = 1f,
        animationSpec = tween(1000),
        label = "fade_in"
    )

    // Make it show animation at least 3s
    LaunchedEffect(isLoading) {
        if (!isLoading) {
            onSplashFinished()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.radialGradient(
                    colors = listOf(
                        Color(0xFFFFE0F4), // Soft pink
                        Color(0xFFE8F4FD), // Light blue
                        Color(0xFFF0E6FF)  // Light lavender
                    ),
                    center = Offset(0.3f + gradientOffset * 0.4f, 0.3f + gradientOffset * 0.4f)
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .scale(scale)
                .alpha(alpha)
        ) {
            // Sparkle decorations around the camera
            Box(
                modifier = Modifier.size(120.dp),
                contentAlignment = Alignment.Center
            ) {
                // Static sparkles in fixed positions
                val sparklePositions = listOf(
                    Pair(-40.dp, -40.dp), // Top left
                    Pair(40.dp, -40.dp),  // Top right
                    Pair(-50.dp, 0.dp),   // Left
                    Pair(50.dp, 0.dp),    // Right
                    Pair(-40.dp, 40.dp),  // Bottom left
                    Pair(40.dp, 40.dp)    // Bottom right
                )

                sparklePositions.forEachIndexed { index, (x, y) ->
                    val sparkleScale by infiniteTransition.animateFloat(
                        initialValue = 0.5f,
                        targetValue = 1.2f,
                        animationSpec = infiniteRepeatable(
                            animation = tween(1000 + index * 200, easing = FastOutSlowInEasing),
                            repeatMode = RepeatMode.Reverse
                        ),
                        label = "sparkle_scale_$index"
                    )

                    Box(
                        modifier = Modifier
                            .offset(x, y)
                            .size(8.dp)
                            .scale(sparkleScale)
                            .background(
                                color = when (index % 3) {
                                    0 -> Color(0xFFFFB6C1) // Light pink
                                    1 -> Color(0xFFADD8E6) // Light blue
                                    else -> Color(0xFFDDA0DD) // Plum
                                },
                                shape = CircleShape
                            )
                            .alpha(sparkleAlpha)
                    )
                }

                // Main camera icon
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .offset(y = bounceOffset.dp)
                        .background(
                            brush = Brush.linearGradient(
                                colors = listOf(
                                    Color(0xFFFF69B4), // Hot pink
                                    Color(0xFF87CEEB)  // Sky blue
                                )
                            ),
                            shape = RoundedCornerShape(16.dp)
                        )
                        .padding(12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    // Camera body
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Color.White.copy(alpha = 0.9f),
                                shape = RoundedCornerShape(8.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        // Camera lens
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .background(
                                    Color(0xFF4A4A4A),
                                    shape = CircleShape
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(20.dp)
                                    .background(
                                        Color(0xFF2C2C2C),
                                        shape = CircleShape
                                    )
                            )
                        }

                        // Camera flash
                        Box(
                            modifier = Modifier
                                .size(6.dp)
                                .background(
                                    Color(0xFFFFD700),
                                    shape = CircleShape
                                )
                                .offset(x = (-12).dp, y = (-12).dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // App name with gradient text effect
            Text(
                text = "MemoCam",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                style = TextStyle(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color(0xFFFF1493), // Deep pink
                            Color(0xFF00BFFF), // Deep sky blue
                            Color(0xFF9370DB)  // Medium purple
                        )
                    )
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Tagline
            Text(
                text = "Capture Every Memory ✨",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF666666),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Loading dots animation
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                repeat(3) { index ->
                    val dotScale by infiniteTransition.animateFloat(
                        initialValue = 0.5f,
                        targetValue = 1f,
                        animationSpec = infiniteRepeatable(
                            animation = tween(600, delayMillis = index * 200),
                            repeatMode = RepeatMode.Reverse
                        ),
                        label = "dot_$index"
                    )

                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .scale(dotScale)
                            .background(
                                Color(0xFFFF69B4).copy(alpha = 0.7f),
                                shape = CircleShape
                            )
                    )
                }
            }
        }

        // Floating hearts animation (simplified)
        val heartPositions = listOf(
            Pair(50.dp, 300.dp),
            Pair(110.dp, 320.dp),
            Pair(170.dp, 310.dp),
            Pair(230.dp, 325.dp),
            Pair(290.dp, 315.dp)
        )

        heartPositions.forEachIndexed { index, (startX, startY) ->
            val heartOffset by infiniteTransition.animateFloat(
                initialValue = 0f,
                targetValue = -150f,
                animationSpec = infiniteRepeatable(
                    animation = tween(4000 + index * 500, easing = LinearEasing),
                    repeatMode = RepeatMode.Restart
                ),
                label = "heart_$index"
            )

            val heartAlpha by infiniteTransition.animateFloat(
                initialValue = 0f,
                targetValue = 1f,
                animationSpec = infiniteRepeatable(
                    animation = keyframes {
                        durationMillis = 4000 + index * 500
                        0f at 0
                        1f at 1000
                        1f at 3000
                        0f at 4000 + index * 500
                    },
                    repeatMode = RepeatMode.Restart
                ),
                label = "heart_alpha_$index"
            )

            Text(
                text = "💖",
                fontSize = 16.sp,
                modifier = Modifier
                    .offset(
                        x = startX,
                        y = startY + heartOffset.dp
                    )
                    .alpha(heartAlpha)
            )
        }
    }
}
