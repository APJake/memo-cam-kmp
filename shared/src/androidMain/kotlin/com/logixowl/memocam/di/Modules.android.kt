package com.logixowl.memocam.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.logixowl.memocam.core.data.createDataStore
import com.logixowl.memocam.core.data.dataStoreFileName
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

/**
 * Created by AP-Jake
 * on 25/06/2025
 */

actual val platformModule: Module = module {
    single<HttpClientEngine> { OkHttp.create() }
    single { dataStore(androidContext()) }
}

private fun dataStore(context: Context): DataStore<Preferences> =
    createDataStore(
        producePath = { context.filesDir.resolve(dataStoreFileName).absolutePath }
    )
