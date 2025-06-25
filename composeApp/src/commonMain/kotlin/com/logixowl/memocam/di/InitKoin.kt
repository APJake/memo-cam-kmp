package com.logixowl.memocam.di

import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

/**
 * Created by AP-Jake
 * on 25/06/2025
 */

fun initKoin(config: KoinAppDeclaration? = null) {
    startKoin {
        config?.invoke(this)
        modules(SharedDI.sharedModule, platformModule, viewModelModules)
    }
}
