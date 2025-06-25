package com.logixowl.memocam.features.auth.login

/**
 * Created by AP-Jake
 * on 25/06/2025
 */

sealed interface LoginEvent {
    data class Error(val error: String) : LoginEvent
    data object LoginSuccess: LoginEvent
}
