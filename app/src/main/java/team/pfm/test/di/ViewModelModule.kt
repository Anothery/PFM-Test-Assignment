package team.pfm.test.di

import androidx.lifecycle.ViewModel
import dagger.MapKey
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import team.pfm.test.data.UsersRepository
import team.pfm.test.ui.edituserdetails.EditUserDetailsViewModel
import team.pfm.test.ui.main.MainViewModel
import team.pfm.test.ui.userdetails.UserDetailsViewModel
import kotlin.reflect.KClass

@Module
class ViewModelModule {

    @Target(
        AnnotationTarget.FUNCTION,
        AnnotationTarget.PROPERTY_GETTER,
        AnnotationTarget.PROPERTY_SETTER
    )
    @kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
    @MapKey
    internal annotation class ViewModelKey(val value: KClass<out ViewModel>)

    @Provides
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    internal fun provideMainViewModel(usersRepository: UsersRepository): ViewModel =
        MainViewModel(usersRepository)

    @Provides
    @IntoMap
    @ViewModelKey(EditUserDetailsViewModel::class)
    internal fun provideEditUserDetailsViewModel(usersRepository: UsersRepository): ViewModel =
        EditUserDetailsViewModel(usersRepository)

    @Provides
    @IntoMap
    @ViewModelKey(UserDetailsViewModel::class)
    internal fun provideUserDetailsViewModel(usersRepository: UsersRepository): ViewModel =
        UserDetailsViewModel(usersRepository)
}

