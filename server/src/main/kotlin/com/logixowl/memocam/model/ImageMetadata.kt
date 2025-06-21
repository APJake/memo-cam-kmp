package com.logixowl.memocam.model

import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

/**
 * Created by AP-Jake
 * on 21/06/2025
 */

@Serializable
data class ImageMetadata(
    @BsonId
    val id: String = ObjectId().toString(),
    val fileName: String,
    val originalName: String,
    val folderId: String,
    val userId: String,
    val size: Long,
    val contentType: String,
    val uploadedAt: Long = System.currentTimeMillis(),
    val gridFsId: String // MongoDB GridFS file ID
)
