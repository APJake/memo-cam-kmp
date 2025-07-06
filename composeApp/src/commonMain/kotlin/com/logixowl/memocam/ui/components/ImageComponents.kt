package com.logixowl.memocam.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImagePainter
import coil3.compose.rememberAsyncImagePainter
import com.logixowl.memocam.ui.themes.AppTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * Created by AP-Jake
 * on 06/07/2025
 */

@Composable
fun MemoImage(
    url: String,
    contentScale: ContentScale = ContentScale.Crop,
    placeholderColors: List<Color> = listOf(
        Color(0xFF667EEA),
        Color(0xFF764BA2),
        Color(0xFFF093FB),
        Color(0xFFF5576C)
    ),
    errorColor: Color = MaterialTheme.colorScheme.error,
    modifier: Modifier = Modifier,
) {
    var isRetrying by remember { mutableStateOf(false) }
    var retryCount by remember { mutableStateOf(0) }
    val maxRetries = 3

    val painter = rememberAsyncImagePainter(url)
    val state by painter.state.collectAsState()

    when (state) {
        is AsyncImagePainter.State.Empty,
        is AsyncImagePainter.State.Loading -> {
            ShimmerPlaceholder(
                colors = placeholderColors,
                modifier = modifier
            )
        }

        is AsyncImagePainter.State.Success -> {
            Image(
                painter = painter,
                contentDescription = "Memory image",
                contentScale = contentScale,
                modifier = modifier
            )
        }

        is AsyncImagePainter.State.Error -> {
            ErrorPlaceholder(
                errorColor = errorColor,
                canRetry = retryCount < maxRetries,
                isRetrying = isRetrying,
                onRetry = {
                    if (retryCount < maxRetries) {
                        isRetrying = true
                        retryCount++
                    }
                },
                modifier = modifier
            )
        }
    }
}

@Composable
private fun ShimmerPlaceholder(
    colors: List<Color>,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "shimmer")
    val shimmerTranslate by infiniteTransition.animateFloat(
        initialValue = -1000f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "shimmer_translate"
    )

    val shimmerColors = listOf(
        colors.first().copy(alpha = 0.3f),
        colors.first().copy(alpha = 0.6f),
        colors.first().copy(alpha = 0.3f)
    )

    Box(
        modifier = modifier
            .background(
                brush = Brush.linearGradient(
                    colors = colors,
                    start = Offset(0f, 0f),
                    end = Offset(1000f, 1000f)
                )
            )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.linearGradient(
                        colors = shimmerColors,
                        start = Offset(shimmerTranslate, shimmerTranslate),
                        end = Offset(
                            shimmerTranslate + 200f,
                            shimmerTranslate + 200f
                        )
                    )
                )
        )
    }
}

@Composable
private fun ErrorPlaceholder(
    errorColor: Color,
    canRetry: Boolean,
    isRetrying: Boolean,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(
                Brush.linearGradient(
                    colors = listOf(
                        Color(0xFFe7bebe),
                        Color(0xFFF5AA5B),
                        Color(0xFFc78181)
                    ),
                    start = Offset(0f, 0f),
                    end = Offset(1000f, 1000f)
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize().padding(16.dp)
        ) {
            // Error icon (using a simple composed icon)
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(
                        color = errorColor.copy(alpha = 0.2f),
                        shape = RoundedCornerShape(24.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "📷",
                    fontSize = 24.sp
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Failed to load image",
                color = errorColor,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center
            )

            if (canRetry) {
                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = onRetry,
                    enabled = !isRetrying,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = errorColor.copy(alpha = 0.1f),
                        contentColor = errorColor
                    ),
                    modifier = Modifier.height(32.dp)
                ) {
                    if (isRetrying) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(16.dp),
                            strokeWidth = 2.dp,
                            color = errorColor
                        )
                    } else {
                        Text(
                            text = "Retry",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun MemoImagePreview() {
    AppTheme {
//        val state: AsyncImagePainter.State = AsyncImagePainter.State.Error(null, ErrorResult(null, ImageRequest.Builder(
//            LocalPlatformContext.current).build(), Exception("")))
        MemoImage("")
    }
}
