package com.logixowl.memocam.plugin

import com.logixowl.memocam.security.JwtConfig
import com.logixowl.memocam.service.UserService
import io.ktor.server.application.Application
import io.ktor.server.auth.authentication
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.jwt.jwt

/**
 * Created by AP-Jake
 * on 21/06/2025
 */

fun Application.configureSecurity() {
    val userService = UserService()

    authentication {
        jwt("auth-jwt") {
            realm = "ktor-app"
            verifier(JwtConfig.verifier)
            validate { credential ->
                val userId = credential.payload.getClaim("userId").asString()
                if (userId != null) {
                    val user = userService.findById(userId)
                    if (user != null) {
                        JWTPrincipal(credential.payload)
                    } else null
                } else null
            }
        }
    }
}
