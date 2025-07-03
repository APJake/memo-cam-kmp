package com.logixowl.memocam.domain.validation.auth

import com.logixowl.memocam.core.Result
import com.logixowl.memocam.core.Validation
import com.logixowl.memocam.core.ValidationError
import com.logixowl.memocam.domain.model.payload.LoginPayload
import com.logixowl.memocam.domain.validation.common.AppRegex

/**
 * Created by AP-Jake
 * on 02/07/2025
 */

class LoginValidation : Validation<LoginPayload, LoginValidation.Error>() {

    sealed interface Error : ValidationError {
        enum class Email : Error {
            Empty, Invalid
        }

        enum class Password : Error {
            Empty,
        }
    }

    override fun invoke(param: LoginPayload): Result<Unit, Error> {
        if (param.email.isBlank()) return error(Error.Email.Empty)

        val regex = AppRegex.EMAIL.toRegex()
        if (!regex.matches(param.email))
            return Result.Error(Error.Email.Invalid)

        if (param.password.isBlank()) return error(Error.Password.Empty)

        return success()
    }
}
