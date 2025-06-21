package com.logixowl.memocam

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform