package com.logixowl.memocam.domain.repository

import com.logixowl.memocam.core.DataError
import com.logixowl.memocam.core.Result
import com.logixowl.memocam.domain.model.Folder
import com.logixowl.memocam.domain.model.FolderImage
import com.logixowl.memocam.domain.model.PaginatedData
import com.logixowl.memocam.domain.model.payload.CreateFolderPayload
import com.logixowl.memocam.domain.model.payload.UpdateFolderPayload
import com.logixowl.memocam.domain.model.payload.UploadFolderImagePayload

/**
 * Created by AP-Jake
 * on 25/06/2025
 */

interface MemoRepository {
    // --- Folders ---
    suspend fun getAllFolders(): Result<List<Folder>, DataError>

    suspend fun createFolder(
        payload: CreateFolderPayload,
    ): Result<Folder, DataError>

    suspend fun updateFolder(
        payload: UpdateFolderPayload,
    ): Result<Folder, DataError>

    suspend fun deleteFolder(
        folderId: String
    ): Result<Unit, DataError>

    // --- Images ---
    suspend fun getImagesInFolder(
        folderId: String,
        page: Int,
        pageSize: Int,
    ): Result<PaginatedData<FolderImage>, DataError>

    suspend fun uploadImage(
        payload: UploadFolderImagePayload,
    ): Result<FolderImage, DataError>

//    suspend fun getImage(
//        imageId: String
//    ): Result<ByteArray, DataError.Remote> // assuming raw bytes

    suspend fun deleteImage(
        imageId: String
    ): Result<Unit, DataError>

}
