package team.pfm.test.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [ViewModelFactoryModule::class, ViewModelModule::class, DatabaseModule::class, NetworkModule::class])
class AppModule {
    @Singleton
    @Provides
    fun provideContext(application: Application): Context = application
}