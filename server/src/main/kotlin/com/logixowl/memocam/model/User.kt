package com.logixowl.memocam.model

import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

/**
 * Created by AP-Jake
 * on 21/06/2025
 */

@Serializable
data class User(
    @BsonId
    val id: String = ObjectId().toString(),
    val username: String,
    val email: String,
    val passwordHash: String,
    val createdAt: Long = System.currentTimeMillis()
)
