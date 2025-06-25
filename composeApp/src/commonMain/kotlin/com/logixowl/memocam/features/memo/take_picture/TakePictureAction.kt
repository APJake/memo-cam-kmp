package com.logixowl.memocam.features.memo.take_picture

/**
 * Created by AP-Jake
 * on 25/06/2025
 */

sealed interface TakePictureAction {
    data object OnBackClicked : TakePictureAction
    data object OnTakePicture : TakePictureAction
    data object OnSwitchCamera : TakePictureAction
    data class OnOpacityChanged(val opacity: Float) : TakePictureAction
    data class OnOverlayImageSelected(val imageUrl: String) : TakePictureAction
}
