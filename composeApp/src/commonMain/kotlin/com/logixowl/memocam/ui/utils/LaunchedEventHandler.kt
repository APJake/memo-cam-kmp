package com.logixowl.memocam.ui.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.logixowl.memocam.core.AppLogger
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest

/**
 * Created by AP-Jake
 * on 28/06/2025
 */

@Composable
fun <E> LaunchedEventHandler(
    event: Flow<E>,
    onEvent: suspend (event: E) -> Unit
) {
    LaunchedEffect(true) {
        event.collectLatest {
            AppLogger.d("LaunchedEventHandler", "onEvent: $it")
            onEvent(it)
        }
    }
}
