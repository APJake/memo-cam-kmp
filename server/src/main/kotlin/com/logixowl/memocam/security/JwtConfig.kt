package com.logixowl.memocam.security

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import java.util.Date

/**
 * Created by AP-Jake
 * on 21/06/2025
 */

object JwtConfig {
    private const val VALIDITY_MS = 36_000_00 * 24 * 7 // 7 days
    private val algorithm = Algorithm.HMAC256(LocalProperties.secretKey)

    val verifier: JWTVerifier = JWT
        .require(algorithm)
        .withIssuer(LocalProperties.secretIssuer)
        .build()

    fun makeToken(userId: String): String = JWT.create()
        .withSubject("Authentication")
        .withIssuer(LocalProperties.secretIssuer)
        .withClaim("userId", userId)
        .withExpiresAt(getExpiration())
        .sign(algorithm)

    private fun getExpiration() = Date(System.currentTimeMillis() + VALIDITY_MS)
}
