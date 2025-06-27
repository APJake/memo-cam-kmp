package com.logixowl.memocam.di

import com.logixowl.memocam.data.network.datasource.AuthNetworkDataSourceImpl
import com.logixowl.memocam.data.network.datasource.MemoNetworkDataSourceImpl
import com.logixowl.memocam.data.network.util.HttpClientFactory
import com.logixowl.memocam.data.prefs.MainPrefsDataSource
import com.logixowl.memocam.data.repository.AuthRepositoryImpl
import com.logixowl.memocam.data.repository.MemoRepositoryImpl
import com.logixowl.memocam.data.repository.PrefsRepositoryImpl
import com.logixowl.memocam.domain.datasource.AuthNetworkDataSource
import com.logixowl.memocam.domain.datasource.MemoNetworkDataSource
import com.logixowl.memocam.domain.datasource.PrefsDataSource
import com.logixowl.memocam.domain.repository.AuthRepository
import com.logixowl.memocam.domain.repository.MemoRepository
import com.logixowl.memocam.domain.repository.PrefsRepository
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import kotlin.jvm.JvmStatic

/**
 * Created by AP-Jake
 * on 25/06/2025
 */

expect val platformModule: Module

object SharedDI {
    @JvmStatic
    val sharedModule = module {
        single { HttpClientFactory.create(get(), get()) }

        singleOf(::MainPrefsDataSource).bind<PrefsDataSource>()
        singleOf(::AuthNetworkDataSourceImpl).bind<AuthNetworkDataSource>()
        singleOf(::MemoNetworkDataSourceImpl).bind<MemoNetworkDataSource>()

        // repository
        singleOf(::PrefsRepositoryImpl).bind<PrefsRepository>()
        singleOf(::AuthRepositoryImpl).bind<AuthRepository>()
        singleOf(::MemoRepositoryImpl).bind<MemoRepository>()
    }
}
