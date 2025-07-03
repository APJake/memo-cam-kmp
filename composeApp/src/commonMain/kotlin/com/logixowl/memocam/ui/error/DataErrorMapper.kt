package com.logixowl.memocam.ui.error

import com.logixowl.memocam.core.DataError
import memocam.composeapp.generated.resources.Res
import memocam.composeapp.generated.resources.error_local_disk_full
import memocam.composeapp.generated.resources.error_local_unknown
import memocam.composeapp.generated.resources.error_remote_no_internet
import memocam.composeapp.generated.resources.error_remote_request_timeout
import memocam.composeapp.generated.resources.error_remote_serialization
import memocam.composeapp.generated.resources.error_remote_server
import memocam.composeapp.generated.resources.error_remote_too_many_requests
import memocam.composeapp.generated.resources.error_remote_unauthorized
import memocam.composeapp.generated.resources.error_remote_unknown
import org.jetbrains.compose.resources.StringResource

/**
 * Created by AP-Jake
 * on 02/07/2025
 */

val DataError.asStringResource: StringResource
    get() = when (this) {
        is DataError.Remote -> asStringResource
        is DataError.Local -> asStringResource
    }

private val DataError.Local.asStringResource: StringResource
    get() = when (this) {
        DataError.Local.DISK_FULL -> Res.string.error_local_disk_full
        DataError.Local.UNKNOWN -> Res.string.error_local_unknown
    }

private val DataError.Remote.asStringResource: StringResource
    get() = when (this) {
        DataError.Remote.UNAUTHORIZED -> Res.string.error_remote_unauthorized
        DataError.Remote.REQUEST_TIMEOUT -> Res.string.error_remote_request_timeout
        DataError.Remote.TOO_MANY_REQUESTS -> Res.string.error_remote_too_many_requests
        DataError.Remote.NO_INTERNET -> Res.string.error_remote_no_internet
        DataError.Remote.SERVER -> Res.string.error_remote_server
        DataError.Remote.SERIALIZATION -> Res.string.error_remote_serialization
        DataError.Remote.UNKNOWN -> Res.string.error_remote_unknown
    }
