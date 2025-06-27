package com.logixowl.memocam.data.mapper

import com.logixowl.memocam.data.network.response.AuthResponse
import com.logixowl.memocam.data.network.response.FolderImageResponse
import com.logixowl.memocam.data.network.response.FolderResponse
import com.logixowl.memocam.data.network.response.PaginatedImagesResponse
import com.logixowl.memocam.data.network.response.UserResponse
import com.logixowl.memocam.domain.model.Auth
import com.logixowl.memocam.domain.model.Folder
import com.logixowl.memocam.domain.model.FolderImage
import com.logixowl.memocam.domain.model.PaginatedData
import com.logixowl.memocam.domain.model.User

/**
 * Created by AP-Jake
 * on 24/06/2025
 */

internal object ResponseMapper {

    fun mapToPaginatedImagesDomain(res: PaginatedImagesResponse) = PaginatedData(
        images = res.images?.map(::mapToFolderImageDomain).orEmpty(),
        currentPage = res.currentPage ?: -1,
        totalPages = res.totalPages ?: -1,
        totalCount = res.totalCount ?: -1,
        hasNext = res.hasNext ?: false,
        hasPrevious = res.hasPrevious ?: false,
    )

    fun mapToFolderImageDomain(res: FolderImageResponse) = FolderImage(
        id = res.id.orEmpty(),
        fileName = res.fileName.orEmpty(),
        originalName = res.originalName.orEmpty(),
        size = res.size ?: -1,
        isFrontCam = res.isFrontCam ?: true,
        contentType = res.contentType.orEmpty(),
        uploadedAt = res.uploadedAt ?: -1,
    )

    fun mapToFolderDomain(res: FolderResponse) = Folder(
        id = res.id.orEmpty(),
        name = res.name.orEmpty(),
        description = res.description.orEmpty(),
        iconId = res.iconId ?: 1,
        createdAt = res.createdAt ?: -1,
        posterImage = res.posterImage.orEmpty(),
        imageCount = res.imageCount ?: -1,
    )

    fun mapToAuthDomain(res: AuthResponse) = Auth(
        token = res.token.orEmpty(),
        user = mapToUserDomain(res.user)
    )

    private fun mapToUserDomain(res: UserResponse?) = User(
        id = res?.id.orEmpty(),
        username = res?.username.orEmpty(),
        email = res?.email.orEmpty()
    )

}
