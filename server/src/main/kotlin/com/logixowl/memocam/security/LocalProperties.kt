package com.logixowl.memocam.security

import java.io.FileNotFoundException
import java.util.Properties

/**
 * Created by AP-Jake
 * on 21/06/2025
 */

object LocalProperties {
    private var properties: Properties = Properties()

    init {
        val inputStream = {}.javaClass.classLoader?.getResourceAsStream("server.properties")
            ?: throw FileNotFoundException("server.properties not found in resources")
        properties.load(inputStream)
    }

    val serverPort = properties.getProperty("port").toIntOrNull()?: 8080

    val mongoDbUrl = properties.getProperty("mongo.url")
    val mongoDbName = properties.getProperty("mongo.database")
    val mongoBucket = properties.getProperty("mongo.bucket")

    val secretKey = properties.getProperty("secret.key")
    val secretIssuer = properties.getProperty("secret.issuer")

}
