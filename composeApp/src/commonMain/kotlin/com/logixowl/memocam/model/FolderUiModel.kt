package com.logixowl.memocam.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Folder
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Created by AP-Jake
 * on 25/06/2025
 */

data class FolderUiModel(
    val id: String,
    val title: String,
    val description: String,
    val icon: ImageVector = Icons.Default.Folder,
    val lastUpdated: String,
    val itemCount: Int
)
