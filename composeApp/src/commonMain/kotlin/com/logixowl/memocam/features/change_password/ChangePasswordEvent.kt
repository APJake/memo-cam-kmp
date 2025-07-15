package com.logixowl.memocam.features.change_password

/**
 * Created by AP-Jake
 * on 08/07/2025
 */

sealed interface ChangePasswordEvent {
    data object OnSuccess : ChangePasswordEvent
}
