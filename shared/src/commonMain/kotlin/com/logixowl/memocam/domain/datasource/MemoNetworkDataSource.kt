package com.logixowl.memocam.domain.datasource

import com.logixowl.memocam.core.DataError
import com.logixowl.memocam.core.Result
import com.logixowl.memocam.domain.model.Folder
import com.logixowl.memocam.domain.model.FolderImage
import com.logixowl.memocam.domain.model.PaginatedData
import com.logixowl.memocam.domain.model.payload.UploadFolderImagePayload

/**
 * Created by AP-Jake
 * on 22/06/2025
 */

interface MemoNetworkDataSource {

    // --- Folders ---
    suspend fun getAllFolders(): Result<List<Folder>, DataError.Remote>

    suspend fun createFolder(
        name: String
    ): Result<Folder, DataError.Remote>

    suspend fun deleteFolder(
        folderId: String
    ): Result<Unit, DataError.Remote>

    // --- Images ---
    suspend fun getImagesInFolder(
        folderId: String,
        page: Int,
        pageSize: Int,
    ): Result<PaginatedData<FolderImage>, DataError.Remote>

    suspend fun uploadImage(
        payload: UploadFolderImagePayload
    ): Result<FolderImage, DataError.Remote>

//    suspend fun getImage(
//        imageId: String
//    ): Result<ByteArray, DataError.Remote> // assuming raw bytes

    suspend fun deleteImage(
        imageId: String
    ): Result<Unit, DataError.Remote>

    // --- Poster ---
    suspend fun updatePosterImage(
        folderId: String,
        imageId: String
    ): Result<Folder, DataError.Remote>

}
