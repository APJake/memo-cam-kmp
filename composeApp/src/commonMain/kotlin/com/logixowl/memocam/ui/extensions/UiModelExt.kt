package com.logixowl.memocam.ui.extensions

import com.logixowl.memocam.model.FolderImageUiModel
import kotlin.random.Random

/**
 * Created by AP-Jake
 * on 05/07/2025
 */

fun FolderImageUiModel.Companion.preview(): FolderImageUiModel {
    val randomId = Random.nextLong()
    return FolderImageUiModel(
        id = randomId.toString(),
        url = randomId.toString(),
        fileName = "File - $randomId",
        originalName = "Original file - $randomId",
        size = randomId,
        isFrontCam = randomId % 2 == 0L,
        contentType = "image/jpg",
        uploadedAt = 1234,
        dayNumber = "Day - ${Random.nextInt()}"
    )
}
