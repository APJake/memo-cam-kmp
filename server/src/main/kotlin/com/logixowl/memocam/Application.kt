package com.logixowl.memocam

import com.logixowl.memocam.plugin.configureDatabase
import com.logixowl.memocam.plugin.configureHTTP
import com.logixowl.memocam.plugin.configureMonitoring
import com.logixowl.memocam.plugin.configureRouting
import com.logixowl.memocam.plugin.configureSecurity
import com.logixowl.memocam.plugin.configureSerialization
import com.logixowl.memocam.security.LocalProperties
import io.ktor.server.application.Application
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty

fun main() {
    embeddedServer(
        Netty,
        port = LocalProperties.serverPort,
        host = "0.0.0.0",
        module = Application::module
    )
        .start(wait = true)
}

fun Application.module() {
    configureDatabase()
    configureSecurity()
    configureSerialization()
    configureMonitoring()
    configureHTTP()
    configureRouting()
}
