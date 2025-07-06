package com.logixowl.memocam.core

import androidx.compose.ui.util.trace

/**
 * Created by AP-Jake
 * on 25/06/2025
 */

fun <T> traceNavigation(navigation: Any, block: (Any) -> T): T =
    trace("Navigation: $navigation") {
        AppLogger.d("Navigation", "navigateTo: $navigation")
        block(navigation)
    }
