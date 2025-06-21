package com.logixowl.memocam.route

import com.logixowl.memocam.base.ApiResponse
import com.logixowl.memocam.base.ErrorResponse
import com.logixowl.memocam.model.ImageMetadata
import com.logixowl.memocam.response.ImageResponse
import com.logixowl.memocam.service.FolderService
import com.logixowl.memocam.service.ImageService
import io.ktor.http.ContentDisposition
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.content.PartData
import io.ktor.http.content.forEachPart
import io.ktor.server.auth.authenticate
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.principal
import io.ktor.server.request.receiveChannel
import io.ktor.server.request.receiveMultipart
import io.ktor.server.response.header
import io.ktor.server.response.respond
import io.ktor.server.response.respondBytes
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import io.ktor.util.cio.writeChannel
import io.ktor.utils.io.copyAndClose
import java.io.File

/**
 * Created by AP-Jake
 * on 21/06/2025
 */

fun Route.imageRoutes() {
    val imageService = ImageService()
    val folderService = FolderService()

    authenticate("auth-jwt") {
        route("/images") {
            post("/upload/{folderId}") {
                val principal = call.principal<JWTPrincipal>()
                val userId = principal!!.payload.getClaim("userId").asString()
                val folderId = call.parameters["folderId"] ?: return@post call.respond(
                    HttpStatusCode.BadRequest,
                    ErrorResponse(message = "Folder ID is required")
                )

                // Verify folder belongs to user
                val folder = folderService.getFolderById(folderId, userId)
                if (folder == null) {
                    call.respond(
                        HttpStatusCode.NotFound,
                        ErrorResponse(message = "Folder not found")
                    )
                    return@post
                }

                val multipartData = call.receiveMultipart(1024 * 1024 * 100)
                var imageMetadata: ImageMetadata? = null

                multipartData.forEachPart { part ->
                    if (part is PartData.FileItem) {
                        imageMetadata = imageService.uploadImage(userId, folderId, part)
                    } else {
                        part.dispose()
                    }
                }

                if (imageMetadata == null) {
                    call.respond(
                        HttpStatusCode.InternalServerError,
                        ErrorResponse(message = "Failed to upload image")
                    )
                    return@post
                }

                // update poster image if required
                if (folder.posterImage.isNullOrBlank()) {
                    folderService.updateFolderImage(
                        folderId = folderId,
                        userId = userId,
                        imageId = imageMetadata!!.id
                    )
                }

                call.respond(
                    HttpStatusCode.Created,
                    ApiResponse(
                        success = true,
                        message = "Image uploaded successfully",
                        data = ImageResponse(
                            id = imageMetadata!!.id,
                            fileName = imageMetadata!!.fileName,
                            originalName = imageMetadata!!.originalName,
                            size = imageMetadata!!.size,
                            contentType = imageMetadata!!.contentType,
                            uploadedAt = imageMetadata!!.uploadedAt
                        )
                    )
                )
            }

            get("/{imageId}") {
                val principal = call.principal<JWTPrincipal>()
                val userId = principal!!.payload.getClaim("userId").asString()
                val imageId = call.parameters["imageId"] ?: return@get call.respond(
                    HttpStatusCode.BadRequest,
                    ErrorResponse(message = "Image ID is required")
                )

                val imageData = imageService.getImage(imageId, userId)
                if (imageData == null) {
                    call.respond(
                        HttpStatusCode.NotFound,
                        ErrorResponse(message = "Image not found")
                    )
                    return@get
                }

                val (bytes, metadata) = imageData
                call.response.header(
                    HttpHeaders.ContentDisposition,
                    ContentDisposition.Attachment.withParameter(
                        ContentDisposition.Parameters.FileName,
                        metadata.originalName
                    ).toString()
                )
                call.respondBytes(bytes, ContentType.parse(metadata.contentType))
            }

            get("/folder/{folderId}") {
                val principal = call.principal<JWTPrincipal>()
                val userId = principal!!.payload.getClaim("userId").asString()
                val folderId = call.parameters["folderId"] ?: return@get call.respond(
                    HttpStatusCode.BadRequest,
                    ErrorResponse(message = "Folder ID is required")
                )

                val page = call.request.queryParameters["page"]?.toIntOrNull() ?: 1
                val pageSize = call.request.queryParameters["pageSize"]?.toIntOrNull() ?: 20

                // Verify folder belongs to user
                val folder = folderService.getFolderById(folderId, userId)
                if (folder == null) {
                    call.respond(
                        HttpStatusCode.NotFound,
                        ErrorResponse(message = "Folder not found")
                    )
                    return@get
                }

                val paginatedImages = imageService.getFolderImages(folderId, userId, page, pageSize)
                call.respond(
                    ApiResponse(
                        success = true,
                        message = "Images retrieved successfully",
                        data = paginatedImages
                    )
                )
            }

            delete("/{imageId}") {
                val principal = call.principal<JWTPrincipal>()
                val userId = principal!!.payload.getClaim("userId").asString()
                val imageId = call.parameters["imageId"] ?: return@delete call.respond(
                    HttpStatusCode.BadRequest,
                    ErrorResponse(message = "Image ID is required")
                )

                val deleted = imageService.deleteImage(imageId, userId)
                if (deleted) {
                    call.respond(
                        ApiResponse(
                            success = true,
                            message = "Image deleted successfully",
                            data = null
                        )
                    )
                } else {
                    call.respond(
                        HttpStatusCode.NotFound,
                        ErrorResponse(message = "Image not found")
                    )
                }
            }
        }
    }
}
