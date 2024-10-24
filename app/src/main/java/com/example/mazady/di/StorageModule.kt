package com.example.mazady.di

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import com.example.mazady.util.Constants

@Module
class StorageModule (var context: Context){


    @Provides
    fun provideSharedPreference() : SharedPreferences =

      context.getSharedPreferences(Constants.SHARED_NAME,Context.MODE_PRIVATE)


}