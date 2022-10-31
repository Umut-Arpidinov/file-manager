package kg.o.internlabs.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kg.o.internlabs.core.BuildConfig
import kg.o.internlabs.core.data.remote.network.ApiService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RemoteModule {

    private val API_KEY = "ypsGjpmpDR9GndA9YvzBHpIqXsFStfyq"

    @Provides
    fun providePostApi(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideRetrofit() = createRetrofit(GsonConverterFactory.create())

    private fun createRetrofit(converter: GsonConverterFactory): Retrofit {
        val interceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        val authInterceptor = Interceptor { chain ->
            val request = chain.request().newBuilder().apply {
                addHeader("Content-type", "application/json").addHeader(
                    "API_KEY", API_KEY
                )
            }.build()
            chain.proceed(request)
        }

        val okHttpClient = OkHttpClient.Builder()
            .writeTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .connectTimeout(20, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
            .addInterceptor(authInterceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl(BuildConfig.API_URL)
            .addConverterFactory(converter)
            .client(okHttpClient)
            .build()
    }
}