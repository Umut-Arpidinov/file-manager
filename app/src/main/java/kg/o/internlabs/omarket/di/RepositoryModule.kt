package kg.o.internlabs.omarket.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import kg.o.internlabs.omarket.domain.repository.RegisterRepository
import kg.o.internlabs.omarket.data.repository.RegisterRepositoryImpl

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideCheckOtpRepository(
        repo: RegisterRepositoryImpl
    ): RegisterRepository

}