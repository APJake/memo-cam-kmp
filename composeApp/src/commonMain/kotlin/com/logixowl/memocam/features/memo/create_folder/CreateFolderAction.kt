package com.logixowl.memocam.features.memo.create_folder

/**
 * Created by AP-Jake
 * on 25/06/2025
 */

sealed interface CreateFolderAction {
    data class OnChangedTitle(val title: String) : CreateFolderAction
    data class OnChangedDescription(val description: String) : CreateFolderAction
    data object OnClickedCreateFolder : CreateFolderAction
    data object OnClickedBack : CreateFolderAction
}
