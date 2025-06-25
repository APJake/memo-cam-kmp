package com.logixowl.memocam.data.network.datasource

import com.logixowl.memocam.core.DataError
import com.logixowl.memocam.core.Result
import com.logixowl.memocam.core.map
import com.logixowl.memocam.data.mapper.ResponseMapper
import com.logixowl.memocam.data.network.request.CreateFolderRequest
import com.logixowl.memocam.data.network.response.FolderImageResponse
import com.logixowl.memocam.data.network.response.FolderResponse
import com.logixowl.memocam.data.network.response.PaginatedImagesResponse
import com.logixowl.memocam.data.network.util.AppUrl
import com.logixowl.memocam.data.network.util.safeCall
import com.logixowl.memocam.domain.datasource.MemoNetworkDataSource
import com.logixowl.memocam.domain.model.Folder
import com.logixowl.memocam.domain.model.FolderImage
import com.logixowl.memocam.domain.model.PaginatedData
import com.logixowl.memocam.domain.model.payload.UploadFolderImagePayload
import io.ktor.client.HttpClient
import io.ktor.client.request.delete
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitFormWithBinaryData
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders

/**
 * Created by AP-Jake
 * on 24/06/2025
 */

class MemoNetworkDataSourceImpl(
    private val client: HttpClient,
) : MemoNetworkDataSource {
    override suspend fun getAllFolders(): Result<List<Folder>, DataError.Remote> {
        return safeCall<List<FolderResponse>> {
            client.get(AppUrl.allFolder)
        }.map { it.map(ResponseMapper::mapToFolderDomain) }
    }

    override suspend fun createFolder(name: String): Result<Folder, DataError.Remote> {
        return safeCall<FolderResponse> {
            client.post(AppUrl.createFolder) {
                setBody(CreateFolderRequest(name))
            }
        }.map(ResponseMapper::mapToFolderDomain)
    }

    override suspend fun deleteFolder(folderId: String): Result<Unit, DataError.Remote> {
        return safeCall<Unit> {
            client.delete(AppUrl.deleteFolder(folderId))
        }
    }

    override suspend fun getImagesInFolder(
        folderId: String,
        page: Int,
        pageSize: Int,
    ): Result<PaginatedData<FolderImage>, DataError.Remote> {
        return safeCall<PaginatedImagesResponse> {
            client.get(AppUrl.getFolderImage(folderId, page, pageSize))
        }.map(ResponseMapper::mapToPaginatedImagesDomain)
    }

    override suspend fun uploadImage(
        payload: UploadFolderImagePayload,
    ): Result<FolderImage, DataError.Remote> {
        return safeCall<FolderImageResponse> {
            client.submitFormWithBinaryData(
                url = AppUrl.uploadFolderImage(payload.folderId),
                formData = formData {
                    append("image", payload.byteArray, Headers.build {
                        append(HttpHeaders.ContentType, payload.mimeType)
                        append(HttpHeaders.ContentDisposition, "filename=${payload.filename}")
                    })
                }
            )
        }.map(ResponseMapper::mapToFolderImageDomain)
    }

    override suspend fun deleteImage(imageId: String): Result<Unit, DataError.Remote> {
        return safeCall<Unit> {
            client.delete(AppUrl.deleteFolderImage(imageId))
        }
    }

    override suspend fun updatePosterImage(
        folderId: String,
        imageId: String
    ): Result<Folder, DataError.Remote> {
        return safeCall<FolderResponse> {
            client.put(AppUrl.updateFolderPoster(folderId, imageId))
        }.map(ResponseMapper::mapToFolderDomain)
    }
}
