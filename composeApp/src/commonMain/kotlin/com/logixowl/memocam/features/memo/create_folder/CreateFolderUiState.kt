package com.logixowl.memocam.features.memo.create_folder

/**
 * Created by AP-Jake
 * on 25/06/2025
 */

data class CreateFolderUiState(
    val title: String = "",
    val description: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
