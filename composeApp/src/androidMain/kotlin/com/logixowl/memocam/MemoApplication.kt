package com.logixowl.memocam

import android.app.Application
import com.logixowl.memocam.di.initKoin
import org.koin.android.ext.koin.androidContext

/**
 * Created by AP-Jake
 * on 26/06/2025
 */

class MemoApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@MemoApplication)
        }
    }
}
