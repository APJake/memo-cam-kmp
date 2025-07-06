package com.logixowl.memocam.features.memo.dashboard

/**
 * Created by AP-Jake
 * on 25/06/2025
 */

sealed interface DashboardEvent {
    data object OnNavigateFolderDetail : DashboardEvent
    data class Error(val error: String) : DashboardEvent
}
