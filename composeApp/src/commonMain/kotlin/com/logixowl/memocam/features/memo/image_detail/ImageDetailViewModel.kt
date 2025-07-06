package com.logixowl.memocam.features.memo.image_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.logixowl.memocam.core.BaseViewModel
import com.logixowl.memocam.delegate.FolderImagesCacheDelegate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

/**
 * Created by AP-Jake
 * on 05/07/2025
 */

class ImageDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val folderImagesCacheDelegate: FolderImagesCacheDelegate,
) : BaseViewModel<ImageDetailEvent>(),
    FolderImagesCacheDelegate by folderImagesCacheDelegate {

    private val argument: ImageDetailNavigation = savedStateHandle.toRoute()

    private val _state = MutableStateFlow(ImageDetailUiState())
    val state = combine(_state, folderImagesCacheState) { uiState, cacheState ->
        uiState.copy(
            images = cacheState.images
        )
    }
        .onStart { syncCacheImages() }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            ImageDetailUiState()
        )

    fun onAction(action: ImageDetailAction) {
    }

    private fun syncCacheImages() {
        _state.update { st ->
            st.copy(
                images = folderImagesCacheState.value.images,
                index = folderImagesCacheState.value.images.indexOfFirst { argument.imageId == it.id },
                isLoading = false
            )
        }
    }

}
