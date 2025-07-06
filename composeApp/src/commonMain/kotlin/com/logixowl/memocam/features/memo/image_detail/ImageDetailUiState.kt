package com.logixowl.memocam.features.memo.image_detail

import com.logixowl.memocam.model.FolderImageUiModel

/**
 * Created by AP-Jake
 * on 05/07/2025
 */

data class ImageDetailUiState(
    val index: Int = 0,
    val isLoading: Boolean = true,
    val images: List<FolderImageUiModel> = emptyList()
)
