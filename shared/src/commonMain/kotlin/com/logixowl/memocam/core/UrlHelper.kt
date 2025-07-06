package com.logixowl.memocam.core

import com.logixowl.memocam.data.network.util.AppUrl

/**
 * Created by AP-Jake
 * on 06/07/2025
 */

/**
 * Not the same with [AppUrl] object,
 * this one is for public
 */
object UrlHelper {

    fun asImageUrl(imageId: String): String {
        return "${AppUrl.imageBaseUrl}/$imageId"
    }

}
