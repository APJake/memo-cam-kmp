package com.logixowl.memocam.core

/**
 * Created by AP-Jake
 * on 22/06/2025
 */

sealed interface DataError : Error {
    enum class Remote : DataError {
        UNAUTHORIZED,
        REQUEST_TIMEOUT,
        TOO_MANY_REQUESTS,
        NO_INTERNET,
        SERVER,
        SERIALIZATION,
        UNKNOWN
    }

    enum class Local : DataError {
        DISK_FULL,
        UNKNOWN
    }
}
