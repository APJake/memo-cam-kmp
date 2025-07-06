package com.logixowl.memocam.ui.error

import com.logixowl.memocam.core.DataError
import com.logixowl.memocam.core.Error
import com.logixowl.memocam.core.ValidationError
import memocam.composeapp.generated.resources.Res
import memocam.composeapp.generated.resources.error_common
import org.jetbrains.compose.resources.StringResource

/**
 * Created by AP-Jake
 * on 02/07/2025
 */


val Error.asStringResource: StringResource
    get() = when (this) {
        is DataError -> this.asStringResource
        is ValidationError -> this.asStringResource
        else -> Res.string.error_common
    }
