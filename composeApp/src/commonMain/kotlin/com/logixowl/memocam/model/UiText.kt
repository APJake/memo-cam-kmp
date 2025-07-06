package com.logixowl.memocam.model

import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

/**
 * Created by AP-Jake
 * on 02/07/2025
 */

sealed interface UiText {

    data class DynamicString(val value: String) : UiText
    class Resource(
        val value: StringResource,
    ) : UiText

    @Composable
    fun asString(): String {
        return when (this) {
            is DynamicString -> value
            is Resource -> stringResource(value)
        }
    }
}
