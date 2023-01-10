package kg.o.internlabs.omarket.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import kg.o.internlabs.omarket.data.repository.AdsRepositoryImpl
import kg.o.internlabs.omarket.data.repository.ProfileRepositoryImpl
import kg.o.internlabs.omarket.data.repository.RegisterRepositoryImpl
import kg.o.internlabs.omarket.domain.repository.AdsRepository
import kg.o.internlabs.omarket.domain.repository.ProfileRepository
import kg.o.internlabs.omarket.domain.repository.RegisterRepository

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideCheckOtpRepository(
        repo: RegisterRepositoryImpl
    ): RegisterRepository

    @Binds
    abstract fun provideAdsRepository(
        ads: AdsRepositoryImpl
    ): AdsRepository

    @Binds
    abstract fun provideProfileRepository(
        profile: ProfileRepositoryImpl
    ): ProfileRepository
}