package com.logixowl.memocam.features.memo.take_picture

/**
 * Created by AP-Jake
 * on 05/07/2025
 */

sealed interface TakePictureEvent {

    data class OnSuccessImageCaptured(
        val folderId: String,
        val imagePath: String
    ) : TakePictureEvent

}
