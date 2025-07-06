package com.logixowl.memocam.ui.error

import com.logixowl.memocam.core.ValidationError
import com.logixowl.memocam.domain.validation.auth.LoginValidation
import com.logixowl.memocam.domain.validation.auth.RegisterValidation
import com.logixowl.memocam.domain.validation.memo.CreateFolderValidation
import memocam.composeapp.generated.resources.Res
import memocam.composeapp.generated.resources.error_common
import memocam.composeapp.generated.resources.error_email_empty
import memocam.composeapp.generated.resources.error_email_invalid
import memocam.composeapp.generated.resources.error_name_empty
import memocam.composeapp.generated.resources.error_name_invalid
import memocam.composeapp.generated.resources.error_password1_empty
import memocam.composeapp.generated.resources.error_password1_need_char
import memocam.composeapp.generated.resources.error_password1_need_digit
import memocam.composeapp.generated.resources.error_password1_too_short
import memocam.composeapp.generated.resources.error_password2_empty
import memocam.composeapp.generated.resources.error_password2_not_match
import memocam.composeapp.generated.resources.error_username_empty
import memocam.composeapp.generated.resources.error_username_invalid
import memocam.composeapp.generated.resources.error_username_too_short
import org.jetbrains.compose.resources.StringResource

/**
 * Created by AP-Jake
 * on 02/07/2025
 */

val ValidationError.asStringResource: StringResource
    get() = when (this) {
        is RegisterValidation.Error -> {
            when (this) {
                RegisterValidation.Error.Email.Empty -> Res.string.error_email_empty
                RegisterValidation.Error.Email.Invalid -> Res.string.error_email_invalid
                RegisterValidation.Error.Password1.Empty -> Res.string.error_password1_empty
                RegisterValidation.Error.Password1.TooShort -> Res.string.error_password1_too_short
                RegisterValidation.Error.Password1.NeedDigit -> Res.string.error_password1_need_digit
                RegisterValidation.Error.Password1.NeedChar -> Res.string.error_password1_need_char
                RegisterValidation.Error.Password2.Empty -> Res.string.error_password2_empty
                RegisterValidation.Error.Password2.NotMatch -> Res.string.error_password2_not_match
                RegisterValidation.Error.Username.Empty -> Res.string.error_username_empty
                RegisterValidation.Error.Username.Invalid -> Res.string.error_username_invalid
                RegisterValidation.Error.Username.TooShort -> Res.string.error_username_too_short
            }
        }

        is LoginValidation.Error -> {
            when (this) {
                LoginValidation.Error.Email.Empty -> Res.string.error_email_empty
                LoginValidation.Error.Email.Invalid -> Res.string.error_email_invalid
                LoginValidation.Error.Password.Empty -> Res.string.error_password1_empty
            }
        }

        is CreateFolderValidation.Error -> {
            when (this) {
                CreateFolderValidation.Error.Description.Invalid -> Res.string.error_name_invalid
                CreateFolderValidation.Error.Name.Empty -> Res.string.error_name_empty
                CreateFolderValidation.Error.Name.Invalid -> Res.string.error_name_invalid
            }
        }

        else -> Res.string.error_common
    }
