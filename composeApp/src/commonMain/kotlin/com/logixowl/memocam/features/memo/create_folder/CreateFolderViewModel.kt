package com.logixowl.memocam.features.memo.create_folder

import androidx.lifecycle.viewModelScope
import com.logixowl.memocam.core.AppLogger
import com.logixowl.memocam.core.BaseViewModel
import com.logixowl.memocam.core.onError
import com.logixowl.memocam.core.onSuccess
import com.logixowl.memocam.domain.model.payload.CreateFolderPayload
import com.logixowl.memocam.domain.repository.MemoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * Created by AP-Jake
 * on 28/06/2025
 */

class CreateFolderViewModel(
    private val memoRepository: MemoRepository,
) : BaseViewModel<CreateFolderEvent>() {

    private val _state = MutableStateFlow(CreateFolderUiState())
    val state = _state
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            CreateFolderUiState()
        )

    fun onAction(action: CreateFolderAction) {
        when (action) {
            is CreateFolderAction.OnChangedDescription -> _state.update {
                it.copy(
                    description = action.description,
                    errorMessage = null,
                )
            }

            is CreateFolderAction.OnChangedTitle -> _state.update {
                it.copy(
                    title = action.title,
                    errorMessage = null,
                )
            }

            CreateFolderAction.OnClickedCreateFolder -> onSubmitCreateFolder()
            else -> {}
        }
    }

    private fun onSubmitCreateFolder() = with(state.value) {
        _state.update {
            it.copy(
                isLoading = true,
            )
        }
        val payload = CreateFolderPayload(
            name = title,
            description = description,
            iconId = 1,
        )

        doCreateFolder(payload)
    }

    private fun doCreateFolder(payload: CreateFolderPayload) {
        viewModelScope.launch {
            memoRepository.createFolder(payload)
                .onSuccess {
                    emitEvent(CreateFolderEvent.SuccessCreated(it.id))
                }
                .onError { error ->
                    AppLogger.e("CreateFolderViewModel", "failed to create folder: $error")

                    _state.update {
                        it.copy(
                            isLoading = true,
                            errorMessage = "Failed to create"
                        )
                    }
                }
            _state.update {
                it.copy(
                    isLoading = false,
                )
            }
        }
    }

}
