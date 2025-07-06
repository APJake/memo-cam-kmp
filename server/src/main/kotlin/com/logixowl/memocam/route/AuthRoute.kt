package com.logixowl.memocam.route

import com.logixowl.memocam.base.ApiResponse
import com.logixowl.memocam.base.ErrorResponse
import com.logixowl.memocam.request.LoginRequest
import com.logixowl.memocam.request.RegisterRequest
import com.logixowl.memocam.response.AuthResponse
import com.logixowl.memocam.response.UserResponse
import com.logixowl.memocam.security.JwtConfig
import com.logixowl.memocam.service.UserService
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.post
import io.ktor.server.routing.route

/**
 * Created by AP-Jake
 * on 21/06/2025
 */

fun Route.authRoutes() {
    val userService = UserService()

    route("/auth") {
        post("/register") {
            val request = call.receive<RegisterRequest>()

            if (request.username.isBlank() || request.email.isBlank() || request.password.length < 6) {
                call.respond(
                    HttpStatusCode.BadRequest,
                    ErrorResponse(message = "Invalid input data")
                )
                return@post
            }

            val user = userService.createUser(request)
            if (user == null) {
                call.respond(
                    HttpStatusCode.Conflict,
                    ErrorResponse(message = "Username or email already exists")
                )
                return@post
            }

            val token = JwtConfig.makeToken(user.id)
            call.respond(
                HttpStatusCode.Created,
                ApiResponse(
                    success = true,
                    message = "User created successfully",
                    data = AuthResponse(
                        token = token,
                        user = UserResponse(
                            id = user.id,
                            username = user.username,
                            email = user.email
                        )
                    )
                )
            )
        }

        post("/login") {
            val request = call.receive<LoginRequest>()

            val user = userService.authenticate(request.email, request.password)
            if (user == null) {
                call.respond(
                    HttpStatusCode.Unauthorized,
                    ErrorResponse(message = "Invalid credentials")
                )
                return@post
            }

            val token = JwtConfig.makeToken(user.id)
            call.respond(
                ApiResponse(
                    success = true,
                    message = "Login successful",
                    data = AuthResponse(
                        token = token,
                        user = UserResponse(
                            id = user.id,
                            username = user.username,
                            email = user.email
                        )
                    )
                )
            )
        }
    }
}
