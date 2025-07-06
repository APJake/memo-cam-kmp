package com.logixowl.memocam.mapper

import com.logixowl.memocam.core.UrlHelper
import com.logixowl.memocam.domain.model.Folder
import com.logixowl.memocam.domain.model.FolderImage
import com.logixowl.memocam.model.FolderIcon
import com.logixowl.memocam.model.FolderImageUiModel
import com.logixowl.memocam.model.FolderUiModel
import com.logixowl.memocam.ui.extensions.asImageVector
import com.logixowl.memocam.ui.extensions.fromInt

/**
 * Created by AP-Jake
 * on 28/06/2025
 */

fun Folder.toUiModel() = FolderUiModel(
    id = id,
    title = name,
    description = description,
    icon = FolderIcon.fromInt(iconId).asImageVector,
    lastUpdated = this.createdAt.toString(),
    itemCount = this.imageCount,
)

fun FolderImage.toUiModel(dayNo: Int) = FolderImageUiModel(
    id = id,
    fileName = fileName,
    originalName = originalName,
    size = size,
    isFrontCam = isFrontCam,
    contentType = contentType,
    uploadedAt = uploadedAt,
    url = UrlHelper.asImageUrl(id),
    dayNumber = "Day - $dayNo"
)
