package team.pfm.test.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import team.pfm.test.BuildConfig
import team.pfm.test.data.local.UsersDatabase
import javax.inject.Singleton

@Module
class DatabaseModule {
    @Singleton
    @Provides
    fun provideDeviceDatabase(context: Context): UsersDatabase =
        Room.databaseBuilder(context, UsersDatabase::class.java, BuildConfig.DB_NAME).build()
}