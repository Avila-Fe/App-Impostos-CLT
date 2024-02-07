package com.example.trabalhador

import android.app.Application
import com.example.trabalhador.core.AppComponent

class CltApplication : Application() {
    val appComponent: AppComponent by lazy {

        DaggerAppComponent.factory().create(applicationContext)
    }
}