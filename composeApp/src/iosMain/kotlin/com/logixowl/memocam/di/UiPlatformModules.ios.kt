package com.logixowl.memocam.di

import com.logixowl.memocam.service.PlatformImageHandler
import org.koin.core.module.Module
import org.koin.dsl.module

/**
 * Created by AP-Jake
 * on 15/07/2025
 */

actual val uiPlatformModule: Module = module {
    single { PlatformImageHandler() }
}
