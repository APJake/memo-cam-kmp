package com.logixowl.memocam.features.memo.dashboard

import com.logixowl.memocam.model.FolderUiModel

/**
 * Created by AP-Jake
 * on 25/06/2025
 */

data class DashboardUiState(
    val userName: String = "User",
    val folders: List<FolderUiModel> = emptyList(),
    val isLoading: Boolean = false
)
