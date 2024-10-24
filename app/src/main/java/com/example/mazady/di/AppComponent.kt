package com.example.mazady.di

import com.example.mazady.ui.activity.home.HomePage
import dagger.Component


@Component(modules = [
    NetworkModule::class,
    StorageModule::class,
    ViewModelFactoryModule::class
])
interface AppComponent {

    fun inject(homePage: HomePage)

}