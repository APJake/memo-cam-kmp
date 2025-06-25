package com.logixowl.memocam.features.memo.dashboard

/**
 * Created by AP-Jake
 * on 25/06/2025
 */

sealed interface DashboardAction {
    data object OnClickedCreateFolder : DashboardAction
    data object OnClickedSettings : DashboardAction
    data class OnClickedFolder(val folderId: String) : DashboardAction
    data object OnClickedRefresh : DashboardAction
}
