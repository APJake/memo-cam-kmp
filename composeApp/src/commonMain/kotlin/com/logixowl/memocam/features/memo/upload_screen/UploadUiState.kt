package com.logixowl.memocam.features.memo.upload_screen

import com.logixowl.memocam.ui.components.UploadingAnimationState
import org.jetbrains.compose.resources.StringResource

/**
 * Created by AP-Jake
 * on Upload
 */

data class UploadUiState(
    val loadingState: UploadingAnimationState = UploadingAnimationState.Loading,
    val error: StringResource? = null,
)
