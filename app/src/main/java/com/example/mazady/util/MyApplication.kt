package com.example.mazady.util

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.example.mazady.di.AppComponent
import com.example.mazady.di.NetworkModule
import com.example.mazady.di.StorageModule
import com.example.mazady.di.ViewModelFactoryModule
import com.example.mazady.di.*

class MyApplication() : Application() {

   var appComponent: AppComponent? = null

    override fun onCreate() {
        super.onCreate()


        appComponent = DaggerAppComponent.builder()
            .networkModule(NetworkModule((baseContext)))
            .storageModule(StorageModule(baseContext))
            .viewModelFactoryModule(ViewModelFactoryModule()).build()


        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)


    }
 }