package com.logixowl.memocam.features.memo.take_picture

import androidx.lifecycle.viewModelScope
import com.logixowl.memocam.core.BaseViewModel
import com.logixowl.memocam.core.DataError
import com.logixowl.memocam.core.FileManager
import com.logixowl.memocam.delegate.FolderImagesCacheDelegate
import com.logixowl.memocam.ui.error.asStringResource
import dev.icerock.moko.permissions.DeniedAlwaysException
import dev.icerock.moko.permissions.DeniedException
import dev.icerock.moko.permissions.Permission
import dev.icerock.moko.permissions.PermissionsController
import dev.icerock.moko.permissions.camera.CAMERA
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Created by AP-Jake
 * on 05/07/2025
 */

class TakePictureViewModel(
    val permissionsController: PermissionsController,
    private val folderImagesCacheDelegate: FolderImagesCacheDelegate,
) : BaseViewModel<TakePictureEvent>(),
    FolderImagesCacheDelegate by folderImagesCacheDelegate {

    private val _state = MutableStateFlow(TakePictureUiState())
    val state = _state
        .onStart { checkPermission() }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            TakePictureUiState()
        )

    fun onAction(action: TakePictureAction) {
        when (action) {
            is TakePictureAction.OnCameraError -> {
                onCameraError()
            }
            TakePictureAction.OnCameraReady -> {
                onCameraReady()
            }
            TakePictureAction.OnCameraSwitch -> {
                onToggleCamera()
            }
            TakePictureAction.OnClearError -> {
                onClearError()
            }
            TakePictureAction.OnFlashToggle -> {
                onToggleFlash()
            }
            is TakePictureAction.OnImageCaptured -> {
                onCapturedImage(action.imagePath)
            }
            TakePictureAction.OnPermissionDenied -> {}
            TakePictureAction.OnPermissionGranted -> {}
            TakePictureAction.OnKeepPhoto -> {
                onKeepImage()
            }
            TakePictureAction.OnPermissionRequested -> {
                requestCameraPermission()
            }
            TakePictureAction.OnRetakePhoto -> {
                onRetakeImage()
            }
            TakePictureAction.OnTakePicture -> {
                onTakeImage()
            }
            is TakePictureAction.OnOpacityChanged -> {
                onOpacityChanged(action.opacity)
            }
            else -> {}
        }
    }

    private fun onOpacityChanged(opacity: Float) {
        _state.update {
            it.copy(
                overlayOpacity = opacity
            )
        }
    }

    private fun onKeepImage() {
        _state.value.capturedImagePath?.let {
            emitEvent(TakePictureEvent.OnSuccessImageCaptured(it))
        }
    }

    private fun onCameraError() {
        _state.update {
            it.copy(
                isLoading = false,
                errorMessage = DataError.Local.UNKNOWN.asStringResource
            )
        }
    }

    private fun onClearError() {
        _state.update {
            it.copy(
                errorMessage = null
            )
        }
    }

    private fun onCameraReady() {
        _state.update {
            it.copy(
                isCameraReady = true
            )
        }
    }

    private fun onToggleCamera() {
        _state.update {
            it.copy(
                cameraFacing = it.cameraFacing.toggleNext()
            )
        }
    }

    private fun onToggleFlash() {
        _state.update {
            it.copy(
                flashMode = it.flashMode.toggleNext()
            )
        }
    }

    private fun onRetakeImage() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true
                )
            }
            _state.value.capturedImagePath?.let { path ->
                withContext(Dispatchers.IO) {
                    FileManager.deleteFile(path)
                }
            }
            _state.update {
                it.copy(
                    isLoading = false,
                    capturedImagePath = null,
                )
            }
        }
    }

    private fun onTakeImage() {
        _state.update {
            it.copy(
                isLoading = true
            )
        }
    }

    private fun onCapturedImage(imagePath: String) {
        _state.update {
            it.copy(
                isLoading = false,
                capturedImagePath = imagePath,
            )
        }
    }

    private fun requestCameraPermission() {
        viewModelScope.launch {
            try {
                permissionsController.providePermission(Permission.CAMERA)
                // Permission has been granted successfully.
                _state.update {
                    it.copy(
                        isPermissionLoading = false,
                        isPermissionGranted = true,
                        isPermissionRequested = false,
                        errorMessage = null,
                    )
                }
            } catch(deniedAlways: DeniedAlwaysException) {
                // Permission is always denied.
                _state.update {
                    it.copy(
                        isPermissionLoading = false,
                        isPermissionGranted = false,
                        isPermissionRequested = true,
                        errorMessage = DataError.Local.UNKNOWN.asStringResource,
                    )
                }
            } catch(denied: DeniedException) {
                // Permission was denied.
                _state.update {
                    it.copy(
                        isPermissionLoading = false,
                        isPermissionGranted = false,
                        isPermissionRequested = false,
                        errorMessage = DataError.Local.UNKNOWN.asStringResource,
                    )
                }
            }
        }
    }

    private fun checkPermission() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true,
                )
            }
            val isCameraGranted = permissionsController.isPermissionGranted(Permission.CAMERA)

            _state.update {
                it.copy(
                    isPermissionLoading = false,
                    isPermissionGranted = isCameraGranted,
                    isPermissionRequested = !isCameraGranted,
                )
            }

            val lastImage = folderImagesCacheState.value.images.firstOrNull()
            // for animation
            delay(300)
            _state.update {
                it.copy(
                    isLoading = false,
                    overlayImage = lastImage,
                )
            }
        }
    }

}
