package kg.o.internlabs.omarket.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import kg.o.internlabs.omarket.domain.repository.CheckOtpRepository
import kg.o.internlabs.omarket.domain.repository.CheckOtpRepositoryImpl

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideCheckOtpRepository(
        repo: CheckOtpRepositoryImpl
    ): CheckOtpRepository

}