package com.logixowl.memocam.ui.utils

import coil3.ImageLoader
import coil3.PlatformContext
import coil3.network.ktor3.KtorNetworkFetcherFactory
import coil3.request.crossfade
import coil3.util.DebugLogger
import io.ktor.client.HttpClient

/**
 * Created by AP-Jake
 * on 06/07/2025
 */

fun getAsyncImageLoader(context: PlatformContext, httpClient: HttpClient) =
    ImageLoader
        .Builder(context)
        .crossfade(true)
        .logger(DebugLogger())
        .components {
            add(
                KtorNetworkFetcherFactory(httpClient)
            )
        }
        .build()
