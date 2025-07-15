package com.logixowl.memocam.features.memo.upload_screen

/**
 * Created by AP-Jake
 * on 15/07/2025
 */

sealed interface UploadAction {
    data object TryAgain : UploadAction
    data object OnClickedBack : UploadAction
}
