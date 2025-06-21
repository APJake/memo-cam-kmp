package com.logixowl.memocam.service

import com.logixowl.memocam.database.DatabaseFactory
import com.logixowl.memocam.model.ImageMetadata
import com.logixowl.memocam.response.ImageResponse
import com.logixowl.memocam.response.PaginatedImagesResponse
import com.mongodb.client.gridfs.model.GridFSUploadOptions
import io.ktor.http.content.PartData
import io.ktor.http.content.streamProvider
import io.ktor.util.cio.writeChannel
import io.ktor.utils.io.asSource
import io.ktor.utils.io.copyAndClose
import io.ktor.utils.io.readByteArray
import io.ktor.utils.io.readRemaining
import io.ktor.utils.io.toByteArray
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.io.buffered
import kotlinx.io.readByteArray
import org.bson.Document
import org.bson.types.ObjectId
import org.litote.kmongo.and
import org.litote.kmongo.eq
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.File

/**
 * Created by AP-Jake
 * on 21/06/2025
 */

class ImageService {
    private val images = DatabaseFactory.images
    private val gridFSBucket = DatabaseFactory.getGridFSBucket()

    suspend fun uploadImage(
        userId: String,
        folderId: String,
        fileItem: PartData.FileItem
    ): ImageMetadata? {
        return withContext(Dispatchers.IO) {
            try {
                val fileName = fileItem.originalFileName ?: return@withContext null
                val contentType = fileItem.contentType?.toString() ?: "application/octet-stream"

                val fileBytes = fileItem.provider().readRemaining().readByteArray()

                if (fileBytes.isEmpty()) {
                    throw Exception("Invalid image")
                }

                // Upload to GridFS using the standard MongoDB driver
                val uploadOptions = GridFSUploadOptions()
                    .metadata(Document("userId", userId).append("folderId", folderId))

                val gridFsId = gridFSBucket.uploadFromStream(
                    fileName,
                    fileBytes.inputStream(),
                    uploadOptions
                )

                fileItem.dispose()

                // Create metadata
                val imageMetadata = ImageMetadata(
                    fileName = fileName,
                    originalName = fileName,
                    folderId = folderId,
                    userId = userId,
                    size = fileBytes.size.toLong(),
                    contentType = contentType,
                    gridFsId = gridFsId.toString()
                )

                images.insertOne(imageMetadata)
                return@withContext imageMetadata
            } catch (e: Exception) {
                e.printStackTrace()
                return@withContext null
            }
        }
    }

    suspend fun getImage(imageId: String, userId: String): Pair<ByteArray, ImageMetadata>? {
        return withContext(Dispatchers.IO) {
            val imageMetadata = images.findOne(
                and(ImageMetadata::id eq imageId, ImageMetadata::userId eq userId)
            ) ?: return@withContext null

            try {
                val outputStream = ByteArrayOutputStream()
                gridFSBucket.downloadToStream(ObjectId(imageMetadata.gridFsId), outputStream)
                return@withContext Pair(outputStream.toByteArray(), imageMetadata)
            } catch (e: Exception) {
                e.printStackTrace()
                return@withContext null
            }
        }
    }

    suspend fun getFolderImages(
        folderId: String,
        userId: String,
        page: Int = 1,
        pageSize: Int = 20
    ): PaginatedImagesResponse {
        val skip = (page - 1) * pageSize

        val totalCount = images.countDocuments(
            and(ImageMetadata::folderId eq folderId, ImageMetadata::userId eq userId)
        )

        val folderImages = images.find(
            and(ImageMetadata::folderId eq folderId, ImageMetadata::userId eq userId)
        )
            .skip(skip)
            .limit(pageSize)
            .toList()

        val imageResponses = folderImages.map { image ->
            ImageResponse(
                id = image.id,
                fileName = image.fileName,
                originalName = image.originalName,
                size = image.size,
                contentType = image.contentType,
                uploadedAt = image.uploadedAt
            )
        }

        val totalPages = ((totalCount + pageSize - 1) / pageSize).toInt()

        return PaginatedImagesResponse(
            images = imageResponses,
            currentPage = page,
            totalPages = totalPages,
            totalCount = totalCount,
            hasNext = page < totalPages,
            hasPrevious = page > 1
        )
    }

    suspend fun deleteImage(imageId: String, userId: String): Boolean {
        return withContext(Dispatchers.IO) {
            val imageMetadata = images.findOne(
                and(ImageMetadata::id eq imageId, ImageMetadata::userId eq userId)
            ) ?: return@withContext false

            try {
                gridFSBucket.delete(ObjectId(imageMetadata.gridFsId))
                images.deleteOneById(imageId)
                return@withContext true
            } catch (e: Exception) {
                e.printStackTrace()
                return@withContext false
            }
        }
    }
}
