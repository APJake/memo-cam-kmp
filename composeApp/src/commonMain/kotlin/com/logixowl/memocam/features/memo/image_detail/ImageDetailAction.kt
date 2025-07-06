package com.logixowl.memocam.features.memo.image_detail

/**
 * Created by AP-Jake
 * on 05/07/2025
 */

sealed interface ImageDetailAction {
    data object OnClickedClose : ImageDetailAction
    data object OnSwipedNext : ImageDetailAction
    data object OnSwipedPrev : ImageDetailAction
}
