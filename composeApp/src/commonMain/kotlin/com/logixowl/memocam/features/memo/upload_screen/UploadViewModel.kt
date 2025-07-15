package com.logixowl.memocam.features.memo.upload_screen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.logixowl.memocam.core.AppLogger
import com.logixowl.memocam.core.BaseViewModel
import com.logixowl.memocam.core.onError
import com.logixowl.memocam.core.onSuccess
import com.logixowl.memocam.delegate.FolderImagesCacheDelegate
import com.logixowl.memocam.domain.model.payload.UploadFolderImagePayload
import com.logixowl.memocam.domain.repository.MemoRepository
import com.logixowl.memocam.mapper.toUiModel
import com.logixowl.memocam.service.PlatformImageHandler
import com.logixowl.memocam.ui.components.UploadingAnimationState
import com.logixowl.memocam.ui.error.asStringResource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import memocam.composeapp.generated.resources.Res
import memocam.composeapp.generated.resources.error_local_unknown

/**
 * Created by AP-Jake
 * on Upload
 */

class UploadViewModel(
    savedStateHandle: SavedStateHandle,
    folderImagesCacheDelegate: FolderImagesCacheDelegate,
    private val memoRepository: MemoRepository,
    private val imageHandler: PlatformImageHandler,
) : BaseViewModel<UploadEvent>(),
    FolderImagesCacheDelegate by folderImagesCacheDelegate {
    private val uploadNavigation = savedStateHandle.toRoute<UploadNavigation>()

    private val _state = MutableStateFlow(UploadUiState())
    val state = _state
        .onStart {
            doOnStart()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            UploadUiState()
        )

    fun onAction(action: UploadAction) {
        when (action) {
            UploadAction.TryAgain -> {
                uploadImage()
            }

            else -> {}
        }
    }

    private fun doOnStart() {
        uploadImage()
    }

    private fun uploadImage() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    loadingState = UploadingAnimationState.Loading
                )
            }
            val folderId = folderImagesCacheState.value.folder?.id ?: run {
                _state.update {
                    it.copy(
                        error = Res.string.error_local_unknown,
                        loadingState = UploadingAnimationState.Failed,
                    )
                }
                AppLogger.e("UploadViewModel", "FolderId missing")
                return@launch
            }

            val imageData = imageHandler.getImageData(uploadNavigation.imageUri) ?: kotlin.run {
                _state.update {
                    it.copy(
                        error = Res.string.error_local_unknown,
                        loadingState = UploadingAnimationState.Failed,
                    )
                }
                AppLogger.e("UploadViewModel", "Failed to get image data")
                return@launch
            }
            memoRepository.uploadImage(
                payload = UploadFolderImagePayload(
                    folderId = folderId,
                    filename = imageData.filename,
                    mimeType = imageData.mimeType,
                    byteArray = imageData.byteArray
                )
            ).onSuccess { result ->
                AppLogger.d("UploadViewModel", "Uploaded image :$result")
                _state.update { st ->
                    st.copy(
                        loadingState = UploadingAnimationState.Completed
                    )
                }
                updateFolderImagesCacheState { cacheState ->
                    cacheState.copy(
                        images = listOf(result.toUiModel(1)) + cacheState.images
                    )
                }
                // wait for animation
                delay(400)
                emitEvent(UploadEvent.OnSuccess(result.id))
            }.onError {
                AppLogger.e("UploadViewModel", "Failed to upload: $it")
                _state.update { st ->
                    st.copy(
                        error = it.asStringResource,
                        loadingState = UploadingAnimationState.Failed
                    )
                }
            }
        }
    }
}
