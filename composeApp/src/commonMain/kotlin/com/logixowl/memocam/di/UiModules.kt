package com.logixowl.memocam.di

import com.logixowl.memocam.delegate.FolderImagesCacheDelegate
import com.logixowl.memocam.delegate.FolderImagesCacheDelegateImpl
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

/**
 * Created by AP-Jake
 * on 05/07/2025
 */

val uiModules = module {
    singleOf(::FolderImagesCacheDelegateImpl).bind<FolderImagesCacheDelegate>()
}
