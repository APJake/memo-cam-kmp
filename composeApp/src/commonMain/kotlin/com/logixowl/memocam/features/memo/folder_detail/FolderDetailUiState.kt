package com.logixowl.memocam.features.memo.folder_detail

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Folder
import androidx.compose.ui.graphics.vector.ImageVector
import com.logixowl.memocam.model.FolderImageUiModel

/**
 * Created by AP-Jake
 * on 05/07/2025
 */

data class FolderDetailUiState(
    val isLoading: Boolean = false,
    val id: String = "",
    val title: String = "FOLDER",
    val description: String = "...",
    val icon: ImageVector = Icons.Rounded.Folder,
    val lastUpdated: String = "",
    val imageCount: Int = 0,
    val canCaptureToday: Boolean = false,
    val images: List<FolderImageUiModel> = emptyList(),
)
