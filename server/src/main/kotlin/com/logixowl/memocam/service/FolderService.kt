package com.logixowl.memocam.service

import com.logixowl.memocam.database.DatabaseFactory
import com.logixowl.memocam.model.Folder
import com.logixowl.memocam.model.ImageMetadata
import com.logixowl.memocam.request.FolderRequest
import com.logixowl.memocam.response.FolderResponse
import org.litote.kmongo.and
import org.litote.kmongo.eq

/**
 * Created by AP-Jake
 * on 21/06/2025
 */

class FolderService {
    private val folders = DatabaseFactory.folders
    private val images = DatabaseFactory.images

    suspend fun createFolder(userId: String, request: FolderRequest): Folder {
        val folder = Folder(
            name = request.name,
            description = request.description.orEmpty(),
            iconId = request.iconId,
            userId = userId
        )
        folders.insertOne(folder)
        return folder
    }

    suspend fun getUserFolders(userId: String): List<FolderResponse> {
        val userFolders = folders.find(Folder::userId eq userId).toList()
        return userFolders.map { folder ->
            val imageCount = images.countDocuments(ImageMetadata::folderId eq folder.id).toInt()
            FolderResponse(
                id = folder.id,
                name = folder.name,
                description = folder.description,
                iconId = folder.iconId,
                createdAt = folder.createdAt,
                posterImage = folder.posterImage,
                imageCount = imageCount,
            )
        }
    }

    suspend fun getFolderById(folderId: String, userId: String): Folder? {
        return folders.findOne(and(Folder::id eq folderId, Folder::userId eq userId))
    }

    suspend fun updateFolderById(userId: String, folderId: String, request: FolderRequest): Folder? {
        val oldFolder = getFolderById(folderId, userId)?: return null
        val newName = request.name
            .takeIf { it.isNotBlank() }?: oldFolder.name
        val newDescription = request.description
            .takeIf { !it.isNullOrBlank() }?: oldFolder.description
        val newIconId = request.iconId
        val newPosterImageId = request.posterImage
            .takeIf { !it.isNullOrBlank() }?: oldFolder.posterImage

        val folder = oldFolder.copy(
            name = newName,
            description = newDescription,
            iconId = newIconId,
            posterImage = newPosterImageId
        )

        folders.replaceOneById(
            id = folderId,
            replacement = folder
        )
        return folder
    }

    suspend fun deleteFolder(folderId: String, userId: String): Boolean {
        val folder = getFolderById(folderId, userId) ?: return false

        // Delete all images in the folder
        val folderImages = images.find(ImageMetadata::folderId eq folderId).toList()
        folderImages.forEach { image ->
            try {
                DatabaseFactory.getGridFSBucket().delete(org.bson.types.ObjectId(image.gridFsId))
            } catch (e: Exception) {
                // Log error but continue
            }
        }
        images.deleteMany(ImageMetadata::folderId eq folderId)

        // Delete the folder
        folders.deleteOneById(folderId)
        return true
    }
}
