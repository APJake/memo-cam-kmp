package com.logixowl.memocam.core

/**
 * Created by AP-Jake
 * on 25/06/2025
 */
 
expect object AppLogger {
    fun e(tag: String, message: String, throwable: Throwable? = null)
    fun d(tag: String, message: String)
    fun i(tag: String, message: String)
}
