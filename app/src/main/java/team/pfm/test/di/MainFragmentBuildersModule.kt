package team.pfm.test.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import team.pfm.test.ui.edituserdetails.EditUserDetailsFragment
import team.pfm.test.ui.userdetails.UserDetailsFragment

@Module
abstract class MainFragmentBuildersModule {

    @ContributesAndroidInjector
    abstract fun provideUserDetailsFragment(): UserDetailsFragment

    @ContributesAndroidInjector
    abstract fun provideEditUserDetailsFragment(): EditUserDetailsFragment
}