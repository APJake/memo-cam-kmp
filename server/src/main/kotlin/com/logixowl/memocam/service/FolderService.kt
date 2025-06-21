package com.logixowl.memocam.service

import com.logixowl.memocam.database.DatabaseFactory
import com.logixowl.memocam.model.Folder
import com.logixowl.memocam.model.ImageMetadata
import com.logixowl.memocam.request.CreateFolderRequest
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

    suspend fun createFolder(userId: String, request: CreateFolderRequest): Folder {
        val folder = Folder(
            name = request.name,
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
                createdAt = folder.createdAt,
                imageCount = imageCount
            )
        }
    }

    suspend fun getFolderById(folderId: String, userId: String): Folder? {
        return folders.findOne(and(Folder::id eq folderId, Folder::userId eq userId))
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
