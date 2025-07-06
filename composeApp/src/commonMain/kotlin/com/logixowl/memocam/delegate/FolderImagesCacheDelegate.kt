package com.logixowl.memocam.delegate

import com.logixowl.memocam.model.FolderImageUiModel
import com.logixowl.memocam.model.FolderUiModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

/**
 * Created by AP-Jake
 * on 05/07/2025
 */

data class FolderImagesCacheState(
    val folder: FolderUiModel? = null,
    val canCaptureToday: Boolean = false,
    val images: List<FolderImageUiModel> = emptyList(),
)

interface FolderImagesCacheDelegate {
    val folderImagesCacheState: StateFlow<FolderImagesCacheState>
    fun updateFolderImagesCacheState(function: (FolderImagesCacheState) -> FolderImagesCacheState)
    fun cleanUpFolderImagesCache()
}

class FolderImagesCacheDelegateImpl : FolderImagesCacheDelegate {
    private val _state = MutableStateFlow(FolderImagesCacheState())
    override val folderImagesCacheState: StateFlow<FolderImagesCacheState>
        get() = _state

    override fun updateFolderImagesCacheState(function: (FolderImagesCacheState) -> FolderImagesCacheState) {
        _state.update(function)
    }

    override fun cleanUpFolderImagesCache() {
        _state.update { FolderImagesCacheState() }
    }
}
