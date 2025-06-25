package com.logixowl.memocam.features.auth.login

/**
 * Created by AP-Jake
 * on 25/06/2025
 */

sealed interface LoginAction {

    data object OnClickLogin : LoginAction
    data object OnClickRegister : LoginAction

    data class OnChangedUsername(val value: String) : LoginAction
    data class OnChangedPassword(val value: String) : LoginAction
    data object OnToggledPasswordVisibility : LoginAction

}
