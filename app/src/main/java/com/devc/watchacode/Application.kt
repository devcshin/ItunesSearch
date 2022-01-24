package com.devc.watchacode

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class Application : Application() {
    init{
        instance = this
    }

    //어디서든지 context를 받기위한 객체
    companion object {
        lateinit var instance: Application
        fun ApplicationContext() : Context {
            return instance.applicationContext
        }
    }

}