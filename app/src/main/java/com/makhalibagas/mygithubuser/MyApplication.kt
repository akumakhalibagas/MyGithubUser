package com.makhalibagas.mygithubuser

import android.app.Application
import com.makhalibagas.mygithubuser.di.modulesList
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApplication)
            modules(modulesList)
        }
    }


}