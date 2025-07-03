package com.logixowl.memocam.domain.validation.auth

import com.logixowl.memocam.core.Result
import com.logixowl.memocam.core.Validation
import com.logixowl.memocam.core.ValidationError
import com.logixowl.memocam.domain.model.payload.RegisterPayload
import com.logixowl.memocam.domain.validation.common.AppRegex

/**
 * Created by AP-Jake
 * on 02/07/2025
 */

class RegisterValidation : Validation<RegisterPayload, RegisterValidation.Error>() {

    sealed interface Error : ValidationError {
        enum class Username : Error {
            Empty, Invalid, TooShort,
        }

        enum class Email : Error {
            Empty, Invalid,
        }

        enum class Password1 : Error {
            Empty, TooShort, NeedDigit, NeedChar,
        }

        enum class Password2 : Error {
            Empty, NotMatch
        }

    }

    override fun invoke(param: RegisterPayload): Result<Unit, Error> {
        // username
        if (param.username.isBlank()) {
            return error(Error.Username.Empty)
        }

        if (param.username.length < 3) {
            return error(Error.Username.TooShort)
        }

        val usernameRegex = AppRegex.USERNAME.toRegex()
        if (!usernameRegex.matches(param.username)) {
            return Result.Error(Error.Username.Invalid)
        }

        // email
        if (param.email.isBlank())
            return Result.Error(Error.Email.Empty)

        val regex = AppRegex.EMAIL.toRegex()
        if (!regex.matches(param.email))
            return Result.Error(Error.Email.Invalid)

        // password
        if (param.password.isBlank()) {
            return error(Error.Password1.Empty)
        }

        if (param.password.length < 6) {
            return error(Error.Password1.TooShort)
        }

        val hasLetter = param.password.any { it.isLetter() }
        if (!hasLetter) {
            return error(Error.Password1.NeedChar)
        }

        val hasDigit = param.password.any { it.isDigit() }
        if (!hasDigit) {
            return error(Error.Password1.NeedDigit)
        }

        // password 2
        if (param.confirmPassword.isBlank()) {
            return error(Error.Password2.Empty)
        }

        if (param.password != param.confirmPassword) {
            return error(Error.Password2.NotMatch)
        }

        return Result.Success(Unit)
    }
}
