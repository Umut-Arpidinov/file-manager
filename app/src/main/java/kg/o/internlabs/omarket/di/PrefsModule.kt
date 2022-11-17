package kg.o.internlabs.omarket.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import kg.o.internlabs.omarket.data.local.prefs.PrefsRepositoryImpl
import kg.o.internlabs.omarket.data.repository.RegisterRepositoryImpl
import kg.o.internlabs.omarket.domain.repository.PrefsRepository
import kg.o.internlabs.omarket.domain.repository.RegisterRepository


@Module
@InstallIn(ViewModelComponent::class)
abstract class PrefsModule {

    @Binds
    abstract fun provideCheckNumberRepository(
        prefsRepositoryImpl: PrefsRepositoryImpl
    ): PrefsRepository
}