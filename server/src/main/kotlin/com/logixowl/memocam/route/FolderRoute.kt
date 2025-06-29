package com.logixowl.memocam.route

import com.logixowl.memocam.base.ApiResponse
import com.logixowl.memocam.base.ErrorResponse
import com.logixowl.memocam.request.CreateFolderRequest
import com.logixowl.memocam.response.FolderResponse
import com.logixowl.memocam.service.FolderService
import com.logixowl.memocam.service.ImageService
import io.ktor.http.HttpStatusCode
import io.ktor.server.auth.authenticate
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.principal
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.put
import io.ktor.server.routing.route

/**
 * Created by AP-Jake
 * on 21/06/2025
 */

fun Route.folderRoutes() {
    val folderService = FolderService()
    val imageService = ImageService()

    authenticate("auth-jwt") {
        route("/folders") {
            post {
                val principal = call.principal<JWTPrincipal>()
                val userId = principal!!.payload.getClaim("userId").asString()
                val request = call.receive<CreateFolderRequest>()

                if (request.name.isBlank()) {
                    call.respond(
                        HttpStatusCode.BadRequest,
                        ErrorResponse(message = "Folder name cannot be empty")
                    )
                    return@post
                }

                val folder = folderService.createFolder(userId, request)
                call.respond(
                    HttpStatusCode.Created,
                    ApiResponse(
                        success = true,
                        message = "Folder created successfully",
                        data = FolderResponse(
                            id = folder.id,
                            name = folder.name,
                            createdAt = folder.createdAt,
                        )
                    )
                )
            }

            get {
                val principal = call.principal<JWTPrincipal>()
                val userId = principal!!.payload.getClaim("userId").asString()

                val folders = folderService.getUserFolders(userId)
                call.respond(
                    ApiResponse(
                        success = true,
                        message = "Folders retrieved successfully",
                        data = folders
                    )
                )
            }

            put("/{folderId}/{imageId}") {
                val principal = call.principal<JWTPrincipal>()
                val userId = principal!!.payload.getClaim("userId").asString()
                val folderId = call.parameters["folderId"] ?: return@put call.respond(
                    HttpStatusCode.BadRequest,
                    ErrorResponse(message = "Folder ID is required")
                )
                val imageId = call.parameters["imageId"] ?: return@put call.respond(
                    HttpStatusCode.BadRequest,
                    ErrorResponse(message = "Image ID is required")
                )

                // check folder
                folderService.getFolderById(folderId, userId) ?: return@put call.respond(
                    HttpStatusCode.NotFound,
                    ErrorResponse(message = "Folder not found")
                )

                // check image
                val hasImage = imageService.checkImage(imageId, folderId, userId)
                if (!hasImage) {
                    return@put call.respond(
                        HttpStatusCode.NotFound,
                        ErrorResponse(message = "Image not found")
                    )
                }

                val folder = folderService.updateFolderImage(
                    folderId = folderId,
                    userId = userId,
                    imageId = imageId
                ) ?: return@put call.respond(
                    HttpStatusCode.NotFound,
                    ErrorResponse(message = "Folder not found")
                )

                call.respond(
                    ApiResponse(
                        success = true,
                        message = "Poster image updated successfully",
                        data = folder
                    )
                )
            }

            delete("/{folderId}") {
                val principal = call.principal<JWTPrincipal>()
                val userId = principal!!.payload.getClaim("userId").asString()
                val folderId = call.parameters["folderId"] ?: return@delete call.respond(
                    HttpStatusCode.BadRequest,
                    ErrorResponse(message = "Folder ID is required")
                )

                val deleted = folderService.deleteFolder(folderId, userId)
                if (deleted) {
                    call.respond(
                        ApiResponse(
                            success = true,
                            message = "Folder deleted successfully",
                            data = null
                        )
                    )
                } else {
                    call.respond(
                        HttpStatusCode.NotFound,
                        ErrorResponse(message = "Folder not found")
                    )
                }
            }
        }
    }
}
