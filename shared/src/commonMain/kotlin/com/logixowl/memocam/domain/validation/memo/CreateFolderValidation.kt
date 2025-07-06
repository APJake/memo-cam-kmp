package com.logixowl.memocam.domain.validation.memo

import com.logixowl.memocam.core.Result
import com.logixowl.memocam.core.Validation
import com.logixowl.memocam.core.ValidationError
import com.logixowl.memocam.domain.model.payload.CreateFolderPayload
import com.logixowl.memocam.domain.validation.common.AppRegex

/**
 * Created by AP-Jake
 * on 02/07/2025
 */

class CreateFolderValidation : Validation<CreateFolderPayload, CreateFolderValidation.Error>() {

    sealed interface Error : ValidationError {
        enum class Name : Error {
            Empty, Invalid,
        }

        enum class Description : Error {
            Invalid,
        }
    }

    override fun invoke(param: CreateFolderPayload): Result<Unit, Error> {
        if (param.name.isBlank()) return error(Error.Name.Empty)

        val regex = AppRegex.DEFAULT_NAME.toRegex()
        if (!regex.matches(param.name)) return error(Error.Name.Invalid)

        if (param.description.isNotBlank() && !regex.matches(param.description)) {
            return error(Error.Description.Invalid)
        }

        return success()
    }
}
