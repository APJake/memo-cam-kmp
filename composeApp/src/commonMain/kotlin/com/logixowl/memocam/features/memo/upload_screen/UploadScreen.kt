package com.logixowl.memocam.features.memo.upload_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.logixowl.memocam.ui.components.UploadingAnimation
import com.logixowl.memocam.ui.components.UploadingAnimationState
import com.logixowl.memocam.ui.utils.LaunchedEventHandler
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

/**
 * Created by Jake
 * on 15/07/2025
 */

@Composable
fun UploadRoute(
    viewModel: UploadViewModel = koinViewModel(),
    onSuccess: (String) -> Unit,
) {
    val state by viewModel.state.collectAsState()

    LaunchedEventHandler(viewModel.event) { event ->
        when (event) {
            is UploadEvent.OnSuccess -> onSuccess.invoke(event.imageId)
        }
    }

    UploadScreen(
        state = state,
        onAction = { action ->
            when (action) {
                else -> viewModel.onAction(action)
            }
        }
    )
}

@Composable
fun UploadScreen(
    state: UploadUiState,
    onAction: (UploadAction) -> Unit,
) {
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
        UploadingAnimation(state.loadingState)

        if (state.loadingState == UploadingAnimationState.Failed) {
            Button(
                onClick = {
                    onAction.invoke(UploadAction.TryAgain)
                },
                modifier = Modifier
                    .padding(40.dp)
                    .align(Alignment.BottomCenter)
            ) {
                Text("Try Again")
            }
        }
    }
}

@Preview
@Composable
private fun UploadScreenPreview() {
    MaterialTheme {
        UploadScreen(
            state = UploadUiState(),
            onAction = {},
        )
    }
}
