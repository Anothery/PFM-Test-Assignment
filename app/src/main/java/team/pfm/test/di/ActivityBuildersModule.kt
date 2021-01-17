package team.pfm.test.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import team.pfm.test.ui.main.MainActivity

@Module
abstract class ActivityBuildersModule {
    @ContributesAndroidInjector(modules = [MainFragmentBuildersModule::class])
    abstract fun provideMainActivity(): MainActivity
}