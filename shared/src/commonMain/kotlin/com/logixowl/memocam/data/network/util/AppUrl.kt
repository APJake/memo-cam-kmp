package com.logixowl.memocam.data.network.util

/**
 * Created by AP-Jake
 * on 24/06/2025
 */

internal object AppUrl {
    private const val BASE_URL = "http://10.0.2.2:9009"

    // authentication
    val login: String
        get() = "$BASE_URL/auth/login"
    val register: String
        get() = "$BASE_URL/auth/register"

    // folder
    val createFolder: String
        get() = "$BASE_URL/folders"
    val allFolder: String
        get() = "$BASE_URL/folders"

    fun deleteFolder(id: String): String = "$BASE_URL/folders/$id"
    fun updateFolder(id: String): String = "$BASE_URL/folders/$id"

    // images
    fun allFolderImage(folderId: String): String = "$BASE_URL/folders/$folderId"
    fun uploadFolderImage(folderId: String): String = "$BASE_URL/images/upload/$folderId"
    fun deleteFolderImage(imageId: String): String = "$BASE_URL/images/$imageId"
    fun getFolderImage(
        imageId: String,
        page: Int,
        pageSize: Int,
    ): String = "$BASE_URL/images/$imageId?page=$page,pageSize:$pageSize"

}
