package com.logixowl.memocam.features.memo.take_picture

/**
 * Created by AP-Jake
 * on 25/06/2025
 */

data class TakePictureUiState(
    val overlayImageUrl: String? = null,
    val overlayOpacity: Float = 0.5f,
    val isFrontCamera: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
