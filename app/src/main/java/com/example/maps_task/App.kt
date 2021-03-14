package com.example.maps_task

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.example.maps_task.di.AppComponent
import com.example.ui_core.base.BaseFragment
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class App : Application(), HasAndroidInjector, Application.ActivityLifecycleCallbacks {

    private val appComponent by lazy {
        AppComponent.create(this)
    }

    @Inject
    internal lateinit var dispatchingAndroidInjectorAny: DispatchingAndroidInjector<Any>

    override fun onCreate() {
        super.onCreate()
        appComponent.inject(this)
        registerActivityLifecycleCallbacks(this)
    }

    override fun onActivityPaused(activity: Activity) {}

    override fun onActivityStarted(activity: Activity) {}

    override fun onActivityDestroyed(activity: Activity) {}

    override fun onActivitySaveInstanceState(activity: Activity, p1: Bundle) {}

    override fun onActivityStopped(activity: Activity) {}

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        if (activity is HasAndroidInjector) {
            AndroidInjection.inject(activity)
        }
        if (activity is FragmentActivity) {
            activity.supportFragmentManager
                    .registerFragmentLifecycleCallbacks(
                            object : FragmentManager.FragmentLifecycleCallbacks() {
                                override fun onFragmentAttached(
                                        fm: FragmentManager,
                                        f: Fragment,
                                        context: Context
                                ) {
                                    if (f is BaseFragment) {
                                        AndroidSupportInjection.inject(f)
                                    }
                                }
                            }, true
                    )
        }
    }

    override fun onActivityResumed(activity: Activity) {}

    override fun androidInjector(): AndroidInjector<Any> = dispatchingAndroidInjectorAny

}