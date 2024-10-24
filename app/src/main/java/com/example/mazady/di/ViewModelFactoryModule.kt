package com.example.mazady.di

import androidx.lifecycle.ViewModel
import dagger.Module
import javax.inject.Named
import androidx.lifecycle.ViewModelProvider.Factory
import com.example.mazady.data.repo.AdminRepo
import com.example.mazady.ui.activity.home.HomeViewModel
import dagger.Provides


@Module
class ViewModelFactoryModule  {


    @Provides
    @Named("home")
    fun provideHomeViewModel(adminRepo: AdminRepo): Factory {
        return object : Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return HomeViewModel(adminRepo) as T
            }
        }
    }

}