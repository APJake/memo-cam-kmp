package com.logixowl.memocam.di

import com.logixowl.memocam.app.AppViewModel
import com.logixowl.memocam.features.auth.login.LoginViewModel
import com.logixowl.memocam.features.auth.register.RegisterViewModel
import com.logixowl.memocam.features.memo.dashboard.DashboardViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

/**
 * Created by AP-Jake
 * on 25/06/2025
 */

val viewModelModules = module {
    viewModelOf(::AppViewModel)
    viewModelOf(::DashboardViewModel)
    viewModelOf(::LoginViewModel)
    viewModelOf(::RegisterViewModel)
}
