package team.pfm.test

import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import team.pfm.test.di.DaggerAppComponent

class App : DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.factory().create(this)
    }
}