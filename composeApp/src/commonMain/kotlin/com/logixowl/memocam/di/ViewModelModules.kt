package com.logixowl.memocam.di

import com.logixowl.memocam.app.AppViewModel
import com.logixowl.memocam.features.auth.login.LoginViewModel
import com.logixowl.memocam.features.auth.register.RegisterViewModel
import com.logixowl.memocam.features.memo.create_folder.CreateFolderViewModel
import com.logixowl.memocam.features.memo.dashboard.DashboardViewModel
import com.logixowl.memocam.features.memo.folder_detail.FolderDetailViewModel
import com.logixowl.memocam.features.memo.image_detail.ImageDetailViewModel
import com.logixowl.memocam.features.memo.take_picture.TakePictureViewModel
import com.logixowl.memocam.features.settings.SettingsViewModel
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
    viewModelOf(::SettingsViewModel)
    viewModelOf(::CreateFolderViewModel)
    viewModelOf(::TakePictureViewModel)
    viewModelOf(::FolderDetailViewModel)
    viewModelOf(::ImageDetailViewModel)
}
