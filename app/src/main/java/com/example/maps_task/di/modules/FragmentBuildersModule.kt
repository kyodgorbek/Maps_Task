package com.example.maps_task.di.modules

import com.example.maps_task.ui.main_page.MainPageFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeMainPageFragment(): MainPageFragment

}