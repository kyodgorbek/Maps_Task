package com.example.maps_task.di.modules

import android.content.Context
import android.preference.PreferenceManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class StorageModule(private val context: Context) {

    @Singleton
    @Provides
    internal fun providePreference() = PreferenceManager.getDefaultSharedPreferences(context)
}