package com.logixowl.memocam.features.memo.folder_detail

/**
 * Created by AP-Jake
 * on 05/07/2025
 */

sealed interface FolderDetailEvent {
    data object OnFolderIdEmpty : FolderDetailEvent
}
