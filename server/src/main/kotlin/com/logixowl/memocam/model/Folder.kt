package com.logixowl.memocam.model

import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

/**
 * Created by AP-Jake
 * on 21/06/2025
 */

@Serializable
data class Folder(
    @BsonId
    val id: String = ObjectId().toString(),
    val name: String,
    val userId: String,
    val posterImage: String? = null,
    val createdAt: Long = System.currentTimeMillis()
)
