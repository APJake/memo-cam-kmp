package com.logixowl.memocam.core

/**
 * Created by AP-Jake
 * on 06/07/2025
 */

expect object FileManager {
    fun deleteFile(filePath: String): Boolean
}
