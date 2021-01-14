package team.pfm.test.di

import androidx.viewbinding.BuildConfig
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import team.pfm.test.BuildConfig
import team.pfm.test.data.UsersRepository
import team.pfm.test.data.network.UsersApiService
import javax.inject.Singleton

@Module
class NetworkModule {
    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .client(okHttpClient)
            .baseUrl(BuildConfig.REQRES_URL)
            .build()
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder().build()


    @Singleton
    @Provides
    fun provideUsersApi(retrofit: Retrofit): UsersApiService =
        retrofit.create(UsersApiService::class.java)

    @Singleton
    @Provides
    fun provideUsersRepository(api: UsersApiService) = UsersRepository(api)
}