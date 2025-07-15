package com.logixowl.memocam.features.change_password

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.logixowl.memocam.ui.utils.LaunchedEventHandler
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

/**
 * Created by AP-Jake
 * on 08/07/2025
 */

@Composable
fun ChangePasswordRoute(
    viewModel: ChangePasswordViewModel = koinViewModel(),
    onNavigateBack: () -> Unit,
) {
    val state by viewModel.state.collectAsState()

    LaunchedEventHandler(viewModel.event) { event ->
        when (event) {
            else -> {}
        }
    }

    ChangePasswordScreen(
        state = state,
        onAction = { action ->
            when (action) {
                else -> viewModel.onAction(action)
            }
        }
    )
}

@Composable
fun ChangePasswordScreen(
    state: ChangePasswordUiState,
    onAction: (ChangePasswordAction) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {

    }
}

@Preview
@Composable
private fun ChangePasswordScreenPreview() {
    MaterialTheme {
        ChangePasswordScreen(
            state = ChangePasswordUiState(),
            onAction = {},
        )
    }
}
