package com.logixowl.memocam.app

/**
 * Created by AP-Jake
 * on 26/06/2025
 */

sealed interface AppEvent {
    data class Error(val error: String) : AppEvent
}
