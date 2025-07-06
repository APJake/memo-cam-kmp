package com.logixowl.memocam.features.memo.folder_detail

/**
 * Created by AP-Jake
 * on 05/07/2025
 */

sealed interface FolderDetailAction {
    data class OnClickedImage(val imageId: String) : FolderDetailAction
    data object OnClickedCaptureImage : FolderDetailAction
    data object OnClickedBack : FolderDetailAction
}
