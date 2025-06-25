package com.logixowl.memocam.features.auth.register

/**
 * Created by AP-Jake
 * on 25/06/2025
 */

sealed interface RegisterEvent {
    data class Error(val error: String) : RegisterEvent
    data object RegisterSuccess : RegisterEvent
}
