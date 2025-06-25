package com.logixowl.memocam.features.memo.dashboard

import com.logixowl.memocam.features.auth.login.LoginEvent

/**
 * Created by AP-Jake
 * on 25/06/2025
 */

sealed interface DashboardEvent {
    data class Error(val error: String) : DashboardEvent
}
