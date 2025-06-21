package com.logixowl.memocam.plugin

import com.logixowl.memocam.route.authRoutes
import com.logixowl.memocam.route.folderRoutes
import com.logixowl.memocam.route.imageRoutes
import io.ktor.server.application.Application
import io.ktor.server.routing.routing

/**
 * Created by AP-Jake
 * on 21/06/2025
 */

fun Application.configureRouting() {
    routing {
        authRoutes()
        folderRoutes()
        imageRoutes()
    }
}
