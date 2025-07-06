package com.logixowl.memocam.data.repository

import com.logixowl.memocam.core.DataError
import com.logixowl.memocam.core.Result
import com.logixowl.memocam.domain.datasource.MemoNetworkDataSource
import com.logixowl.memocam.domain.model.Folder
import com.logixowl.memocam.domain.model.FolderImage
import com.logixowl.memocam.domain.model.PaginatedData
import com.logixowl.memocam.domain.model.payload.CreateFolderPayload
import com.logixowl.memocam.domain.model.payload.UpdateFolderPayload
import com.logixowl.memocam.domain.model.payload.UploadFolderImagePayload
import com.logixowl.memocam.domain.repository.MemoRepository

/**
 * Created by AP-Jake
 * on 25/06/2025
 */

internal class MemoRepositoryImpl(
    private val memoNetworkDataSource: MemoNetworkDataSource,
) : MemoRepository {
    override suspend fun getAllFolders(): Result<List<Folder>, DataError> {
        return memoNetworkDataSource.getAllFolders()
    }

    override suspend fun createFolder(payload: CreateFolderPayload): Result<Folder, DataError> {
        return memoNetworkDataSource.createFolder(payload)
    }

    override suspend fun updateFolder(payload: UpdateFolderPayload): Result<Folder, DataError> {
        return memoNetworkDataSource.updateFolder(payload)
    }

    override suspend fun deleteFolder(folderId: String): Result<Unit, DataError> {
        return memoNetworkDataSource.deleteFolder(folderId)
    }

    override suspend fun getImagesInFolder(
        folderId: String,
        page: Int,
        pageSize: Int
    ): Result<PaginatedData<FolderImage>, DataError> {
        return memoNetworkDataSource.getImagesInFolder(folderId, page, pageSize)
    }

    override suspend fun uploadImage(payload: UploadFolderImagePayload): Result<FolderImage, DataError> {
        return memoNetworkDataSource.uploadImage(payload)
    }

    override suspend fun deleteImage(imageId: String): Result<Unit, DataError> {
        return memoNetworkDataSource.deleteImage(imageId)
    }
}
