package com.logixowl.memocam.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.logixowl.memocam.core.data.createDataStore
import com.logixowl.memocam.core.data.dataStoreFileName
import kotlinx.cinterop.ExperimentalForeignApi
import org.koin.core.module.Module
import org.koin.dsl.module
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSURL
import platform.Foundation.NSUserDomainMask

/**
 * Created by AP-Jake
 * on 25/06/2025
 */

actual val platformModule: Module = module {
    single { dataStore() }
}

@OptIn(ExperimentalForeignApi::class)
private fun dataStore(): DataStore<Preferences> = createDataStore(
    producePath = {
        val documentDirectory: NSURL? = NSFileManager.defaultManager.URLForDirectory(
            directory = NSDocumentDirectory,
            inDomain = NSUserDomainMask,
            appropriateForURL = null,
            create = false,
            error = null,
        )
        requireNotNull(documentDirectory).path + "/$dataStoreFileName"
    }
)
