package com.logixowl.memocam.features.memo.create_folder

/**
 * Created by AP-Jake
 * on 28/06/2025
 */

sealed interface CreateFolderEvent {
    data class SuccessCreated(val folderId: String) : CreateFolderEvent
}
