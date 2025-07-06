package com.logixowl.memocam.features.memo.folder_detail

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Folder
import androidx.lifecycle.viewModelScope
import com.logixowl.memocam.core.BaseViewModel
import com.logixowl.memocam.core.onSuccess
import com.logixowl.memocam.delegate.FolderImagesCacheDelegate
import com.logixowl.memocam.domain.repository.MemoRepository
import com.logixowl.memocam.mapper.toUiModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * Created by AP-Jake
 * on 05/07/2025
 */

class FolderDetailViewModel(
    private val memoRepository: MemoRepository,
    private val folderImagesCacheDelegate: FolderImagesCacheDelegate,
) : BaseViewModel<FolderDetailEvent>(),
    FolderImagesCacheDelegate by folderImagesCacheDelegate {

    private val _state = MutableStateFlow(FolderDetailUiState())
    val state = _state.combine(folderImagesCacheState) { uiState, cacheState ->
        uiState.copy(
            id = cacheState.folder?.id.orEmpty(),
            title = cacheState.folder?.title.orEmpty(),
            description = cacheState.folder?.description.orEmpty(),
            icon = cacheState.folder?.icon ?: Icons.Rounded.Folder,
            lastUpdated = cacheState.folder?.lastUpdated.orEmpty(),
            imageCount = cacheState.folder?.itemCount ?: 0,
            canCaptureToday = cacheState.canCaptureToday,
            images = cacheState.images,
        )
    }
        .onStart { loadFolderDetail() }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            FolderDetailUiState()
        )

    private fun loadFolderDetail() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true,
                )
            }
            delay(200)
            if (state.value.id.isBlank()) {
                _state.update {
                    it.copy(
                        isLoading = false,
                    )
                }
                emitEvent(FolderDetailEvent.OnFolderIdEmpty)
                return@launch
            }
            memoRepository.getImagesInFolder(
                folderId = state.value.id,
                pageSize = 20,
                page = 0
            ).onSuccess { result ->
                _state.update {
                    it.copy(
                        isLoading = false,
                    )
                }
                updateFolderImagesCacheState { cacheState ->
                    val len = result.images.size
                    cacheState.copy(
                        canCaptureToday = true,
                        images = result.images.mapIndexed { index, image ->
                            image.toUiModel(len - index)
                        }
                    )
                }
            }
        }
    }

}
