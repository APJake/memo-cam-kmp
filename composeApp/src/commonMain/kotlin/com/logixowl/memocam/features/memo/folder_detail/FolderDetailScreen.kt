package com.logixowl.memocam.features.memo.folder_detail

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.Photo
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material.icons.filled.Update
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.logixowl.memocam.model.FolderImageUiModel
import com.logixowl.memocam.ui.extensions.preview
import com.logixowl.memocam.ui.themes.AppTheme
import com.logixowl.memocam.ui.themes.spacing
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

/**
 * Created by AP-Jake
 * on 05/07/2025
 */

@Composable
fun FolderDetailRoute(
    viewModel: FolderDetailViewModel = koinViewModel(),
    onClickedImage: (String) -> Unit,
    onCaptureImage: () -> Unit,
    onBackPressed: () -> Unit,
) {
    val uiState by viewModel.state.collectAsState()

    FolderDetailScreen(
        uiState = uiState,
        onAction = { action ->
            when (action) {
                FolderDetailAction.OnClickedBack -> onBackPressed()
                FolderDetailAction.OnClickedCaptureImage -> onCaptureImage()
                is FolderDetailAction.OnClickedImage -> onClickedImage(action.imageId)
            }
        }
    )
}

@Composable
fun FolderDetailScreen(
    uiState: FolderDetailUiState,
    onAction: (FolderDetailAction) -> Unit = {},
) {

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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Header Section
            FolderHeader(
                uiState = uiState,
                onBackPressed = {
                    onAction.invoke(FolderDetailAction.OnClickedBack)
                }
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Folder Properties Card
            FolderPropertiesCard(uiState = uiState)

            Spacer(modifier = Modifier.height(24.dp))

            // Images Section
            Text(
                text = "📸 Captured Memories",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF333333),
                modifier = Modifier.padding(bottom = 16.dp)
            )

            if (uiState.images.isNotEmpty()) {
                ImagesHorizontalSlider(
                    images = uiState.images,
                    onClickedImage = {
                        onAction.invoke(FolderDetailAction.OnClickedImage(it))
                    }
                )
            } else {
                EmptyImagesState()
            }
        }

        // Animated Floating Action Button
        AnimatedFloatingActionButton(
            onClick = {
                onAction.invoke(FolderDetailAction.OnClickedCaptureImage)
            },
            canCapture = uiState.canCaptureToday,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(MaterialTheme.spacing.large)
        )
    }
}

@Composable
fun FolderHeader(
    uiState: FolderDetailUiState,
    onBackPressed: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = onBackPressed,
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(Color.White.copy(alpha = 0.8f))
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = Color(0xFF333333)
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(1f).padding(horizontal = 16.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(
                        Brush.radialGradient(
                            colors = listOf(
                                Color(0xFFFF6B9D),
                                Color(0xFFFFB347)
                            )
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = uiState.icon,
                    contentDescription = "Folder Icon",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column {
                Text(
                    text = uiState.title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF333333),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = uiState.description,
                    fontSize = 14.sp,
                    color = Color(0xFF666666),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
fun FolderPropertiesCard(uiState: FolderDetailUiState) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp)),
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.9f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                PropertyItem(
                    icon = Icons.Default.Update,
                    label = "Last Updated",
                    value = uiState.lastUpdated.toString(),
                    color = Color(0xFF4FC3F7)
                )
                PropertyItem(
                    icon = Icons.Default.Photo,
                    label = "Images",
                    value = "${uiState.imageCount}",
                    color = Color(0xFFFF6B9D)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Capture Status
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = if (uiState.canCaptureToday) Icons.Default.CheckCircle else Icons.Default.Cancel,
                    contentDescription = "Capture Status",
                    tint = if (uiState.canCaptureToday) Color(0xFF4CAF50) else Color(0xFFFF5722),
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = if (uiState.canCaptureToday) "✨ Ready to capture today!" else "📅 Already captured today",
                    fontSize = 14.sp,
                    color = if (uiState.canCaptureToday) Color(0xFF4CAF50) else Color(0xFFFF5722),
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Composable
fun PropertyItem(
    icon: ImageVector,
    label: String,
    value: String,
    color: Color
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .background(color.copy(alpha = 0.2f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = color,
                modifier = Modifier.size(16.dp)
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(
                text = label,
                fontSize = 12.sp,
                color = Color(0xFF666666)
            )
            Text(
                text = value,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF333333)
            )
        }
    }
}

@Composable
fun ImagesHorizontalSlider(
    images: List<FolderImageUiModel>,
    onClickedImage: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val pagerState = rememberPagerState(pageCount = { images.size })

    Column(
        modifier = modifier
    ) {
        // Main image slider
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(horizontal = 8.dp),
            pageSpacing = 16.dp
        ) { page ->
            ImageSlideCard(
                image = images[page],
                onClick = {
                    onClickedImage.invoke(images[page].id)
                },
                modifier = Modifier.fillMaxSize()
            )
        }

        // Page indicator
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(images.size) { index ->
                val isSelected = pagerState.currentPage == index
                Box(
                    modifier = Modifier
                        .size(if (isSelected) 10.dp else 6.dp)
                        .clip(CircleShape)
                        .background(
                            if (isSelected) Color(0xFFFF6B9D) else Color(0xFFBDBDBD)
                        )
                        .padding(horizontal = 2.dp)
                )
                if (index < images.size - 1) {
                    Spacer(modifier = Modifier.width(4.dp))
                }
            }
        }
    }
}

@Composable
fun ImageSlideCard(
    image: FolderImageUiModel,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(20.dp))
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 12.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(20.dp))
        ) {
            // Image background (placeholder with gradient)
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.linearGradient(
                            colors = listOf(
                                Color(0xFFE1BEE7),
                                Color(0xFFF8BBD9),
                                Color(0xFFFFD54F),
                                Color(0xFF81C784)
                            ),
                            start = Offset(0f, 0f),
                            end = Offset(1000f, 1000f)
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                // In real app, replace with AsyncImage or similar
                Icon(
                    imageVector = Icons.Default.PhotoCamera,
                    contentDescription = "Captured Image",
                    tint = Color.White.copy(alpha = 0.7f),
                    modifier = Modifier.size(80.dp)
                )
            }

            // Gradient overlay for text readability
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Transparent,
                                Color.Black.copy(alpha = 0.3f),
                                Color.Black.copy(alpha = 0.7f)
                            ),
                            startY = 0f,
                            endY = 1000f
                        )
                    )
            )

            // Text overlay at bottom center
            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = image.dayNumber,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = image.uploadedAt.toString(),
                    fontSize = 16.sp,
                    color = Color.White.copy(alpha = 0.9f),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "📅 ${image.uploadedAt}",
                    fontSize = 14.sp,
                    color = Color.White.copy(alpha = 0.8f),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun EmptyImagesState() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White.copy(alpha = 0.7f)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Default.PhotoLibrary,
                contentDescription = "No images",
                tint = Color(0xFFBDBDBD),
                modifier = Modifier.size(48.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "No memories captured yet 📷",
                fontSize = 16.sp,
                color = Color(0xFF666666),
                textAlign = TextAlign.Center
            )
            Text(
                text = "Start capturing your journey!",
                fontSize = 14.sp,
                color = Color(0xFF999999),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun AnimatedFloatingActionButton(
    onClick: () -> Unit,
    canCapture: Boolean,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "fab_animation")

    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "fab_scale"
    )

    val rotation by infiniteTransition.animateFloat(
        initialValue = -5f,
        targetValue = 5f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "fab_rotation"
    )

    FloatingActionButton(
        onClick = onClick,
        modifier = modifier
            .size(64.dp)
            .scale(scale)
            .rotate(rotation),
        containerColor = if (canCapture) Color(0xFFFF6B9D) else Color(0xFFBDBDBD),
        contentColor = Color.White,
        elevation = FloatingActionButtonDefaults.elevation(
            defaultElevation = 12.dp,
            pressedElevation = 16.dp
        )
    ) {
        Icon(
            imageVector = Icons.Default.Camera,
            contentDescription = "Capture Image",
            modifier = Modifier.size(28.dp)
        )
    }
}

@Preview
@Composable
fun PreviewFolderDetailScreen() {
    val sampleImages = listOf(
        FolderImageUiModel.preview(),
        FolderImageUiModel.preview(),
        FolderImageUiModel.preview(),
    )

    val uiState = FolderDetailUiState(
        id = "1",
        title = "My Journey 🌟",
        description = "Daily progress photos",
        icon = Icons.Default.Folder,
        lastUpdated = "Jan 04, 2025",
        imageCount = 3,
        canCaptureToday = true,
        images = sampleImages
    )

    AppTheme {
        FolderDetailScreen(
            uiState = uiState,
        )
    }
}
