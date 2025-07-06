package com.logixowl.memocam.features.memo.image_detail

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.logixowl.memocam.model.FolderImageUiModel
import com.logixowl.memocam.ui.components.MemoImage
import com.logixowl.memocam.ui.extensions.preview
import com.logixowl.memocam.ui.themes.AppTheme
import com.logixowl.memocam.ui.themes.spacing
import com.logixowl.memocam.ui.utils.LaunchedEventHandler
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

/**
 * Created by AP-Jake
 * on 05/07/2025
 */

@Composable
fun ImageDetailRoute(
    viewModel: ImageDetailViewModel = koinViewModel(),
    onClickedBack: () -> Unit,
) {
    val uiState by viewModel.state.collectAsState()

    LaunchedEventHandler(viewModel.event) { event ->
        when (event) {
            else -> {}
        }
    }

    if (uiState.isLoading) {
        ImageDetailLoading()
    } else {
        ImageDetailScreen(
            uiState = uiState,
            onAction = { action ->
                when (action) {
                    ImageDetailAction.OnClickedClose -> {
                        onClickedBack.invoke()
                    }

                    else -> viewModel.onAction(action)
                }
            }
        )
    }
}

@Composable
private fun ImageDetailLoading() {
    val lightGradient = Brush.linearGradient(
        colors = listOf(
            Color(0xFFFFF3E0),
            Color(0xFFF3E5F5),
            Color(0xFFE8F5E8)
        ),
        start = Offset(0f, 0f),
        end = Offset(1000f, 1000f)
    )
    val systemBarsPadding = WindowInsets.systemBars.asPaddingValues()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(lightGradient)
            .padding(
                top = systemBarsPadding.calculateTopPadding()
            )
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(16.dp)
                .align(Alignment.Center)
        )
    }
}

@Composable
fun ImageDetailScreen(
    uiState: ImageDetailUiState,
    onAction: (ImageDetailAction) -> Unit = {},
) {
    val pagerState = rememberPagerState(
        initialPage = uiState.index,
        pageCount = { uiState.images.size }
    )

    var showControls by remember { mutableStateOf(true) }
    val controlsAlpha by animateFloatAsState(
        targetValue = if (showControls) 1f else 0f,
        animationSpec = tween(300),
        label = "controls_alpha"
    )

    // Auto-hide controls after 3 seconds
    LaunchedEffect(showControls) {
        if (showControls) {
            kotlinx.coroutines.delay(3000)
            showControls = false
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .pointerInput(Unit) {
                // Show controls when user taps
                detectTapGestures {
                    showControls = !showControls
                }
            }
    ) {
        // Main image pager
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            ZoomableImage(
                image = uiState.images[page],
                onImageTap = { showControls = !showControls }
            )
        }
        val systemBarsPadding = WindowInsets.systemBars.asPaddingValues()

        // Top controls (close button and page indicator)
        AnimatedVisibility(
            visible = showControls,
            enter = slideInVertically(
                initialOffsetY = { -it },
                animationSpec = tween(300)
            ) + fadeIn(animationSpec = tween(300)),
            exit = slideOutVertically(
                targetOffsetY = { -it },
                animationSpec = tween(300)
            ) + fadeOut(animationSpec = tween(300)),
            modifier = Modifier
                .padding(top = systemBarsPadding.calculateTopPadding())
                .align(Alignment.TopCenter)
                .zIndex(1f)
        ) {
            TopControlsBar(
                currentPage = pagerState.currentPage,
                totalPages = uiState.images.size,
                onClose = {
                    onAction.invoke(ImageDetailAction.OnClickedClose)
                }
            )
        }

        // Bottom controls (image info)
        AnimatedVisibility(
            visible = showControls,
            enter = slideInVertically(
                initialOffsetY = { it },
                animationSpec = tween(300)
            ) + fadeIn(animationSpec = tween(300)),
            exit = slideOutVertically(
                targetOffsetY = { it },
                animationSpec = tween(300)
            ) + fadeOut(animationSpec = tween(300)),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .zIndex(1f)
        ) {
            BottomImageInfo(
                image = uiState.images[pagerState.currentPage]
            )
        }

        // Page indicators (dots)
        if (uiState.images.size > 1) {
            Row(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 32.dp)
                    .graphicsLayer { alpha = controlsAlpha }
                    .zIndex(1f),
                horizontalArrangement = Arrangement.Center
            ) {
                repeat(uiState.images.size) { index ->
                    val isSelected = pagerState.currentPage == index
                    Box(
                        modifier = Modifier
                            .size(if (isSelected) 10.dp else 6.dp)
                            .clip(CircleShape)
                            .background(
                                if (isSelected) Color.White else Color.White.copy(alpha = 0.5f)
                            )
                    )
                    if (index < uiState.images.size - 1) {
                        Spacer(modifier = Modifier.width(6.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun TopControlsBar(
    currentPage: Int,
    totalPages: Int,
    onClose: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(
                Color.Black.copy(alpha = 0.3f),
                shape = RoundedCornerShape(16.dp)
            )
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Close button
        IconButton(
            onClick = onClose,
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(Color.White.copy(alpha = 0.2f))
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Close",
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
        }

        // Page indicator
        Text(
            text = "${currentPage + 1} / $totalPages",
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun BottomImageInfo(image: FolderImageUiModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color.Transparent,
                        Color.Black.copy(alpha = 0.7f)
                    )
                )
            )
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = image.dayNumber,
            fontSize = 16.sp,
            color = Color.White.copy(alpha = 0.9f),
            textAlign = TextAlign.Center
        )
        Text(
            text = "📅 ${image.uploadedAt}",
            fontSize = 14.sp,
            color = Color.White.copy(alpha = 0.8f),
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(MaterialTheme.spacing.medium2))
    }
}

@Composable
fun ZoomableImage(
    image: FolderImageUiModel,
    onImageTap: () -> Unit
) {
    var scale by remember { mutableStateOf(1f) }
    var offset by remember { mutableStateOf(Offset.Zero) }
    val maxScale = 4f
    val minScale = 1f

    val animatedScale by animateFloatAsState(
        targetValue = scale,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "zoom_scale"
    )

//    val animatedOffset by animateOffsetAsState(
//        targetValue = offset,
//        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
//        label = "zoom_offset"
//    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(0.dp))
            .pointerInput(Unit) {
                detectTapGestures(
                    onDoubleTap = {
                        scale = if (scale > minScale) minScale else 2f
                        offset = Offset.Zero
                    },
                    onTap = { onImageTap() }
                )
            }
            .pointerInput(scale) {
                if (scale > 1f) {
                    detectTransformGestures { _, pan, zoom, _ ->
                        val newScale = (scale * zoom).coerceIn(minScale, maxScale)
                        val newOffset = if (newScale > minScale) {
                            offset + pan
                        } else {
                            Offset.Zero
                        }

                        scale = newScale
                        offset = newOffset
                    }
                }
            },
        contentAlignment = Alignment.Center
    ) {
        // Image with beautiful gradient background
        Box(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer {
                    scaleX = animatedScale
                    scaleY = animatedScale
                    translationX = offset.x
                    translationY = offset.y
                },
            contentAlignment = Alignment.Center
        ) {
            MemoImage(
                url = image.url,
                modifier = Modifier
                    .fillMaxSize(),
            )
        }

        // Zoom hint overlay (shows briefly)
        var showZoomHint by remember { mutableStateOf(true) }

        LaunchedEffect(Unit) {
            kotlinx.coroutines.delay(2000)
            showZoomHint = false
        }

        AnimatedVisibility(
            visible = showZoomHint,
            enter = fadeIn() + scaleIn(),
            exit = fadeOut() + scaleOut(),
            modifier = Modifier.align(Alignment.Center)
        ) {
            Card(
                modifier = Modifier.padding(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.Black.copy(alpha = 0.7f)
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "💡 Double tap to zoom\nPinch to zoom\nTap to toggle controls",
                    color = Color.White,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}

@Composable
fun animateOffsetAsState(
    targetValue: Offset,
    animationSpec: AnimationSpec<Offset> = spring(),
    label: String = "OffsetAnimation"
): State<Offset> {
    val animatable = remember { Animatable(targetValue, Offset.VectorConverter) }

    LaunchedEffect(targetValue) {
        animatable.animateTo(targetValue, animationSpec)
    }

    return animatable.asState()
}

@Preview
@Composable
fun PreviewFullScreenImageViewer() {
    AppTheme {
        ImageDetailScreen(
            uiState = ImageDetailUiState(
                images = (1..5).map {
                    FolderImageUiModel.preview()
                }
            ),
        )
    }
}
