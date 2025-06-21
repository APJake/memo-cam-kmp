package com.logixowl.memocam.plugin

import com.logixowl.memocam.database.DatabaseFactory
import io.ktor.server.application.Application

/**
 * Created by AP-Jake
 * on 21/06/2025
 */

fun Application.configureDatabase() {
    DatabaseFactory.init()
}
