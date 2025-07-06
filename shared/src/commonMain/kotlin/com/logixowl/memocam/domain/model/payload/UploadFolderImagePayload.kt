package com.logixowl.memocam.domain.model.payload

/**
 * Created by AP-Jake
 * on 25/06/2025
 */

class UploadFolderImagePayload(
    val folderId: String,
    val filename: String,
    val mimeType: String,
    val byteArray: ByteArray,
)
