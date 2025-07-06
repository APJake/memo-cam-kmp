package com.logixowl.memocam.domain.validation.common

/**
 * Created by AP-Jake
 * on 02/07/2025
 */
 
object AppRegex {
    const val EMAIL = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"
    const val USERNAME = "^[A-Za-z _-]+$"
    const val DEFAULT_NAME = "^[\\p{L} _-]+$"
}
