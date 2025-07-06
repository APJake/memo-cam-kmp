package com.logixowl.memocam.features.memo.take_picture

/**
 * Created by AP-Jake
 * on 03/07/2025
 */

sealed interface TakePictureAction {
    data object OnPermissionRequested : TakePictureAction
    data object OnPermissionGranted : TakePictureAction
    data object OnPermissionDenied : TakePictureAction
    data object OnNavigateBack : TakePictureAction
    data object OnCameraReady : TakePictureAction
    data object OnTakePicture : TakePictureAction
    data object OnFlashToggle : TakePictureAction
    data object OnCameraSwitch : TakePictureAction
    data object OnRetakePhoto : TakePictureAction
    data object OnKeepPhoto : TakePictureAction
    data object OnClearError : TakePictureAction
    data class OnOpacityChanged(val opacity: Float) : TakePictureAction
    data class OnImageCaptured(val imagePath: String) : TakePictureAction
    data class OnCameraError(val error: String) : TakePictureAction
}
