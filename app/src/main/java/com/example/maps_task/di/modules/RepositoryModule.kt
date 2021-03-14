package com.example.maps_task.di.modules

import android.content.Context
import com.example.maps_task.api.PlacesToVisitApi
import com.example.maps_task.repository.exceptions.PlacesToVisitRepository
import com.example.maps_task.repository.exceptions.PlacesToVisitRepositoryImpl
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule(private val context: Context) {

    @Provides
    @Singleton
    fun provideUserRepository(
            placesToVisitApi: PlacesToVisitApi,
            gson: Gson
    ): PlacesToVisitRepository = PlacesToVisitRepositoryImpl(
            placesToVisitApi = placesToVisitApi,
            gson = gson
    )

}