package kg.o.internlabs.omarket.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kg.o.internlabs.core.BuildConfig.API_URL
import okhttp3.OkHttpClient

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient?): Retrofit? {
        return client?.let {
          Retrofit.Builder()
              .baseUrl(API_URL)
              .addConverterFactory(GsonConverterFactory.create())
              .client(it)
              .build()
        }
    }

    @Provides
    fun providePostApi(retrofit: Retrofit): ApiService? {
        return retrofit.create(ApiService::class.java)
    }



}