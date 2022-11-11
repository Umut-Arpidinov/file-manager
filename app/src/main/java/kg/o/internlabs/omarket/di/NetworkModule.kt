package kg.o.internlabs.omarket.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kg.o.internlabs.core.BuildConfig.API_URL
import kg.o.internlabs.core.data.local.prefs.StoragePreferences
import kg.o.internlabs.omarket.data.local.prefs.PrefsRepositoryImpl
import kg.o.internlabs.omarket.data.remote.ApiService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://staging-api-market.o.kg/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun providePostApi(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun providePrefsRepository(context: StoragePreferences) = PrefsRepositoryImpl(context)

  /*  @Provides
    fun providesAuthRepository(storagePreferences: StoragePreferences, apiAuth: ApiAuth) =
        AuthRepository(storagePreferences, apiAuth)*/
}